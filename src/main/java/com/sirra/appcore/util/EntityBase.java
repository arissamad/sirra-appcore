package com.sirra.appcore.util;

import java.util.*;

import javax.persistence.*;

/**
 * Allows entity objects to hold additional key/value pairs, which can then get transmitted to the front-end.
 * 
 * This allows us to return the entity object even with additional attributes.
 * 
 * @author aris
 */
public class EntityBase {
	
	@Hidden
	protected Map<String, Object> additionalParameters = new HashMap();
	
	@Transient
	public Map<String, Object> getAdditionalParameters() {
		return additionalParameters;
	}
	
	public void put(String key, Object value) {
		additionalParameters.put(key, value);
	}
	
	@Transient
	public Object get(String key) {
		return additionalParameters.get(key);
	}
}
