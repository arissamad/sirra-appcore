package com.sirra.appcore.sql;

import java.util.*;

import org.hibernate.*;
import org.hibernate.criterion.*;

import com.ming.server.session.*;
import com.sirra.appcore.accounts.*;
import com.sirra.appcore.users.*;

/**
 * Assists with finding database entities.
 * 
 * @author aris
 */
public class Finder {

	protected static Class accountClass;
	protected static Class userClass;
	
	/**
	 * Call this in your server bootstrap code.
	 */
	public static void configure(Class accountClass, Class userClass) {
		Finder.accountClass = accountClass;
		Finder.userClass = userClass;
	}
	
	public BaseAccount getAccount() {
		MingSession ms = MingSession.get();
		if(ms.getAccountId() == null) {
			throw new RuntimeException("Finder requires accountId.");
		}
		return (BaseAccount) getSession().get(accountClass, Integer.parseInt(ms.getAccountId()));
	}

	public BaseUser getUser() {
		MingSession ms = MingSession.get();
		if(ms.getUserId() == null) {
			throw new RuntimeException("FinderImpl requires userId.");
		}
		return (BaseUser) getSession().get(userClass, ms.getUserId());
	}

	/**
	 * Search for an object that has a field with a matching value (equals match).
	 * 
	 * If there are multiple results, it returns the first (random) one.
	 * 
	 * Don't have to cast return object. Nice!
	 */
	public <T> T findByField(Class<T> entityClass, String fieldName, String value) {
		Criteria criteria = getSession().createCriteria(entityClass).add(Restrictions.eq(fieldName, value));
		
		List results = criteria.list();
		if(results.size() == 0) return null;
		else return (T) results.get(0);
	}
	
	/**
	 * Search by multiple equals critiera.
	 * If there are multiple results, it returns the first (random) one.
	 */
	public <T> T findByFields(Class<T> entityClass, Pair... fields) {
		Criteria criteria = getSession().createCriteria(entityClass);
		
		for(int i=0; i<fields.length; i++) {
			criteria.add(Restrictions.eq(fields[i].getKey(), fields[i].getValue()));
		}
		
		List results = criteria.list();
		if(results.size() == 0) return null;
		else return (T) results.get(0);
	}
	
	protected Session getSession() {
		MingSession ms = MingSession.get();
		return ms.getHibernateSession();
	}
}
