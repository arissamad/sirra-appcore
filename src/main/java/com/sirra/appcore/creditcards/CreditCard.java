package com.sirra.appcore.creditcards;

import java.util.*;

import javax.persistence.*;

import com.ming.server.session.*;
import com.sirra.appcore.accounts.*;

@Entity
@Table(name = "creditcards")
public class CreditCard extends AccountEnabled {
	
	protected String id;
	protected Date creationDate;
	
	protected String stripeCardId;
	
	protected String type;
	
	protected String name;
	protected String last4;
	
	protected int expMonth;
	protected int expYear;
	
	public CreditCard() {
		creationDate = new Date();
		accountId = MingSession.get().getAccountId();
	}

	@Id
	@GeneratedValue(generator = "uuid")
	@org.hibernate.annotations.GenericGenerator(name = "uuid", strategy = "uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getStripeCardId() {
		return stripeCardId;
	}

	public void setStripeCardId(String stripeCardId) {
		this.stripeCardId = stripeCardId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLast4() {
		return last4;
	}

	public void setLast4(String last4) {
		this.last4 = last4;
	}

	public int getExpMonth() {
		return expMonth;
	}

	public void setExpMonth(int expMonth) {
		this.expMonth = expMonth;
	}

	public int getExpYear() {
		return expYear;
	}

	public void setExpYear(int expYear) {
		this.expYear = expYear;
	}
}
