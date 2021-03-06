package com.sirra.server.json;

import java.math.*;
import java.util.*;

/**
 * Used to transfer via JSON.
 * 
 * @author aris
 */
public class Data extends HashMap<String, Object> {
	
	public Data() {
		
	}
	
	public String getString(String key) {
		return (String) get(key);
	}
	
	public int getInt(String key) {
		return (Integer) get(key);
	}
	
	public int getInt(String key, int valueIfEmpty) {
		Object obj = get(key);
		
		if(obj == null) return valueIfEmpty;
		
		if(obj instanceof BigInteger) {
			return ((BigInteger)obj).intValue();
		} else return (Integer) obj;
	}
	
	public double getDouble(String key) {
		return (Double) get(key);
	}

	public double getDouble(String key, double defaultValue) {
		if(get(key) == null) return defaultValue;
		return (Double) get(key);
	}
	
	public boolean getBoolean(String key) {
		return (Boolean) get(key);
	}
	
	public Date getDate(String key) {
		return (Date) get(key);
	}
	
	public String toString() {
		SortedSet<String> keys = new TreeSet(keySet());
		StringBuffer str = new StringBuffer();
		
		Iterator<String> it = keys.iterator();
		while(it.hasNext()) {
			String key = it.next();
			str.append("[" + key + "]: " + get(key));
			if(it.hasNext()) str.append(", ");
		}
		
		return str.toString();
	}
}