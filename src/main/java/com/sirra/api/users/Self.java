package com.sirra.api.users;

import javax.ws.rs.*;

import com.sirra.appcore.sql.*;
import com.sirra.appcore.users.*;
import com.sirra.server.rest.*;

public class Self extends ApiBase {

	@GET
	public BaseUser loadSelf() {
		return new Finder().getUser();
	}
}