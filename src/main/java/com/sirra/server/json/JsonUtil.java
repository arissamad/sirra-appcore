package com.sirra.server.json;

import java.lang.reflect.*;
import java.util.*;

import javax.persistence.*;

import org.json.*;
import org.json.simple.JSONValue;
import org.reflections.*;

import com.sirra.appcore.util.*;
import com.sirra.server.rest.*;

public class JsonUtil {
	
	protected static JsonUtil instance;
	public static JsonUtil getInstance() {
		if(instance == null) instance = new JsonUtil();
		return instance;
	}
	
	protected Map<Class, List<Field>> lookup = new HashMap();
	
	/**
	 * Given a Java object, convert as JSONObject/JSONNArray.
	 */
	public Object convert(Object obj) {
		try {
			return _convertToJson(obj, 0);
		} catch(JSONException e) {
			throw new RuntimeException(e);
		}
	}
	
	private Object _convertToJson(Object obj, int level)
	throws JSONException
	{
		if(level > 10) {
			throw new RuntimeException("Something probably wrong. _convertToJson now at level 11 for object: " + obj);
		}
		
		if(obj == null) return null;
			
		if(obj instanceof String ||
		   obj instanceof Integer ||
		   obj instanceof Double ||
		   obj instanceof Boolean)
		{
			return obj;
		}
		else if(Map.class.isInstance(obj)) {
			Map<String, Object> map = (Map) obj;
			
			JSONObject jsonMap = new JSONObject();
			for(String key:map.keySet()) {
				jsonMap.put(key, _convertToJson(map.get(key), level+1));
			}
			return jsonMap;
		}
		else if(List.class.isInstance(obj)) {
			List list = (List) obj;
			
			JSONArray array = new JSONArray();
			for(Object item: list) {
				array.put(_convertToJson(item, level+1));
			}
			return array;
		}
		else if(Data.class.isInstance(obj)) {
			Data data = (Data) obj;
			
			JSONObject json = new JSONObject();
			for(String key: data.keySet()) {
				json.put(key, _convertToJson(data.get(key), level+1));
			}
			return json;
		} else if(Date.class.isInstance(obj)) {
			Date date = (Date) obj;
			return date.getTime();
		} else if(obj.getClass().isEnum()) {
			// Danger: This is not bidirectional to the front-end. Once it is a string, you can't identify the type anymore.
			// But with DB storage it can still work because the Caster can identify that the field type is an enum.
			return ((Enum)obj).name();
		} else if(PlainDate.class.isInstance(obj)) {
			return ((PlainDate)obj).toString();
		}
		// Else, retrieve all the fields in the object using reflection
		
		Class currClass = obj.getClass();

		if(!lookup.containsKey(currClass)) {
			List<Field> fields = new ArrayList();

	    	while(true) {
				Field[] declaredFields = currClass.getDeclaredFields();
				
				for(Field field: declaredFields) {
					if(field.getAnnotation(Hidden.class) != null) {
						continue;
					}
					fields.add(field);
				}

				currClass = currClass.getSuperclass();
				if(currClass.getName().equals("java.lang.Object"))
				{
					break;
				}	
			}
	    	
	    	lookup.put(obj.getClass(), fields);
		}
		
		List<Field> fields = lookup.get(obj.getClass());
	    	
		JSONObject json = new JSONObject();
		
		for(Field field: fields) {
			field.setAccessible(true);
			try {
				Object value = field.get(obj);
				json.put(field.getName(), _convertToJson(value, level+1));
			} catch(IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
		
		if(obj instanceof EntityBase) {
			EntityBase entityBase = (EntityBase) obj;
			Map<String, Object> additionalParameters = entityBase.getAdditionalParameters();
			
			for(String key: additionalParameters.keySet()) {
				json.put(key, _convertToJson(additionalParameters.get(key), level+1));
			}
		}
		
		if(SirraSerializable.class.isInstance(obj)) {
			json.put("_s_type", obj.getClass().getSimpleName());
		}
		
		return json;
	}
	
	public Object parse(String jsonString) {
		if(jsonString == null) {
			return null;
		}
		
		Object obj = JSONValue.parse(jsonString);
		return _parse(obj);
		
		/*
		JSONParser parser = new JSONParser();
		
		try {
			
			Object obj = parser.parse(jsonString);
			
			return _parse(obj);
		} catch(ParseException e) {
			throw new RuntimeException(e);
		}
		*/
	}
	
	protected Object _parse(Object obj) {

		if(obj instanceof org.json.simple.JSONArray) {
			org.json.simple.JSONArray arr = (org.json.simple.JSONArray) obj;
			List l = new ArrayList();
			for(int i=0; i<arr.size(); i++) {
				l.add(_parse(arr.get(i)));
			}
			return l;
		} else if(obj instanceof org.json.simple.JSONObject) {
			org.json.simple.JSONObject json = (org.json.simple.JSONObject) obj;
			
			for(Object keyObject: json.keySet()) {
				String key = (String) keyObject;
				Object childObject = json.get(key);
				json.put(key, _parse(childObject));
			}
			
			if(json.containsKey("_s_type")) {
		    	Set<Class<? extends SirraSerializable>> classes = ReflectionCache.getSubTypesOf(SirraSerializable.class);
		    	
		    	String className = (String) json.get("_s_type");
		    	
		    	for(Class theClass: classes) {
		    		
		    		if(theClass.getSimpleName().equals(className)) {
		    			try {
		    				SirraSerializable ss = (SirraSerializable) theClass.newInstance();
		    				
		    				for(Object keyObject: json.keySet()) {
		    					String key = (String) keyObject;
		    					Object childObject = json.get(key);
		    					Setter.set(ss, key, childObject);
		    				}
		    				
		    				if(ss instanceof SirraPersistentSerializable) {
		    					((SirraPersistentSerializable) ss).setIsClean();
		    				}
		    				
		    				return ss;
		    			} catch(Exception e) {
		    				throw new RuntimeException(e);
		    			}
		    		}
		    	}
		    	
				// Instantiate native object
				return json;
			}
			
			// Remember that json implements Map.
			return json;
		}
		else {
			return obj;
		}
	}
}
