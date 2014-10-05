package com.sirra.appcore.dailyprocess;

import java.util.*;

import com.sirra.server.session.*;
import com.sirra.appcore.util.*;

/**
 * Kicks off the daily processes at the specified hour and minute.
 */
public class DailyProcess implements Runnable {
	
	protected static DailyProcess instance;
	
	public static void start(int hour, int minute) {
		if(instance == null) {
			instance = new DailyProcess(hour, minute);
		} else {
			System.out.println("DailyProcess already started.");
		}
	}
	
	public static void runNow() {
		instance.runNow = true;
		instance.thread.interrupt();
	}

	protected Thread thread;
	
	protected long randomDelay;
	protected int hour;
	protected int minute;
	
	protected String friendlyTime;
	protected boolean runNow = false;
	
	/**
	 * @param hour This is in 24 hours. 13 is 1pm.
	 * @param minute
	 */
	private DailyProcess(int hour, int minute) {
		this.hour = hour;
		this.minute = minute;

		// DailyProcess has a random delay between 0 and 5 seconds. This helps OncePerDay.canIRunToday() ensure only one core runs the DailyProcess.
		randomDelay = (long) (Math.random()*5000d);
		System.out.println("DailyProcess randomDelay: " + randomDelay);
		
		friendlyTime = pad(hour) + ":" + pad(minute);
		
		thread = new Thread(this);
		thread.start();
	}
	
	protected String pad(int num) {
		String n = num + "";
		if(n.length() == 1) return "0" + n;
		else return n;
	}
	
	public void run() {
		System.out.println("Daily Process has been scheduled.");
		
		while(true) {
			try {
				long millis = getNextScheduled();
				
				System.out.println("DailyProcess: Sleeping till " + friendlyTime);
				System.out.println("In milliseconds: " + millis);
				Thread.sleep(millis);
			} catch(InterruptedException e) {
				if(runNow) {
					runNow = false;
				} else {
					System.out.println("DailyProcess has been interrupted.");
					System.out.println("Exiting...");
					instance = null;
					return;
				}
			}
			
			System.out.println("DailyProcess: Waking up to run scheduled processes.");
			
			if(!OncePerDay.canIRunToday("daily-process")) {
				System.out.println("... but not running today because another core has run today's daily process already.");
				continue;
			}
			
			for(DailyProcessor dailyProcessor: ProcessorList.getList()) {
				
				try {
					System.out.println(" -- DailyProcess: Running " + dailyProcessor.getClass().getSimpleName());
					SirraSession.start();
					dailyProcessor.process();
					SirraSession.end();
				} catch(Exception e) {
					StackTrace.notify(e, "Problem with dailyProcessor: ");
					SirraSession.rollback();
				}
				
				
			}
		}
	}
	
	public long getNextScheduled() {
		// First, find out if the scheduled time for today has already passed
		Date currDate = new Date();
		
		Calendar runTimeToday = Calendar.getInstance();
		runTimeToday.setTime(currDate);
		
		runTimeToday.set(Calendar.HOUR_OF_DAY, hour);
		runTimeToday.set(Calendar.MINUTE, minute);
		runTimeToday.set(Calendar.SECOND, 0);
		
		Date runTimeTodayDate = runTimeToday.getTime();
		
		if(currDate.before(runTimeTodayDate)) {
			System.out.println("DailyProcess: Next runtime is today.");
		} else {
			// equals or after
			System.out.println("DailyProcess: Next runtime is tomorrow.");
			runTimeToday.add(Calendar.DATE, 1);
			runTimeTodayDate = runTimeToday.getTime();
		}
		
		long runTime = runTimeTodayDate.getTime() - currDate.getTime();
		
		if(false) {
			// Debugging
			runTime = 0;
		}
		
		runTime += randomDelay;
		
		return runTime;
	}
}