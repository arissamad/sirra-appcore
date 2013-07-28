package com.sirra.appcore.sql;

/**
 * Used to hold key-value pairs for whichever method needs it.
 * 
 * @author aris
 */
public class Pair {
	
	protected String key;
	protected String value;
	
	public Pair(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}