package com.sirra.appcore.dailyprocess;

import java.text.*;
import java.util.*;

import com.sirra.appcore.lookup.*;
import com.sirra.server.session.*;

/**
 * Assists DailyProcesses that need to run only once per day even though
 * there may be multiple server instances running.
 * 
 * Does this by setting a semaphore in the DB. Only one core can set it per day.
 */
public class OncePerDay {
	
	public static boolean canIRunToday(String dailyProcessMetaId) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String value = sdf.format(date);
		
		String key = dailyProcessMetaId;
		
		Lookup lookup = (Lookup) SirraSession.get().getHibernateSession().get(Lookup.class, key);
		
		System.out.println("Lookup is " + lookup);
		
		if(lookup == null) {
			lookup = new Lookup();
			lookup.setKey(key);
			lookup.setValue(value);
			
			SirraSession.get().getHibernateSession().saveOrUpdate(lookup);
			SirraSession.get().commitButLeaveRunnning();
			
			System.out.println("Committed lookup: " + lookup.getKey() + ": " + lookup.getValue());
			
			return true;
		} else {
			String currentValue = lookup.getValue();
			
			System.out.println("Current value: " + currentValue);
			
			if(value.equals(currentValue)) {
				// Already ran
				System.out.println("Already ran");
				return false;
			} else {
				lookup.setValue(value);
				SirraSession.get().getHibernateSession().saveOrUpdate(lookup);
				SirraSession.get().commitButLeaveRunnning();
				
				System.out.println("Resaved lookup.");
				return true;
			}
		}
	}
}
