package com.sirra.server.json;

/**
 * Helps with casting values into the appropriate type.
 * 
 * Usually used to map values coming from javascript into the type defined by a java method.
 * 
 * @author aris
 */
public class Caster {
	
	public static Object cast(Class parameterType, Object value) {
		
		if(parameterType.equals(int.class)) {
			if(value == null) {
				return 0;
			} else {
				return forceToInt(value);
			}
		} else if(parameterType.equals(Integer.class)) {
			if(value == null) {
				return null;
			} else {
				return forceToInt(value);
			}
		}
		else if(parameterType.equals(double.class)) {
			if(value == null) {
				return 0.0d;
			} else {
				return Double.parseDouble(value.toString());
			}
		}
		else if(parameterType.equals(String.class)) {
			return value.toString();
		}
		else if(parameterType.equals(Boolean.class)) {
			return Boolean.parseBoolean(value.toString());
		}
		else if(parameterType.isEnum()) {
			return Enum.valueOf(parameterType, (String) value);
		}
		return value;
	}
	
	public static int forceToInt(Object value) {
		try {
			return Integer.parseInt(value.toString());
		} catch(NumberFormatException e) {
			// Maybe it's a double
			double d = Double.parseDouble(value.toString());
			return (int) Math.round(d);
		}
	}
}
