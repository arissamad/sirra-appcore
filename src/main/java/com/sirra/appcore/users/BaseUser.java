package com.sirra.appcore.users;

import java.util.*;

import javax.persistence.*;

import org.apache.commons.codec.digest.*;
import org.hibernate.annotations.*;

import com.sirra.appcore.accounts.*;
import com.sirra.appcore.util.*;

/**
 * Each Sirra App will have a User entity, which should extend this class.
 * 
 * @author aris
 */
@MappedSuperclass
public class BaseUser extends AccountEnabled {
	
	protected String id;
	protected String facebookId;
	protected String name;
	protected String email;
	protected String password;
	protected String roleMetaId;
	
	protected String forgotPasswordHash;
	protected Date forgotPasswordDate;
	
	public BaseUser() { }
	public BaseUser(String id) { this.id = id; }
	
	/**
	 * You can manually set your own ID if desired.
	 * 
	 * @return
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY, generator="UuidIfNotBlank")
	@GenericGenerator(name="UuidIfNotBlank",
	                  strategy="com.sirra.server.persistence.UuidGeneratorIfNotBlank"
	)
    public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getFacebookId() {
		return facebookId;
	}
	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void encryptAndSetPassword(String password) {
		this.password = encryptPassword(password);
	}
	
	public boolean verifyPassword(String cleanTextPassword) {
		return this.password.equals(encryptPassword(cleanTextPassword));
	}
	
	public String getRoleMetaId() {
		return roleMetaId;
	}
	public void setRoleMetaId(String roleMetaId) {
		this.roleMetaId = roleMetaId;
	}
	
	public String getForgotPasswordHash() {
		return forgotPasswordHash;
	}
	public void setForgotPasswordHash(String forgotPasswordHash) {
		this.forgotPasswordHash = forgotPasswordHash;
	}
	
	public Date getForgotPasswordDate() {
		return forgotPasswordDate;
	}
	public void setForgotPasswordDate(Date forgotPasswordDate) {
		this.forgotPasswordDate = forgotPasswordDate;
	}
	
	private static String encryptPassword(String password) {
		return DigestUtils.shaHex(Salt.password(password));
	}
}
