package com.sirra.appcore.util;

import java.text.*;
import java.util.*;

/**
 * Helpful for formatting dates like "2012-01-01".
 * 
 * @author aris
 *
 */
public class PlainDate {
	protected String plainDateStr;
	
	public PlainDate(String plainDateStr) {
		this.plainDateStr = plainDateStr;
	}
	
	public PlainDate(Date date, String timezone) {
		this.plainDateStr = convertDate(date, timezone);
	}
	
	protected String convertDate(Date date, String timezone) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		if(timezone != null) {
			TimeZone timeZone = TimeZone.getTimeZone(timezone);
			sdf.setTimeZone(timeZone);
		}
		
		return sdf.format(date);
	}
	
	public void addDays(int numDays) {
		Date date = getServerMidnightDate();
		Date newDate = DateUtil.addDays(date, numDays);
		plainDateStr = convertDate(newDate, null);
	}

	public Date getServerMidnightDate() {
		int fullYear = Integer.parseInt(plainDateStr.substring(0, 4));
		int month = Integer.parseInt(plainDateStr.substring(5, 7));
		int dateInt = Integer.parseInt(plainDateStr.substring(8, 10));
		
		Date date = new Date(fullYear-1900, month-1, dateInt);
		
		return date;
	}
	
	public Date getTimezoneMidnightDate(String timezone) {
		SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		if(timezone != null) {
			TimeZone timeZone = TimeZone.getTimeZone(timezone);
			isoFormat.setTimeZone(timeZone);
		}
		
		try {
			Date date = isoFormat.parse(plainDateStr);
			return date;
		} catch(ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	public String toString() {
		return plainDateStr;
	}
}
