package com.sirra.api.users;

import java.util.*;

import javax.ws.rs.*;

import com.sirra.appcore.sql.*;
import com.sirra.appcore.users.*;
import com.sirra.server.rest.*;
import com.sirra.server.rest.annotations.*;

public abstract class BaseUsers extends ApiBase {

	@GET
	@DefaultAttributes({"id", "name", "email", "title", "roleMetaId", "thumbUrl"})
	@Parameters({"showArchived"})
	public List<BaseUser> getUsers(boolean showArchived) {
		
		getSqlParams().addConstraint("users.archived = " + showArchived);
		List<BaseUser> users = SqlSearch.search(Finder.userClass, "SELECT ${columns} FROM users ORDER BY name", getSqlParams("name", "email", "title"));
		
		return users;
	}
	
	@GET
	@BY_ID
	@DefaultAttributes({"id", "name", "email", "title", "roleMetaId", "thumnUrl"})
	public BaseUser getUser(String userId) {
		BaseUser user = (BaseUser) get(Finder.userClass, userId);
		return user;
	}
	
	@POST
	@BY_ID
	@Parameters({"userId", "name", "email", "paypalEmail", "password", "title", 
		"roleMetaId", "address", "ssn", "teamMemberIds", "projectIds", "hourlyRate",
		"sendEmail", "notes"})
	public void updateUser(String userId, String name, String email, String password, String roleMetaId)
	{	
		BaseUser user = (BaseUser) get(Finder.userClass, userId);
		
		if(user == null) {
			user = instantiateUser();
			user.setRoleMetaId("user");
		}
		
		if(name != null) user.setName(name);
		if(email != null) user.setEmail(email);
		
		if(password != null) user.encryptAndSetPassword(password);
		
	
		if(roleMetaId != null) user.setRoleMetaId(roleMetaId);
		
		save(user);
	}
	
	@DELETE
	@BY_ID
	public void deleteUser(String userId) {
		BaseUser user = (BaseUser) get(Finder.userClass, userId);
		delete(user);
	}
	
	protected abstract BaseUser instantiateUser();
}