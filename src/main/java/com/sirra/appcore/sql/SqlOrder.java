package com.sirra.appcore.sql;

public enum SqlOrder {
	Ascending("ASC"), Descending("DESC");
	
	protected String sqlForm;
	
	private SqlOrder(String sqlForm) {
		this.sqlForm = sqlForm;
	}
	
	public String getSqlForm() {
		return sqlForm;
	}
}
