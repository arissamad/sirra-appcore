package com.sirra.appcore.util;

import java.lang.reflect.*;
import java.util.*;

import org.reflections.*;

public class ListUtil {
	
	
	
	public static <VO> Map<String, VO> mapList(List<VO> theList, String keyAttribute) {
		Map<String, VO> resultMap = new HashMap();
		
		for(VO vo: theList) {
			
			try {
				Field field = vo.getClass().getDeclaredField(keyAttribute);
				field.setAccessible(true);
				
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
				Set<Field> fields = ReflectionUtils.getAllFields(vo.getClass());
				
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
}
