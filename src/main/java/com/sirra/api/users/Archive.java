package com.sirra.api.users;

import javax.ws.rs.*;

import com.sirra.appcore.sql.*;
import com.sirra.appcore.users.*;
import com.sirra.server.rest.*;
import com.sirra.server.rest.annotations.*;

public class Archive extends ApiBase {

	@DELETE
	@BY_ID
	public void archiveUser(String userId) {
		BaseUser user = (BaseUser) get(Finder.userClass, userId);
		user.setArchived(true);
		
		save(user);
	}
	
	@POST
	@BY_ID
	public void restoreUser(String userId) {
		BaseUser user = (BaseUser) get(Finder.userClass, userId);
		user.setArchived(false);
		
		save(user);
	}
}