package com.sirra.appcore.lookup;

import java.util.*;

import javax.persistence.*;

import com.sirra.appcore.accounts.*;
import com.sirra.server.session.*;

/**
 * Allows any app to store information as a key-value pair.
 * 
 * @author aris
 */
@Entity
@Table(name = "lookup")
public class Lookup extends AccountEnabled {
	
	protected Date creationDate;
	protected String key;
	protected String value;
	
	public Lookup() {
		creationDate = new Date();
		accountId = SirraSession.get().getAccountId();
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(Date date) {
		this.creationDate = date;
	}

	@Id
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
