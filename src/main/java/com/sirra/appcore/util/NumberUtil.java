package com.sirra.appcore.util;

public class NumberUtil {
	private final static double epsilon = Math.pow(10, -4);
	
	public static int compareDouble(double val1, double val2)
	{
		double diff = val1 - val2;
		double absDiff = Math.abs(diff);

		if(diff == 0 || (absDiff > 0 && absDiff < epsilon)) {
			return 0;
		}
		else if(diff < 0) {
			return -1;
		} else {
			return 1;
		}
	}	
}
