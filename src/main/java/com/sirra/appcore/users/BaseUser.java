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
	
	protected Date creationDate;
	
	protected String id;
	protected String facebookId;
	protected String name;
	protected String email;
	protected String password;
	protected String roleMetaId;
	protected List<String> tags;
	
	protected String forgotPasswordHash;
	protected Date forgotPasswordDate;
	
	public BaseUser() { 
		creationDate = new Date();
	}
	
	public BaseUser(String id) {
		creationDate = new Date();
		this.id = id;
	}
	
	/**
	 * You can manually set your own ID if desired.
	 * 
	 * @return
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SequenceStringIfNotBlank")
	@GenericGenerator(name="SequenceStringIfNotBlank",
	                  strategy="com.sirra.server.persistence.SequenceStringGeneratorIfNotBlank"
	)
    public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
		
	public Date getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
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
	
	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
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
