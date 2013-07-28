package com.sirra.appcore.util;

import java.util.*;

public class DateUtil {
	
	public static Date addHours(Date dateObject, int numberOfHoursToAdd) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateObject);
		cal.add(Calendar.HOUR, numberOfHoursToAdd);
		Date newDate = cal.getTime();
		
		return newDate;
	}
	
	public static Date addDays(Date dateObject, int numberOfDaysToAdd) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateObject);
		cal.add(Calendar.DATE, numberOfDaysToAdd);
		Date newDate = cal.getTime();
		
		return newDate;
	}
	
	public static Date addMonths(Date dateObject, int numberOfMonthsToAdd) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateObject);
		cal.add(Calendar.MONTH, numberOfMonthsToAdd);
		Date newDate = cal.getTime();
		
		return newDate;
	}
	
	public static Date addYears(Date dateObject, int numberOfYearsToAdd) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateObject);
		cal.add(Calendar.YEAR, numberOfYearsToAdd);
		Date newDate = cal.getTime();
		
		return newDate;
	}
}