package com.sirra.appcore.accounts;

import javax.persistence.*;

import com.sirra.appcore.util.*;

/**
 * Parent class of entities that need to be isolated in multi-tenant architecture.
 */
@MappedSuperclass
public abstract class AccountEnabled extends EntityBase {
	protected String accountId;

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
}