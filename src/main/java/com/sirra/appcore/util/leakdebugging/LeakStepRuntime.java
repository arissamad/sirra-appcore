package com.sirra.appcore.util.leakdebugging;

public class LeakStepRuntime extends RuntimeException {
	
	public LeakStepRuntime(double step, String message) {
		System.out.println("+++++");
		System.out.println("LeakStep stopping at step " + step + ": " + message);
		System.out.println("+++++");
	}
}