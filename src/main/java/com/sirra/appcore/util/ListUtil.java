package com.sirra.appcore.util;

import java.lang.reflect.*;
import java.util.*;

import org.reflections.*;

public class ListUtil {
	
	public static <VO> Map<String, VO> mapList(List<VO> theList, String keyAttribute) {
		Map<String, VO> resultMap = new HashMap();
		Field field = null;
		
		if(theList.size() > 0) {
			VO vo = theList.get(0);
			
			Class currClass = vo.getClass();
			for(int i=0; i<3; i++) {
				try {
					field = currClass.getDeclaredField(keyAttribute);
				} catch(Exception e) {
					currClass = vo.getClass().getSuperclass();
				}
			}
			field.setAccessible(true);
		}
		
		for(VO vo: theList) {
			
			try {	
				String value = (String) field.get(vo);
				resultMap.put(value, vo);
			} catch(Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		return resultMap;
	}

	public static <VO> Set<String> extractAttribute(List<VO> theList, String keyAttribute) {
		Set<String> set = new HashSet<String>();
		
		Field field = null;
		
		if(theList.size() > 0 ){
			VO vo = theList.get(0);
			try {
				field = vo.getClass().getDeclaredField(keyAttribute);
			} catch(Exception e) {
				Set<Field> fields = ReflectionCache.getFields(vo.getClass());
				
				for(Field currField: fields) {
					if(currField.getName().equals(keyAttribute)) {
						field = currField;
						break;
					}
				}
			}
			
			field.setAccessible(true);
		}
		
		
		for(VO vo: theList) {
			
			try {
				String value = (String) field.get(vo);
				set.add(value);
			} catch(Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		return set;
	}
	
	public static List<String> convertStringToList(String str) {
		List<String> result = new ArrayList();
		
		if(str == null) return result;
		str = str.trim();
		if(str.equals("")) return result;
		
		String[] pieces = str.split("\n");
		
		for(String piece: pieces) {
			result.add(piece.trim());
		}
		
		return result;
	}
	
	public static String convertListToString(List<String> strList) {
		StringBuffer str = new StringBuffer();
		
		Iterator<String> it = strList.iterator();
		
		while(it.hasNext()) {
			str.append(it.next());
			
			if(it.hasNext()) str.append("\n");
		}
		
		return str.toString();
	}
	
	public static List<String> convertStringToList(String str, String separator) {
		List<String> result = new ArrayList();
		
		if(str == null) return result;
		str = str.trim();
		if(str.equals("")) return result;
		
		String[] pieces = str.split(separator);
		
		for(String piece: pieces) {
			result.add(piece.trim());
		}
		
		return result;
	}
	
	public static String convertListToString(List<String> strList, String separator) {
		StringBuffer str = new StringBuffer();
		
		Iterator<String> it = strList.iterator();
		
		while(it.hasNext()) {
			str.append(it.next());
			
			if(it.hasNext()) str.append(separator);
		}
		
		return str.toString();
	}
}
