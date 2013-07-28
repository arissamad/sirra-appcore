package com.sirra.appcore.transactions;

import java.util.*;

import javax.persistence.*;

import com.sirra.server.session.*;
import com.sirra.appcore.accounts.*;

/**
 * A single charge on the user's credit card for billing purposes.
 * 
 * @author aris
 */
@Entity
@Table(name = "transactions")
public class Transaction extends AccountEnabled {
	
	protected String id;
	
	protected Date creationDate;
	
	protected String stripeTransactionId;
	protected String creditCardId;
	
	protected String transactionNumber;
	protected String description;
	
	protected double attemptedAmount;
	protected double finalAmount;
	
	// From TransactionStatus
	protected String status;
	protected String failureMessage;
	
	public Transaction() {
		creationDate = new Date();
		accountId = SirraSession.get().getAccountId();
		failureMessage = "";
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

	public String getStripeTransactionId() {
		return stripeTransactionId;
	}

	public void setStripeTransactionId(String stripeTransactionId) {
		this.stripeTransactionId = stripeTransactionId;
	}

	public String getCreditCardId() {
		return creditCardId;
	}

	public void setCreditCardId(String creditCardId) {
		this.creditCardId = creditCardId;
	}

	public String getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getAttemptedAmount() {
		return attemptedAmount;
	}

	public void setAttemptedAmount(double attemptedAmount) {
		this.attemptedAmount = attemptedAmount;
	}

	public double getFinalAmount() {
		return finalAmount;
	}

	public void setFinalAmount(double finalAmount) {
		this.finalAmount = finalAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFailureMessage() {
		return failureMessage;
	}

	public void setFailureMessage(String failureMessage) {
		this.failureMessage = failureMessage;
	}
}
