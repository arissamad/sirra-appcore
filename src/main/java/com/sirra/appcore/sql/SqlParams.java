package com.sirra.appcore.sql;

import java.util.*;

/**
 * Contains the SQL parameters like constraints, sort order, etc.
 * 
 * @author aris
 */
public class SqlParams {
	
	protected int startIndex = 0;
	
	// Default is flat list.
	protected int numItemsToRetrieve = 10000;
	
	protected List<String> constraints;
	protected Map<String, List<String>> orConstraints;
	
	// The following fields support the user searching by typing out a search term.
	protected List<String> searchFields;
	protected String searchTerm;
	
	protected List<Sort> sortColumns;
	
	public SqlParams() {
		constraints = new ArrayList();
		orConstraints = new HashMap();
		sortColumns = new ArrayList();
	}
	
	public void addConstraint(String constraint) {
		constraints.add(constraint);
	}
	
	public List<String> getAllConstraints() {
		List<String> allConstraints = new ArrayList();
		allConstraints.addAll(constraints);

		if(requiresSearch()) {
			orConstraints.remove("_search_term");
			for(String fieldName: searchFields) {
				addOrConstraint("_search_term", "LOWER(" + fieldName + ") LIKE '%" + searchTerm + "%'");
			}
		}
		
		for(List<String> orList: orConstraints.values()) {
			if(orList.size() == 0) continue;
			
			StringBuffer str = new StringBuffer();
			str.append("(");
			Iterator<String> orIterator = orList.iterator();
			while(orIterator.hasNext()) {
				String orConstraint = orIterator.next();
				str.append(orConstraint);
				
				if(orIterator.hasNext()) str.append(" OR ");
			}
			str.append(")");
			allConstraints.add(str.toString());
		}
		
		return allConstraints;
	}
	
	public void addOrConstraint(String groupName, String constraint) {
		if(!orConstraints.containsKey(groupName)) orConstraints.put(groupName, new ArrayList());
		
		orConstraints.get(groupName).add(constraint);
	}
	
	private boolean requiresSearch() {
		if(searchFields == null) return false;
		else if(searchTerm == null) return false;
		else if(searchTerm.equals("")) return false;
		else if(searchFields.size() == 0) return false;
		else return true;
	}
	
	public List<String> getSearchFields() {
		return searchFields;
	}

	public void setSearchFields(List<String> searchFields) {
		this.searchFields = searchFields;
	}

	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
		if(this.searchTerm != null) this.searchTerm = this.searchTerm.toLowerCase();
	}
	
	public String getSearchTerm(String searchTerm) {
		return searchTerm;
	}
	
	public void setSort(String columnName, SqlOrder sqlOrder) {
		if(sortColumns.size() > 0) {
			sortColumns.set(0, new Sort(columnName, sqlOrder));
		} else {
			sortColumns.add(new Sort(columnName, sqlOrder));
		}
	}
	
	public List<String> getSortColumns() {
		List<String> orderBys = new ArrayList();
		
		for(Sort sort: sortColumns) {
			orderBys.add(sort.getColumnName() + " " + sort.getSqlOrder().getSqlForm());
		}
		
		return orderBys;
	}
	
	public int getStartIndex() {
		return startIndex;
	}
	
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	
	public int getNumItemsToRetrieve() {
		return numItemsToRetrieve;
	}
	
	public void setNumItemsToRetrieve(int numItemsToRetrieve) {
		this.numItemsToRetrieve = numItemsToRetrieve;
	}
	
	private class Sort {
		protected String columnName;
		protected SqlOrder sqlOrder;
		
		public Sort(String columnName, SqlOrder sqlOrder) {
			this.columnName = columnName;
			this.sqlOrder = sqlOrder;
		}

		public String getColumnName() {
			return columnName;
		}

		public SqlOrder getSqlOrder() {
			return sqlOrder;
		}
	}
}
