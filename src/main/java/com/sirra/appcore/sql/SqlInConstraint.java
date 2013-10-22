package com.sirra.appcore.sql;

import java.lang.reflect.*;
import java.util.*;

/**
 * Helps build the "IN" constraint string for anyone that needs it.
 * 
 * @author Aris
 */
public class SqlInConstraint {
	
	/**
	 * Does not include the surrounding parenthesis ().
	 */
	public static String build(Collection<String> idCollection) {
		if(idCollection == null) return "''";
		if(idCollection.size() == 0) return "''";
		
		StringBuffer constraint = new StringBuffer();
		
		Iterator<String> it = idCollection.iterator();
		while(it.hasNext()) {
			String id = it.next();
			
			constraint.append(quoteAndEscape(id));
			
			if(it.hasNext()) {
				constraint.append(", ");
			}
		}
		
		return constraint.toString();
	}
	
	public static String quoteAndEscape(String incoming) {
		if(incoming.indexOf("'") < 0 && incoming.indexOf("?") < 0) return "'" + incoming + "'";
		else {
			String temp = "E'" + incoming.replaceAll("'", "\\\\'") + "'";
			return temp.replaceAll("\\?", "\\\\?");
		}
	}
}