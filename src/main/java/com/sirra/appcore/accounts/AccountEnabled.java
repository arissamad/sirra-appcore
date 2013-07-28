package com.sirra.appcore.accounts;

import javax.persistence.*;

/**
 * Parent class of entities that need to be isolated in multi-tenant architecture.
 */
@MappedSuperclass
public abstract class AccountEnabled {
	protected String accountId;

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
}