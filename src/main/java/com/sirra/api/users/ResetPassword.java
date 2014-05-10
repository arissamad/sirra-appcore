package com.sirra.api.users;

import java.util.*;

import javax.ws.rs.*;

import com.sirra.appcore.sql.*;
import com.sirra.appcore.users.*;
import com.sirra.appcore.util.*;
import com.sirra.server.rest.*;
import com.sirra.server.rest.annotations.*;

public class ResetPassword extends ApiBase {
	
	@POST
	@Parameters({"hash", "password"})
	public Map<String, Object> onPost(String hash, String password) {
		BaseUser user = (BaseUser) new Finder().findByField(Finder.userClass, "forgotPasswordHash", hash);
		
		if(user == null) return fail("User not found.");

		Date forgotDate = user.getForgotPasswordDate();
		Date oneDayAgo = DateUtil.addDays(new Date(), -1);
		
		if(forgotDate.before(oneDayAgo)) {
			return fail("Reset password has expired.");
		}
		
		user.setForgotPasswordDate(null);
		user.setForgotPasswordHash(null);
		user.encryptAndSetPassword(password);
		
		save(user);
		
		return success();
	}
}
