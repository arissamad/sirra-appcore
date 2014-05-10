package com.sirra.api.signup;

import org.apache.commons.validator.*;

import com.sirra.appcore.email.*;
import com.sirra.appcore.sql.*;
import com.sirra.appcore.users.*;
import com.sirra.appcore.util.*;
import com.sirra.server.json.*;
import com.sirra.server.rest.*;
import com.sirra.server.templating.*;

/**
 * Extend this class with your own "SignUp.java" class.
 * 
 * @author aris
 */
public abstract class BaseSignUp extends ApiBase {

	/**
	 * Returns null if details look okay.
	 */
	protected Data verify(String fullname, String username, String password) {
		fullname = fullname.trim();
		username = username.trim();
		password = password.trim();
		
		if(fullname.equals("")) {
			return fail("Name cannot be blank.");
		}
		
		if(username.equals("")) {
			return fail("Email cannot be blank.");
		}

		if(!EmailValidator.getInstance().isValid(username)) {
			return fail("You didn't enter a valid email.");
		}
		
		if(password.equals("")) {
			return fail("Password cannot be blank.");
		}
		
		if(password.length() < 4) {
			return fail("Password cannot be less than 4 characters.");
		}
		
		Data foundUser = SqlSearch.singleSearch("SELECT email FROM users WHERE email = '" + username + "'", 
				new Columns("email"), null);
		
		if(foundUser != null) {
			return fail("The email address is already in use. Are you trying to log-in instead?");
		}
		
		return null;
	}
	
	public void sendEmails(BaseUser user) {

		EmailMessage emailMessage = new EmailMessage(Template.get("emails/welcome-email.html",
				"name", user.getName(),
				"username", user.getEmail()));
		
		Email.send("Welcome to " + AppDetails.name + "!", emailMessage,
				EmailPerson.defaultSender(),
				new EmailPerson(user.getName(), user.getEmail()));

		emailMessage = new EmailMessage(Template.get("emails/new-trial-notification.html", 
				"name", user.getName(),
				"email", user.getEmail()));
		
		Email.send(AppDetails.name + " Admin: New account!", emailMessage, EmailPerson.defaultSender(), EmailPerson.me());
		
	}
	
	protected Data fail(String reason) {
		Data data = new Data();
		data.put("isAccountCreated", false);
		data.put("problem", reason);
		return data;
	}
}
