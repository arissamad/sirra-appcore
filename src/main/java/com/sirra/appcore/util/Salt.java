package com.sirra.appcore.util;

/**
 * Adds the salt for the encryption of passwords.
 * 
 * @author aris
 */
public class Salt {
	private static String salt = "RANDOMLY_GENERATED_STRING"; //generate per user hash, not one salt for all hashes!
	
	public static String password(String password) {
		return salt + password;
	}
}
