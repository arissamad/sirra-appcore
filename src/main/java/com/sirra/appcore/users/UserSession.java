package com.sirra.appcore.users;

import java.util.*;

import javax.persistence.*;

import com.sirra.appcore.util.*;

@Entity
@Table(name = "usersessions")
public class UserSession {
	
	protected String sessionId;
	protected String username;
	protected Date expiryDate;
	
	public UserSession() {
	}
	
	public static UserSession newUserSession(String username) {
		UserSession us = new UserSession();
		
		Date d = new Date();
		Date nd = DateUtil.addDays(d, 1);
		
		us.setSessionId(UUID.randomUUID().toString());
		us.setUserName(username);
		us.setExpiryDate(DateUtil.addDays(new Date(), 1));
		
		return us;
	}
	
	@Id
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUserName(String username) {
		this.username = username;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
