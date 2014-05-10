package com.sirra.api.users;

import java.util.*;

import javax.ws.rs.*;

import com.sirra.appcore.sql.*;
import com.sirra.appcore.users.*;
import com.sirra.server.rest.*;
import com.sirra.server.rest.annotations.*;

public class CheckResetHash extends ApiBase {

	@GET
	@Parameters({"hash"})
	public Map<String, Object> checkResetHash(String hash) {
		BaseUser user = (BaseUser) new Finder().findByField(Finder.userClass, "forgotPasswordHash", hash);
		
		if(user == null) return fail("Hash does not match");
		else return success();
	}
}
