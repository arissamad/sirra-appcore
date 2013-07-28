package com.sirra.appcore.transactions;

/**
 * Used by Transaction to indicate the status of a credit-card charge.
 * 
 * @author aris
 */
public enum TransactionStatus {
	Succeeded("Succeeded"), 
	Failed("Failed");
	
	protected String status;
	
	private TransactionStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
}