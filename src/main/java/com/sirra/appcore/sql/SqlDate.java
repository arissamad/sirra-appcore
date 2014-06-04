package com.sirra.appcore.sql;

import java.text.*;
import java.util.*;

public class SqlDate {
	
	protected static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:00");
	
	public static String format(Date date) {
		return sdf.format(date);
	}
}
