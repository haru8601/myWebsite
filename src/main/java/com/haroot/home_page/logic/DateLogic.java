package com.haroot.home_page.logic;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 日付クラス
 * @author sekiharuhito
 *
 */
public class DateLogic {
    /**
     * 現在時間の日本時間取得
     * @return 日本時間文字列
     */
	public static String getJSTDateStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));
		String dateStr = sdf.format(new Date());
		return dateStr;
	}
}
