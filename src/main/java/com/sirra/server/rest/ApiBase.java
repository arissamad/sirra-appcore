package com.sirra.server.rest;

import java.util.*;

import javax.servlet.http.*;

import com.sirra.appcore.accounts.*;
import com.sirra.appcore.sql.*;
import com.sirra.server.session.*;

/**
 * All API handler classes need to extend this class.
 * 
 * Simply annotate the appropriate methods with GET, POST, PUT or DELETE annotations.
 * Return any object that is supported by our JSON converter.
 * 
 * - You have access to the GET parameters via hasParameter() and getParameter().
 * - You have acccess to the PATH variables via the "pathParameters" variable.
 * - Also see ApiServlet for information on mapping parameters to API method parameters.
 * 
 * @author aris
 */
public class ApiBase extends HttpServlet
{
	protected String method; // GET or POST
	
	// The GET parameters
	protected Map<String, String> parameters;
	
	// A path of "/teachers/1234" will result in this being set to ["1234"].
	protected List<String> pathParameters; 
	
	protected ApiBase() {
		// Do nothing special during instantiation
	}
	
	protected Map<String, Object> fail(String message) {
		Map<String, Object> result = new HashMap();
		result.put("isSuccessful", false);
		result.put("error", message);
		
		return result;
	}
	
	public Map<String, Object> success() {
		Map<String, Object> result = new HashMap();
		result.put("isSuccessful", true);
		
		return result;
	}
	
	protected void save(Object object) {
		SirraSession ss = SirraSession.get();
		
		if(ss.getAccountId() != null && AccountEnabled.class.isInstance(object)) {
			AccountEnabled ae = (AccountEnabled) object;
			
			if(ae.getAccountId() == null) ae.setAccountId(ss.getAccountId());
		}
		
		ss.getHibernateSession().saveOrUpdate(object);
	}
	
	protected <T extends Object> T get(Class<T> clazz, String id) {
		SirraSession ms = SirraSession.get();
		return (T) ms.getHibernateSession().get(clazz, id);
	}
	
	protected void delete(Object object) {
		SirraSession ms = SirraSession.get();
		ms.getHibernateSession().delete(object);
	}

	// Convenience method to retrieve a GET parameter as a string
	protected String getParameter(String parameterName) {
		return parameters.get(parameterName);
	}
	
	protected void setParameterMap(Map<String, String> parameterMap) {
		this.parameters = parameterMap;
	}
	
	protected boolean hasParameter(String parameterName) {
		return parameters.containsKey(parameterName);
	}
	
	protected void setPathParameters(List<String> pathParameters) {
		this.pathParameters = pathParameters;
	}
	
	public List<String> getPathParameters() {
		return pathParameters;
	}
	
	private SqlParams sqlParams;
	
	/**
	 * searchFields ties into the built-in "_search" search term. It specifies
	 * that the SQL should search for the search term in the specified search fields.
	 */
	protected SqlParams getSqlParams(String... searchFields) {
		if(sqlParams == null) sqlParams = new SqlParams();
		
		if(parameters.containsKey("_search") && searchFields.length > 0) {
			List<String> searchFieldList = new ArrayList<String>();
			for(String searchField: searchFields) {
				searchFieldList.add(searchField);
			}
			sqlParams.setSearchFields(searchFieldList);
			sqlParams.setSearchTerm(parameters.get("_search"));
		}
		
		if(parameters.containsKey("_limit")) {
			sqlParams.setNumItemsToRetrieve(Integer.parseInt(parameters.get("_limit")));
		}

		if(parameters.containsKey("_page")) {
			int startIndex = Integer.parseInt(parameters.get("_page")) * sqlParams.getNumItemsToRetrieve();
			sqlParams.setStartIndex(startIndex);
			
			if(sqlParams.getStartIndex() < 0) sqlParams.setStartIndex(0);
		}
		
		return sqlParams;
	}
}