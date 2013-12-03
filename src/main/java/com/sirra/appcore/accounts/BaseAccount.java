package com.sirra.appcore.accounts;

import java.util.*;

import javax.persistence.*;

import org.hibernate.annotations.*;

/**
 * Each Sirra App will have an Account entity, which should extend this class.
 * 
 * Accounts also help keep data isolated in multi-tenant architecture.
 *  
 * @author aris
 */
@MappedSuperclass
public abstract class BaseAccount {

	protected int id;
	
	// Comes from AccountStatus
	protected String status;
	
	protected String name;
	protected Date creationDate;
	
	protected Date trialEndDate;
	protected Date subscribedDate;
	protected Date cancelledDate;
	protected Date SuspendedDate;

	// Defined in Stripe subscriptions
	protected String planId;
	
	protected String stripeCustomerId;
	
	// Only one active credit card at a time.
	protected String creditCardId;
	
	protected int nextTransactionNumber;
	
	protected Date nextBillDate;
	
	public BaseAccount() {
		creationDate = new Date();
		nextTransactionNumber = 1;
	}

	public BaseAccount(String id) {
		this.id = Integer.parseInt(id);
	}
	
	/**
	 * You can manually set your own ID if needed. Don't use 0 - that is not a valid ID. Negative numbers are okay.
	 * 
	 * @return
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SequenceIfNotBlank")
	@GenericGenerator(name="SequenceIfNotBlank",
	                  strategy="com.sirra.server.persistence.SequenceGeneratorIfNotBlank"
	)
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public Date getTrialEndDate() {
		return trialEndDate;
	}

	public void setTrialEndDate(Date trialEndDate) {
		this.trialEndDate = trialEndDate;
	}

	public Date getSubscribedDate() {
		return subscribedDate;
	}

	public void setSubscribedDate(Date subscribedDate) {
		this.subscribedDate = subscribedDate;
	}

	public Date getCancelledDate() {
		return cancelledDate;
	}

	public void setCancelledDate(Date cancelledDate) {
		this.cancelledDate = cancelledDate;
	}

	public Date getSuspendedDate() {
		return SuspendedDate;
	}

	public void setSuspendedDate(Date suspendedDate) {
		SuspendedDate = suspendedDate;
	}

	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	
	public String getStripeCustomerId() {
		return stripeCustomerId;
	}
	public void setStripeCustomerId(String stripeCustomerId) {
		this.stripeCustomerId = stripeCustomerId;
	}
	
	public String getCreditCardId() {
		return creditCardId;
	}
	public void setCreditCardId(String creditCardId) {
		this.creditCardId = creditCardId;
	}
	
	public int getNextTransactionNumber() {
		return nextTransactionNumber;
	}
	public void setNextTransactionNumber(int nextTransactionNumber) {
		this.nextTransactionNumber = nextTransactionNumber;
	}

	public Date getNextBillDate() {
		return nextBillDate;
	}
	public void setNextBillDate(Date nextBillDate) {
		this.nextBillDate = nextBillDate;
	}
}
