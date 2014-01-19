package com.sirra.appcore.util;

import com.sirra.server.templating.*;
import com.sirra.appcore.email.*;

/** 
 * Assists with clear and helpful stack traces.
 */
public class StackTrace {
	
	protected static String appName = "Sirra";
	
	public static void setAppName(String appName) {
		StackTrace.appName = appName;
	}
	
	public static String get(Throwable t) {
		return ExceptionUtil.getStackTrace(t);
	}
	
	public static void print(Throwable t) {
		System.out.println(get(t));
	}
	
	/**
	 * Log the message but also notify me actively via email.
	 */
	public static void notify(Throwable t, String message) {
		System.out.println(message);
		System.out.println(t);
		
		EmailMessage em = new EmailMessage(Template.get("error-occurred.html",
				"summary", message,
				"errorDetails", ExceptionUtil.getStackTrace(t)));
		
		Email.send(appName + " Error", em, EmailPerson.me());
	}
	
	/**
	 * Log the message but also notify me actively via email AND sms.
	 */
	public static void notifyUrgent(Throwable t, String message) {
		System.out.println(message);
		System.out.println(t);
		
		EmailMessage em = new EmailMessage(Template.get("error-occurred.html",
				"summary", message,
				"errorDetails", ExceptionUtil.getStackTrace(t)));
		
		Email.send(appName + " Error", em, EmailPerson.me());
	}
}