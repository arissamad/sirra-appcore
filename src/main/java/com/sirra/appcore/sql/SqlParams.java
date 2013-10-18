package com.sirra.appcore.sql;

import java.util.*;

/**
 * Contains the SQL parameters like constraints, sort order, etc.
 * 
 * @author aris
 */
public class SqlParams {
	
	protected int startIndex = 0;
	protected int numItemsToRetrieve = 10;
	
	protected List<String> constraints;
	protected Map<String, List<String>> orConstraints;
	
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
