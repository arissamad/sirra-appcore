package com.sirra.server.json;

import java.util.*;

import com.sirra.appcore.hibernatetypes.*;
import com.sirra.appcore.util.*;

/**
 * Helps with casting values into the appropriate type.
 * 
 * Usually used to map values coming from javascript into the type defined by a java method.
 * 
 * Also can be used to map values coming from the db into the type defined by the java method. Needed when materializing columns.
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
		else if(parameterType.equals(Date.class)) {
			if(value instanceof Date) {
				return value;
			} else {
				return new Date(Long.parseLong(value.toString()));
			}
		} else if(parameterType.equals(PlainDate.class)) {
			if(value instanceof PlainDate) {
				return value;
			} else {
				return new PlainDate((String)value);
			}
		}
		else if(parameterType.isEnum()) {
			return Enum.valueOf(parameterType, (String) value);
		}
		else if(List.class.isAssignableFrom(parameterType)) {
			if(value instanceof String) {
				// So then, it must be a json object
				return ListTypeDescriptor.INSTANCE.fromString((String)value);
			} else {
				return value; // This can happen when the value is ALREADY a list.
			}
		}
		else if(SirraSerializable.class.isAssignableFrom(parameterType)) {
			return (SirraSerializable) JsonUtil.getInstance().parse((String) value);
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
