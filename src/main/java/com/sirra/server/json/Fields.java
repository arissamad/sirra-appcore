package com.sirra.server.json;

import java.lang.reflect.*;
import java.util.*;

public class Fields {
	
	protected static Map<Class, List<String>> lookup = new HashMap();
	
	public static List<String> getFields(Object valueObject) {
		Class currClass = valueObject.getClass();
		
		if(!lookup.containsKey(currClass)) {
			List<String> fields = new ArrayList();

	    	while(true) {
				Field[] declaredFields = currClass.getDeclaredFields();
				
				for(Field field: declaredFields) {
					fields.add(field.getName());
				}
				
				currClass = currClass.getSuperclass();
				if(currClass.getName().equals("java.lang.Object"))
				{
					break;
				}	
			}
	    	
	    	lookup.put(valueObject.getClass(), fields);
		}
		
		return lookup.get(valueObject.getClass());
	}
}
