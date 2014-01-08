package com.sirra.appcore.email;

import javax.mail.Message.RecipientType;

/**
 * Encapsulates a "To:" or "Cc:" or "Bcc:" email target.
 * 
 * @author aris
 */
public class EmailPerson {
	
	protected static String defaultName;
	protected static String defaultEmail;
	
	public static void configure(String defaultName, String defaultEmail) {
		EmailPerson.defaultName = defaultName;
		EmailPerson.defaultEmail = defaultEmail;
	}
	
	protected RecipientType type;
	protected String name;
	protected String email;
	
	public EmailPerson(String name, String email) {
		this.type = RecipientType.TO;
		this.name = name;
		this.email = email;
	}
	
	public EmailPerson(RecipientType type, String name, String email) {
		this.type = type;
		this.name = name;
		this.email = email;
	}
	
	public RecipientType getRecipientType() {
		return type;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public static EmailPerson defaultSender() {
		return new EmailPerson(defaultName, defaultEmail);
	}
	
	public static EmailPerson me() {
		return new EmailPerson("Aris Samad", "aris.samad@gmail.com");
	}
}
