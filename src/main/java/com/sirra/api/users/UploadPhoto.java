package com.sirra.api.users;

import javax.ws.rs.*;

import com.sirra.appcore.sql.*;
import com.sirra.appcore.users.*;
import com.sirra.server.rest.*;
import com.sirra.server.rest.annotations.*;

public class UploadPhoto extends ApiBase {
	
	@POST
	@Parameters({"thumbUrl"})
	public void setPhotoUrls(String thumbUrl) {
		BaseUser user = new Finder().getUser();
		user.setThumbUrl(thumbUrl);
		
		save(user);
	}
}