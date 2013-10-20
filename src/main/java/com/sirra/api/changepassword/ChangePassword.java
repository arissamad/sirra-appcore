package com.sirra.api.changepassword;

import javax.ws.rs.*;

import com.sirra.appcore.sql.*;
import com.sirra.appcore.users.*;
import com.sirra.server.rest.*;
import com.sirra.server.rest.annotations.*;

public class ChangePassword extends ApiBase {

	// Change password
	@POST
	@Parameters({"password"})
	public void changePassword(String password) {
		BaseUser baseUser = new Finder().getUser();
		baseUser.encryptAndSetPassword(password);
		
		save(baseUser);
	}
}