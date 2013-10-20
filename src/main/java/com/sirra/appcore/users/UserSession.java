package com.sirra.appcore.users;

import java.util.*;

import javax.persistence.*;
import javax.servlet.http.*;

import com.sirra.appcore.sql.*;
import com.sirra.appcore.util.*;
import com.sirra.server.session.*;

@Entity
@Table(name = "usersessions")
public class UserSession {
	
	protected String sessionId;
	protected String username;
	
	protected String userId;
	protected String accountId;
	
	protected Date expiryDate;
	
	public UserSession() {
	}
	
	public static UserSession newUserSession(String username, String userId, String accountId) {
		UserSession us = new UserSession();
		
		us.setSessionId(UUID.randomUUID().toString());
		us.setUserName(username);
		us.setUserId(userId);
		us.setAccountId(accountId);
		us.setExpiryDate(DateUtil.addDays(new Date(), 1));
		
		return us;
	}
	
	protected static Map<String, UserSession> lookup = new HashMap();
	
	/**
	 * For a logged-in user, this would return the user session.
	 * It caches UserSessions for efficiency.
	 * 
	 * @return
	 */
	public static UserSession getCurrentSession() {
		Cookie[] cookies = SirraSession.get().getRequest().getCookies();

		if(cookies == null) return null;
		
		for(Cookie cookie: cookies) {
			if(cookie.getName() != null && cookie.getName().equals("sirra-session-id")) {
				String userSessionId = cookie.getValue();
				
				UserSession userSession = lookup.get(userSessionId);
				
				if(userSession == null) {
					userSession = new Finder().findByField(UserSession.class, "sessionId", userSessionId);
					
					if(userSession != null) lookup.put(userSessionId, userSession);
				}
				
				return userSession;
			}
		}
		
		return null;
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
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
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
