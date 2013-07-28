package com.sirra.appcore.plans;

import java.util.*;

import com.sirra.appcore.util.*;

/**
 * Represents the various subscription plans for the end-user. The plan definition
 * is configured in your server bootstrap code with a call to configure(...);
 * 
 * @author aris
 */
public class Plan {

	protected static Plan defaultPlan;
	protected static List<Plan> plans;
	
	/**
	 * Call this in server bootstrap code.
	 */
	public static void configure(Plan defaultPlan, Plan... planArray) {
		plans = new ArrayList();
		
		Plan.defaultPlan = defaultPlan;
		
		for(Plan plan: planArray) {
			plans.add(plan);
		}
	}
	
	protected String planId;
	protected String planName;
	protected double monthlyAmount;
	protected int numberOfTrialDays;
	
	public Plan(String planId, String planName, int numberOfTrialDays, double monthlyAmount) {
		this.planId = planId;
		this.planName = planName;
		this.numberOfTrialDays = numberOfTrialDays;
		this.monthlyAmount = monthlyAmount;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public double getMonthlyAmount() {
		return monthlyAmount;
	}

	public void setMonthlyAmount(double monthlyAmount) {
		this.monthlyAmount = monthlyAmount;
	}
	
	public Date getDefaultTrialEndDate(Date accountCreationDate) {
		return DateUtil.addDays(accountCreationDate, numberOfTrialDays);
	}
	
	public static Plan get(String planId) {
		for(Plan plan: plans) {
			if(plan.getPlanId().equals(planId)) return plan;
		}
		throw new RuntimeException("Can't find plan with planId " + planId);
	}
	
	public static Plan getDefaultPlan() {
		return defaultPlan;
	}
}