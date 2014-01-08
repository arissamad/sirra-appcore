package com.sirra.server.rest;

import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;

import javax.ws.rs.*;

import org.eclipse.jetty.util.ajax.*;
import org.json.*;
import org.reflections.*;

import com.sirra.appcore.util.*;
import com.sirra.server.json.*;
import com.sirra.server.rest.annotations.*;

/**
 * Assists with finding and calling methods annotated with GET, PUT, POST or DELETE.
 * 
 * There are two ways method parameters can be passed in. 
 *  - Via regular rest parameters, which then get mapped via the "Variables" annotation.
 *  - Via a "parameters" variable.
 * 
 * Otherwise, the parameters are not passed in via the method, and is made available via the ApiBase.getParameter() method.
 * 
 * @author aris
 */
public class AnnotatedMethodCaller {
	
	protected Method method;
	
	public Method getMethod() {
		return method;
	}
	
	public Object call(ApiBase apiBase, List<Object> parameters, Class annotationClass) {
		
		boolean firstParameterIsId = false;
		
		int numPathParameters = apiBase.getPathParameters().size();
		if(numPathParameters == 1) {
			firstParameterIsId = true;
		}
		
		Set<Method> methods = ReflectionUtils.getAllMethods(apiBase.getClass(), ReflectionUtils.withAnnotation(annotationClass));
		
		// Now filter BY_ID methods as necessary
		Iterator<Method> methodIt = methods.iterator();
		while(methodIt.hasNext()) {
			Method method = methodIt.next();
			
			if(numPathParameters > 1 && method.isAnnotationPresent(PathParameters.class)) {
				PathParameters pathParametersAnnotation = method.getAnnotation(PathParameters.class);
				if(pathParametersAnnotation.value().length == numPathParameters) {
					// This one wins
					methods = new HashSet();
					methods.add(method);
					break;
				}
			}
			
			if(numPathParameters <= 1 && method.isAnnotationPresent(PathParameters.class)) {
				methodIt.remove();
				continue;
			}
			
			if(firstParameterIsId && !method.isAnnotationPresent(BY_ID.class)) {
				methodIt.remove();
			} else if(!firstParameterIsId && method.isAnnotationPresent(BY_ID.class)) {
				methodIt.remove();
			}
		}
		
		if(methods.size() != 1) {
			throw new RuntimeException("For class " + apiBase.getClass().getSimpleName() + ", number of methods matching " + annotationClass + " and has BY_ID:" + firstParameterIsId + " should be 1 but it is: " + methods.size());
		}
		
		method = methods.iterator().next();
		
		Annotation publicAccessAnnotation = method.getAnnotation(PublicAccess.class);
		if(publicAccessAnnotation != null) {
			
		}
		
		List<Object> finalParameterList = new ArrayList();
		
		// Fill in ID parameter for GET_BY_ID. Other methods of specifying parameters, if applicable, will override this.
		
		if(firstParameterIsId) {
			if(apiBase.getPathParameters().size() == 1) {
				finalParameterList.add(apiBase.getPathParameters().get(0));
			}
		}
		
		// Method 1 of specifying method parameters.
		if(parameters != null) {
			finalParameterList.clear();
			finalParameterList.addAll(parameters);
		}
		
		// Method 2 of specifying method parameters: As query parameters (or, in the case of jQuery, as the data object).
		PathParameters pathParametersAnnotation = method.getAnnotation(PathParameters.class);
		Map<String, String> pathLookup = new HashMap();
		if(pathParametersAnnotation != null) {
			List<String> pathValues = apiBase.getPathParameters();
			
			String[] pathNames = pathParametersAnnotation.value();
			for(int i=0; i<pathNames.length; i++) {
				pathLookup.put(pathNames[i], pathValues.get(i));
			}
		}
				
		Parameters parametersAnnotation = method.getAnnotation(Parameters.class);
		Class[] parameterTypes = method.getParameterTypes();
		if(parametersAnnotation != null) {
			
			List<Object> values = new ArrayList();
			
			String[] parameterMapping = parametersAnnotation.value();
			
			for(int i=0; i<parameterMapping.length; i++) {
				String parameterName = parameterMapping[i];
				
				// If value was not passed in via REST call, it will be null.
				String value = apiBase.getParameter(parameterName);
				
				if(value == null) {
					value = pathLookup.get(parameterName);
				}
				
				// Special case for first parameter when it is GET_BY_ID
				if(i == 0 && firstParameterIsId) {
					if(apiBase.getPathParameters().size() == 1) {
						value = apiBase.getPathParameters().get(0);
					}
				}
				
				// Now cast value appropriately
				
				if(i >= parameterTypes.length) {
					throw new RuntimeException("Misconfigured REST method: Expecting parameter \"" + parameterName + "\", but method signature does not have that.");
				}
				
				Class parameterType = parameterTypes[i];
				
				// Consider using Caster.cast()
				if(parameterType.equals(int.class)) {
					if(value == null) {
						values.add(0);
					} else {
						values.add(new Integer(value));
					}
				} else if(parameterType.equals(Integer.class)) {
					if(value == null) {
						values.add(null);
					} else {
						values.add(new Integer(value));
					}
				}
				else if(parameterType.equals(Double.class)) {
					if(value == null || value.equals("")) {
						values.add(null);
					} else {
						values.add(new Double(value));
					}
				}
				else if(parameterType.equals(double.class)) {
					if(value == null || value.equals("")) {
						values.add(0.0d);
					} else {
						values.add(new Double(value));
					}
				}
				else if(parameterType.equals(String.class)) {
					values.add(value);
				}
				else if(parameterType.equals(Boolean.class)) {
					if(value == null) {
						values.add(null);
					} else {
						values.add(Boolean.parseBoolean(value));
					}
				}
				else if(parameterType.equals(boolean.class)) {
					if(value == null) {
						values.add(false);
					} else {
						values.add(Boolean.parseBoolean(value));
					}
				}
				else if(parameterType.equals(Date.class)) {
					if(value == null) values.add(null);
					else {
						Long l = Long.parseLong(value);
						Date date = new Date(l);
						values.add(date);
					}
				}
				else if(parameterType.equals(PlainDate.class)) {
					if(value == null) values.add(null);
					else {
						values.add(new PlainDate(value));
					}
				}
				else {
					// Assume complex JSON
					Object obj = JsonUtil.getInstance().parse(value);
					values.add(obj);	
				}
			}
			
			finalParameterList.clear();
			finalParameterList.addAll(values);
		}
		
		try {
			return method.invoke(apiBase, finalParameterList.toArray());

		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	protected String formatAnnotations(List<Class> annotations) {
		StringBuffer str = new StringBuffer();
		str.append("[");
		
		Iterator<Class> it = annotations.iterator();
		
		while(it.hasNext()) {
			Class annotation = it.next();
			
			str.append(annotation.getSimpleName());
			while(it.hasNext()) {
				str.append(", ");
			}
		}
		
		str.append("]");
		
		return str.toString();
	}
	

    protected static Object filterAttributes(Method method, Object result) {
    	
    	DefaultAttributes defaultAttributesAnnotation = method.getAnnotation(DefaultAttributes.class);
    	
    	if(defaultAttributesAnnotation == null) return result;
    	
    	String[] defaultAttributes = defaultAttributesAnnotation.value();
    	

    	if(result instanceof List) {
    		List originalList = (List) result;
    		List newList = new ArrayList();
    		
    		for(Object object: originalList) {
    			
    		}
    		
    		result = newList;
    	} else {
    		
    	}
    
    	return result;
    }
}