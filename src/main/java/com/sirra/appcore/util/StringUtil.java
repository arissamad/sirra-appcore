package com.sirra.appcore.util;

public class StringUtil {
	
	public static String pad(String originalString, Character padCharacter, int length) {
		StringBuffer buffer = new StringBuffer(originalString);
		
		while (buffer.length() < length) 
		{
			buffer.insert(0, padCharacter);
		}
		
		return buffer.toString();
	}
}