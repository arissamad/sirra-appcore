package com.sirra.appcore.sql;

import java.util.*;


/**
 * Specifically to specify additional columns that come frmo another table and not in a base entity's fields.
 * 
 * @author aris
 *
 */
public class ExtraColumns {
	protected List<String> columns;
	
	public ExtraColumns(String... cols) {
		columns = new ArrayList();
		for(int i=0; i<cols.length; i++) {
			columns.add(cols[i]);
		}
	}
	
	public List<String> getColumns() {
		return columns;
	}
}
