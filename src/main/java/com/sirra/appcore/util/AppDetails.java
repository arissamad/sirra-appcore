package com.sirra.appcore.util;

/**
 * Allows different Sirra projects to configure their relevant details.
 * 
 * @author aris
 */
public class AppDetails {
	
	// Something like "http://www.<yourserver>.com". Please leave trailing slash out.
	public static String url;
	
	// Something like "SirraTeam".
	public static String name;
	
	public static void configure(String name, String url) {
		AppDetails.name = name;
		AppDetails.url = url;
	}
}
