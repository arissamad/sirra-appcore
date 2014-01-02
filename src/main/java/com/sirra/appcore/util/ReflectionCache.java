package com.sirra.appcore.util;

import java.lang.reflect.*;
import java.util.*;

import org.reflections.*;

import com.sirra.server.rest.*;

public class ReflectionCache {
	
	protected static Reflections reflections;
	protected static Map<Class, Set> subTypeLookup;
	protected static Map<String, Set<Method>> methodLookup;
	protected static Map<Class, Set<Field>> fieldLookup;
	
	protected static void init() {
		if(reflections == null) {
			reflections = new Reflections(ApiServlet.getAPIPackageBase());
			subTypeLookup = new HashMap();
			methodLookup = new HashMap();
			fieldLookup = new HashMap();
		}
	}
	
	public static Set getSubTypesOf(Class theClass) {
		init();
		
		if(!subTypeLookup.containsKey(theClass)) {
			Set classes = reflections.getSubTypesOf(theClass);
			subTypeLookup.put(theClass, classes);
		}
		
		return subTypeLookup.get(theClass);
	}
	
	public static Set<Method> getMethods(Class theClass, String setterName) {
		init();
		
		String combinedString = theClass.getName() + "-" + setterName;
		
		if(!methodLookup.containsKey(combinedString)) {
			Set<Method> methods = ReflectionUtils.getAllMethods(theClass, ReflectionUtils.withName(setterName));
			methodLookup.put(combinedString, methods);
		}
		
		return methodLookup.get(combinedString);
	}
	
	public static Set<Field> getFields(Class theClass) {
		init();
		
		if(!fieldLookup.containsKey(theClass)) {
			Set<Field> fields = ReflectionUtils.getAllFields(theClass);
			fieldLookup.put(theClass, fields);
		}
		
		return fieldLookup.get(theClass);
		
	}
}
