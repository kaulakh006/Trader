package com.nagarro.nagp.ebroker.util;

import java.util.Calendar;

public class DateUtil {

	private DateUtil() {

	}

	private static final String SUNDAY = "SUNDAY";
	private static final String SATURDAY = "SATURDAY";

	public static boolean isValidPeriod() {
		Calendar c = Calendar.getInstance();
		if (c.get(Calendar.HOUR_OF_DAY) > 17 || c.get(Calendar.HOUR_OF_DAY) < 9 || (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
				|| (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)) {
			return false;
		}
		return true;
	}
}
