package com.sirra.server.rest;

import java.io.IOException;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;

import org.eclipse.jetty.http.*;
import org.json.*;
import org.reflections.Reflections;

import com.sirra.appcore.users.*;
import com.sirra.appcore.util.*;
import com.sirra.server.json.JsonUtil;
import com.sirra.server.rest.annotations.*;
import com.sirra.server.session.SirraSession;

/**
 * All API calls are directed to this servlet first, which then finds the right ApiBase handler
 * based on a matching path.
 * 
 * Calls the appropriate method in based on annotations (GET, POST, PUT or DELETE).
 * 
 * There are three ways to pass in parameters:
 * 
 *   - Regular GET parameters. These are available from ApiBase via convenience methods.
 *   - PATH parameters. These are available from ApiBase via "pathParameters" variable.
 *   - A special GET parameter called "parameters". This is an array and maps to the API method parameters.
 *     For example, if the api method is "doApi(String a, int b)", then the rest call can pass in 
 *     ?parameters=["A String", 5].
 * 
 * @author aris
 */
@WebServlet(urlPatterns = {"/api/*"})
public class ApiServlet extends HttpServlet {

	protected static Map<String, Class<? extends ApiBase>> lookup;
	
	protected static String packageBase = "com.sirra";
	
	/**
	 * Set the root package where sirra-core will search for your API classes.
	 * 
	 * @param incomingPackageBase e.g. "com.sirra"
	 */
	public static void setAPIPackageBase(String incomingPackageBase) {
		packageBase = incomingPackageBase;
	}
	
	public static String getAPIPackageBase() {
		return packageBase;
	}

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
		// For IE
		response.setHeader("Cache-Control", "no-store, no-cache");
    	execute(request, response);
    }

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	execute(request, response);
    }

	@Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	execute(request, response);
    }

	@Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	execute(request, response);
    }

    protected void execute(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
    	String apiPath = request.getPathInfo();
    	HttpType httpMethod = HttpType.valueOf(request.getMethod());
    	
    	Object o = request.getParameter("cards");
    	
    	String parameterString = request.getParameter("parameters");
    	List<Object> parameters = (List<Object>) JsonUtil.getInstance().parse(parameterString);
    	
    	Map<String, String> parameterMap = processParameters(request);

    	List<String> pathList = new ArrayList();
    	String[] pathElements = apiPath.split("/");
    	for(String pathElement: pathElements) {
    		if(pathElement.equals("")) continue;
    		pathList.add(pathElement.toLowerCase());
    	}
    	
    	List<String> pathParameters = new ArrayList();
    	Class clazz = processPath(pathList, pathParameters);
    	
    	StringBuffer parameterDebug = new StringBuffer();
    	Iterator<String> parameterIterator = parameterMap.keySet().iterator();
    	while(parameterIterator.hasNext()) {
    		String key = parameterIterator.next();
    		parameterDebug.append(key + ": ");
    		
    		String value = parameterMap.get(key);
    		if(value != null && value.length() > 50) value = value.substring(0, 49) + " (TRUNCATED)";
    		parameterDebug.append(value);
    		
    		if(parameterIterator.hasNext()) parameterDebug.append(", ");
    	}
    	
    	System.out.println("\n--------- API Call Begin: " + request.getScheme() + " " + httpMethod.name() + " " + apiPath + " - Parameters: [" + parameterDebug.toString() + "] --------- ");
    	SirraSession.start(request, response);

    	UserSession userSession = UserSession.getCurrentSession();
		
		if(userSession != null) {
			SirraSession ss = SirraSession.get();
			
			ss.setUserId(userSession.getUserId());
			ss.setAccountId(userSession.getAccountId());
		}
    	
    	try {
    		ApiBase apiBase = (ApiBase) clazz.newInstance();
    		
    		apiBase.setPathParameters(pathParameters);
    		apiBase.setParameterMap(parameterMap);
    		
    		AnnotatedMethodCaller caller = new AnnotatedMethodCaller();
    		
    		Object result = null;
    		if(httpMethod == HttpType.GET) {
    			result = caller.call(apiBase, parameters, GET.class);
    		} else if(httpMethod == HttpType.POST) {
    			result = caller.call(apiBase, parameters, POST.class);
    		} else if(httpMethod == HttpType.PUT) {
    			result = caller.call(apiBase, parameters, PUT.class);
    		} else if(httpMethod == HttpType.DELETE) {
    			result = caller.call(apiBase, parameters, DELETE.class);
    		} else {
    			result = caller.call(apiBase, parameters, GET.class);
    		}

    		String responseStr = formatResponse(result, caller.getMethod());
    		
    		response.getWriter().write(responseStr);
    		return;
    	} catch(Exception e) {
    		System.err.println(StackTrace.get(e));
    		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR_500);
    		
    		Map map = new HashMap();
    		map.put("errorCode", 400);
    		map.put("errorMessage", "An unexpected server has occured.");
    		
    		JSONObject json = (JSONObject) JsonUtil.getInstance().convert(map);
    		
    		response.getWriter().write(json.toString());
    	} finally {
    		SirraSession.end();
    		System.out.println("--------- API Call END: " + httpMethod.name() + " " + apiPath +" ---------");
    	}
    }
    
    protected Class processPath(List<String> pathList, List<String> pathParameters) {
    	// Try longest first, and get progressively shorter
    	
    	for(int i=pathList.size(); i>=0; i--) {
    		
    		StringBuffer str = new StringBuffer("/");
    		for(int j=0; j<i; j++) {
    			str.append(pathList.get(j));
    			
    			if(j < i-1) {
    				str.append("/");
    			}
    		}
    		
    		Class clazz = getCorrespondingClass(str.toString());
    		if(clazz != null) {
    			
    			for(int k=i; k<pathList.size(); k++) {
    				pathParameters.add(pathList.get(k));
    			}
    			
    			return clazz;
    		}
    	}
    	
    	throw new RuntimeException("Cannot find API Class in " + packageBase + ": " + pathList);
    }
    
    public static void prepareClasses() {
    	if(lookup == null) {
    		lookup = new HashMap();
    		
	    	Reflections reflections = new Reflections(packageBase);
	    	Set<Class<? extends ApiBase>> apiClasses = reflections.getSubTypesOf(ApiBase.class);
	    	
	    	Reflections coreReflections = new Reflections("com.sirra");
	    	apiClasses.addAll(coreReflections.getSubTypesOf(ApiBase.class));
	    	
	    	// For each class, figure out the path.
	    	for(Class<? extends ApiBase> apiClass: apiClasses) {
	    		System.out.println("Class is " + apiClass);
	    		String fullName = apiClass.getCanonicalName();
	    		
	    		String endPath = fullName.substring(fullName.indexOf(".api.") + 5);
	    		String[] pieces = endPath.split("\\.");
	    		
	    		StringBuffer path = new StringBuffer("/");
	    		
	    		List<String> piecesList = new ArrayList();
	    		
	    		String lastPiece = "";
	    		for(String piece: pieces) {
	    			
	    			// For example, teachers.Teachers, the path is "/teachers".
	    			if(piece.toLowerCase().equals(lastPiece)) break;
	    			piecesList.add(piece.toLowerCase());
	    			
	    			lastPiece = piece;
	    		}
	    		
	    		Iterator<String> it = piecesList.iterator();
	    		while(it.hasNext()) {
	    			String pathElement = it.next();
	    			path.append(pathElement);
	    			
	    			if(it.hasNext()) path.append("/");
	    		}
	    		
	    		lookup.put(path.toString(), apiClass);
	    	}
    	}
    }
    
    protected Class<? extends ApiBase> getCorrespondingClass(String restPath) {
    	return lookup.get(restPath);
    }
    
    protected Map<String, String> processParameters(HttpServletRequest request) {

    	Map<String, String[]> requestMap = request.getParameterMap();
    	Map<String, String> parameterMap = new TreeMap();
    	
    	for(String key: requestMap.keySet()) {
    		String[] values = requestMap.get(key);
    		String value = null;
    		if(values.length > 0) value = values[0];
    		
    		parameterMap.put(key, value);
    	}
    	
    	return parameterMap;
    }
    
    protected String formatResponse(Object returnValue, Method method) {
    	Object json;
    	
		if(returnValue == null) {
			json = new JSONObject();
		} else {
			json = JsonUtil.getInstance().convert(returnValue);
		}
		
		// Now, return only the requested attributes.
		
		DefaultAttributes defaultAttributesAnnotation = method.getAnnotation(DefaultAttributes.class);
		if(defaultAttributesAnnotation  != null) {
			String[] validAttributesArray = defaultAttributesAnnotation.value();
			
			List<String> validAttributes = new ArrayList();
			for(int i=0; i<validAttributesArray.length; i++) {
				validAttributes.add(validAttributesArray[i]);
			}
			
			String fieldStr = SirraSession.get().getRequest().getParameter("fields");
			if(fieldStr != null && fieldStr.length() > 0) {
				String[] fields = fieldStr.split(",");
				for(String field: fields) {
					field = field.trim();
					if(field.length() > 0) {
						validAttributes.add(field);
					}
				}
			}
		
			if(json instanceof JSONObject) {
				filter((JSONObject) json, validAttributes);
			} else if(json instanceof JSONArray) {
				JSONArray arr = (JSONArray) json;
				for(int i=0; i<arr.length(); i++ ) {
					try {
						Object childObject = arr.get(i);
						if(childObject instanceof JSONObject) {
							filter((JSONObject) childObject, validAttributes);
						}
					} catch(JSONException e) {
						throw new RuntimeException(e);
					}
				}
			}
		}
		
		// tricky: If string, to be technically json it needs to be wrapped in quotes
		if(json instanceof String) {
			return "\"" + json + "\"";
		}
		
		return json.toString();
	}
    
    protected void filter(JSONObject json, List<String> validAttributes) {
    	Iterator<String> keysIt = json.keys();

    	while(keysIt.hasNext()) {
    		String key = keysIt.next();
    		
    		if(!validAttributes.contains(key)) {
    			keysIt.remove();
    		}
    	}
    }
}
