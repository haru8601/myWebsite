package com.haroot.home_page.logic;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateLogic {
	public static String getJSTDateStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));
		String dateStr = sdf.format(new Date());
		return dateStr;
	}
}
