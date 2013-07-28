package com.sirra.appcore.util;

import java.util.*;

/**
 * Assists with reading environment variables.
 * 
 * @author aris
 */
public class Config {

	protected static Config instance;
	public static Config getInstance() {
		if(instance == null) {
			instance = new Config();
		}
		return instance;
	}
	
	private Config() {
		
	}
	
	public void dump() {
		Map<String, String> map = System.getenv();
		System.out.println("All env variables:\n");
		for(String key: map.keySet()) {
			String value = map.get(key);
			
			System.out.println("  " + key + " -> " + value);
		}
		
		System.out.println("Done with env");
	}
	
	public String get(String key) {
		String val = System.getenv(key);
		if(val == null) throw new RuntimeException("No environment key: " + key);
		return val;
	}
}
