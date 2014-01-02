package com.sirra.appcore.util.leakdebugging;

/**
 * Memory leaks in Heroku are a bit difficult to identify. Use this class to help.
 * 
 * You can put various points where the LeakDebugger 
 * 
 * @author aris
 */
public class LeakDebugger {

	protected static double maxStepNumber = 10000;
	
	public static void setLimit(double maxStepNumber) {
		LeakDebugger.maxStepNumber = maxStepNumber;
	}
	
	public static void check(double stepNumber) {
		check(stepNumber, "");
	}
	
	public static void check(double stepNumber, String message) {
		if(stepNumber >= maxStepNumber) throw new LeakStepRuntime(stepNumber, message);
	}
	
}
