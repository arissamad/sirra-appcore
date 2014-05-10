package com.sirra.api.users;

import java.util.*;

import javax.ws.rs.*;

import org.apache.commons.codec.digest.*;

import com.sirra.appcore.email.*;
import com.sirra.appcore.sql.*;
import com.sirra.appcore.users.*;
import com.sirra.appcore.util.*;
import com.sirra.server.rest.*;
import com.sirra.server.rest.annotations.*;
import com.sirra.server.templating.*;

public class ForgotPassword extends ApiBase {

	@POST
	@BY_ID
	public Map<String, Object> sendForgotPasswordEmail(String email) {
		if(email == null || email.equals("")) return fail("Email is blank.");
		
		BaseUser user = (BaseUser) new Finder().findByField(Finder.userClass, "email", email.trim().toLowerCase());
		
		if(user == null) {
			return fail("Email not recognized.");
		}
		
		String randomString = new Date().toString() + user.getId();
		String hash = DigestUtils.shaHex(randomString);
		
		String changeLink = AppDetails.url + "/resetpassword?h=" + hash;
		
		user.setForgotPasswordDate(new Date());
		user.setForgotPasswordHash(hash);
		save(user);
		
		EmailMessage em = new EmailMessage(Template.get("emails/forgot-password-email.html", 
				"name", user.getName(),
				"changeLink", changeLink));
		
		Email.send("Reset your password", em, EmailPerson.defaultSender(), new EmailPerson(user.getName(), user.getEmail()));
		
		return success();
	}
}
