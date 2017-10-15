/**
 * Copyright (c) 2010-2015 by Shanghai HanTao Information Co., Ltd.
 * All rights reserved.
 */
package com.piaoniu.common;

import java.util.Date;
import java.util.Locale;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 日期工具类
 *
 * @author yan.liu
 *
 */
public abstract class PnDateUtils {

	public static final String PIAONIU_EVENT_FORMAT = "yyyy年MM月dd日 E HH:mm";

	public static final String PIAONIU_EVENT_FORMAT_WITHOUT_TIME="yyyy年MM月dd日 E";

	public static final String PIAONIU_EVENT_FORMAT_WITHOUT_TIME_WEEK = "yyyy年MM月dd日";

	public static String formatForActivityEventDate(Date date) {
		return DateFormatUtils.format(date, PIAONIU_EVENT_FORMAT, Locale.PRC).replace("星期", "周");
	}

	public static String formatForActivityEventDateWithOutTime(Date date){
		return DateFormatUtils.format(date, PIAONIU_EVENT_FORMAT_WITHOUT_TIME, Locale.PRC).replace("星期", "周");
	}

	public static String formatForActivityEventDateWithOutTimeWeek(Date date){
		return DateFormatUtils.format(date,PIAONIU_EVENT_FORMAT_WITHOUT_TIME_WEEK);
	}

	public static void main(String[] args) {
		System.out.println(formatForActivityEventDateWithOutTimeWeek(new Date()));
	}

}
