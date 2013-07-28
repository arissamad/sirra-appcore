package com.sirra.appcore.util;

/**
 * Adds the salt for the encryption of passwords.
 * 
 * @author aris
 */
public class Salt {
	private static String salt = "KS_2342sJs";
	
	public static String password(String password) {
		return salt + password;
	}
}