package com.sirra.appcore.dailyprocess;

import java.util.*;

public class ProcessorList {
	 
	protected static List<DailyProcessor> list;
	
	/**
	 * Call this in your server bootstrap code.
	 * 
	 * @param processors
	 */
	public static void setDailyProcessors(DailyProcessor... processors) {
		list = new ArrayList();
		
		for(DailyProcessor processor: processors) {
			list.add(processor);
		}
	}
	
	public static List<DailyProcessor> getList() {
		return list;
	}
}