package com.sirra.appcore.util;

import com.ming.server.templating.*;
import com.sirra.appcore.email.*;

/** 
 * Assists with clear and helpful stack traces.
 */
public class StackTrace {
	
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
		
		Email.send("Sirra Voicemail Error", em, 
				new EmailPerson("Sirra Voicemail", "support@sirravoicemail.com"),
				new EmailPerson("Aris Samad", "aris.samad@gmail.com"));
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
		
		Email.send("Sirra Voicemail Error", em, 
				new EmailPerson("Sirra Voicemail", "support@sirravoicemail.com"),
				new EmailPerson("Aris Samad", "aris.samad@gmail.com"));
	}
}