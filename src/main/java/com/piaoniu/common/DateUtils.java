/**
 * Copyright (c) 2010-2015 by Shanghai HanTao Information Co., Ltd.
 * All rights reserved.
 */
package com.piaoniu.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.apache.commons.lang3.time.FastDateFormat;
import org.joda.time.DateTime;

/**
 * 日期工具类
 *
 * @author yan.liu
 *
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

	public static final String RFC822_FORMAT = "EEE, d MMM yyyy HH:mm:ss Z";

	public static final FastDateFormat RFC822_FORMATTER = FastDateFormat.getInstance(RFC822_FORMAT, Locale.US);

	public static final FastDateFormat DEFAULT_DAY_FORMATTER = FastDateFormat.getInstance("yyyy-MM-dd");

	public static final FastDateFormat CHINESE_DAY_FORMATTER = FastDateFormat.getInstance("yyyy年MM月dd日");

	public static final FastDateFormat CHINESE_DAY_FORMATTER_ALL = FastDateFormat.getInstance("yyyy年MM月dd日HH:mm");


	private static final String[] CHINESE_MONTHS = { "一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二" };

	public static final FastDateFormat MonthMarkFormat = FastDateFormat.getInstance("yyMM");

	public static final long HOURSTIME = 60*60*1000;

	public  static final long DAYTIME = 24*HOURSTIME;

	public static final int WEEKDAYS = 7;

	public static final long WEEkTIME = WEEKDAYS*DAYTIME;


	public static List<Date> allDaysDuring(Date start, Date end) {
		return allPeriodDuring(start,end, PeriodType.DAILY);
	}

	public static List<Date> allMonthDuring(Date start, Date end) {
		return allPeriodDuring(start,end,PeriodType.MONTHLY);
	}

	public static List<Date> allYearDuring(Date start,Date end){
	    return allPeriodDuring(start,end,PeriodType.YEARLY);
	}

	public static DateTime beginOfPeriod(DateTime timeCalc, PeriodType periodType){
		switch (periodType){
			case YEARLY:
				return timeCalc.dayOfYear().withMinimumValue().withTimeAtStartOfDay();
			case QUARTER:
				return timeCalc.dayOfMonth().withMinimumValue().withTimeAtStartOfDay()
						.withMonthOfYear((((timeCalc.getMonthOfYear() - 1) / 3) * 3) + 1);
			case MONTHLY:
				return timeCalc.dayOfMonth().withMinimumValue().withTimeAtStartOfDay();
			case WEEKLY:
				return timeCalc.dayOfWeek().withMinimumValue().withTimeAtStartOfDay();
			default:case DAILY:
				return timeCalc.withTimeAtStartOfDay();
		}
	}

	public static DateTime minusPeriod(DateTime timeCalc, PeriodType periodType,int number){
		switch (periodType) {
			case YEARLY:
			    return timeCalc.minusYears(number);
			case QUARTER:
				return timeCalc.minusMonths(number * 3);
			case MONTHLY:
				return timeCalc.minusMonths(number);
			case WEEKLY:
				return timeCalc.minusWeeks(number);
			default:
			case DAILY:
				return timeCalc.minusDays(number);
		}
	}

	public static DateTime plusPeriod(DateTime timeCalc, PeriodType periodType,int number){
		switch (periodType) {
			case YEARLY:
				return timeCalc.plusYears(number);
			case MONTHLY:
				return timeCalc.plusMonths(number);
			case WEEKLY:
				return timeCalc.plusWeeks(number);
			case QUARTER:
				return timeCalc.plusMonths(number * 3);
			default:
			case DAILY:
				return timeCalc.plusDays(number);
		}
	}

	public static List<Date> allPeriodDuring(Date start, Date end, PeriodType periodType) {
		List<Date> dates = new ArrayList<>();
		DateTime dateTime = beginOfPeriod(new DateTime(start), periodType);
		DateTime endTime = new DateTime(end);
		while (!dateTime.isAfter(endTime)){
			dates.add(dateTime.withTimeAtStartOfDay().toDate());
			dateTime = plusPeriod(dateTime, periodType, 1);
		}
		return dates;
	}

	public static GeneralPair<DateTime, DateTime> getPeriod(PeriodType periodType, DateTime timeCalc) {
		DateTime thisPeriod = beginOfPeriod(timeCalc, periodType);
		DateTime lastPeriod = beginOfPeriod(minusPeriod(timeCalc, periodType, 1), periodType);
		return new GeneralPair<>(lastPeriod, thisPeriod);
	}

	/**
	 * get current date
	 *
	 * @return
	 */
	public static Date currentDate() {
		return Calendar.getInstance().getTime();
	}

	/**
	 * get duration in second between to dates
	 *
	 * @param from
	 * @param to
	 * @return
	 */
	public static long getDurationInSecond(Date from, Date to) {
		if (from == null || to == null) {
			throw new IllegalArgumentException("from and to must not be null.");
		}
		return Math.abs(from.getTime() - to.getTime()) / MILLIS_PER_SECOND;
	}

	public static long getSecondsFromNow(Date date) {
		return getDurationInSecond(currentDate(), date);
	}

	public static Date parseUseDefaultFormat(String date) {
		return parse(date, getDayFormatter());
	}

	public static Date parse(String date, DateFormat df) {
		try {
			return df.parse(date);
		} catch (ParseException e) {
			throw new RuntimeException("parse date [" + date + "] failed in use [" + getDayFormatter() + "]", e);
		}
	}


	public static String format(Date date, DateFormat df) {
		if (date == null) {
			return "";
		} else if (df != null) {
			return df.format(date).toLowerCase();
		} else {
			return DEFAULT_DAY_FORMATTER.format(date);
		}
	}

	public static String getDayTime(Date start) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
		return simpleDateFormat.format(start);
	}

	public static String range(Date start, Date end) {
		return getDayTime(start) + " - " + getDayTime(end);
	}

	public static String getChineseMonth(int month) {
		if (month >= 1 && month <= 12) {
			return CHINESE_MONTHS[month - 1];
		}
		throw new IllegalArgumentException("Month must between 1 and 12, but is " + month);
	}

	/**
	 * get previous month mark for the input month mark, as 1006=>1005
	 *
	 * @param monthMark
	 * @return
	 */
	public static String getPrevMonthMark(String monthMark) {
		SimpleDateFormat monthFormat = new SimpleDateFormat("yyMM");
		Date monthDate = null;
		try {
			monthDate = monthFormat.parse(monthMark);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Invalid month mark[" + monthMark + "].", e);
		}
		Date prevMonthDate = org.apache.commons.lang3.time.DateUtils.addMonths(monthDate, -1);
		return MonthMarkFormat.format(prevMonthDate);
	}

	/**
	 * 返回RFC822格式的日期字符串,使用系统默认Locale
	 *
	 * @param date
	 * @return
	 */
	public static String rfc822Format(Date date) {
		return FastDateFormat.getInstance(RFC822_FORMAT).format(date);
	}

	public static String rfc822Format(Date date, Locale locale) {
		return FastDateFormat.getInstance(RFC822_FORMAT, locale).format(date);
	}

	/**
	 * 得到某一天的该星期的第一日 00:00:00
	 *
	 * @param date
	 * @param firstDayOfWeek
	 *            本周一个星期的第一天为星期几
	 *
	 * @return
	 */

	public static Date getFirstDayOfWeek(Date date, int firstDayOfWeek) {
		DateTime dateTime = new DateTime(date);
		if(dateTime.getDayOfWeek() >= firstDayOfWeek){
			return dateTime.withDayOfWeek(firstDayOfWeek).withMillisOfDay(0).toDate();
		}else {
			return getPreDay(dateTime.withDayOfWeek(firstDayOfWeek).withMillisOfDay(0).toDate(),7);
		}
	}


	/**
	 * 得到某一天的该星期的第一日 00:00:00
	 *
	 * @param date
	 * @param
	 *
	 *
	 * @return
	 */

	public static Date getDayStart(Date date) {
		DateTime dateTime = new DateTime(date);
		return dateTime.withMillisOfDay(0).toDate();
	}

	/*
	*参考与2015年1月2日  到  2015 年1月9日算第一周
		return 上周
	 */
	public static long getRefWeek(Date now,int firstDayOfWeek){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date ref = null;
		try {
			ref = simpleDateFormat.parse("2015-01-02");
		}catch (ParseException e){
			e.printStackTrace();
		}
		if(ref == null){
			return -1;
		}else {
			long duration  =getDurationInSecond(getFirstDayOfWeek(now, firstDayOfWeek), ref);
			 return duration*1000/(WEEkTIME);
		}
	}

	/*
	*参考与2015年1月1日  到  2015 年1月31日算第一月
		return 当前月
	 */
	public static int getRefMonth(Date now){
		if(now == null){
			return -1;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date ref = null;
		try {
			ref = simpleDateFormat.parse("2015-01-01");
		}catch (ParseException e){
			e.printStackTrace();
		}
		if(ref == null){
			return -1;
		}else {
			return allMonthDuring(ref,now).size();
		}
	}

	public static Date getStartOfMonth(Date date){
		 return new DateTime(date).withDayOfMonth(1).withTimeAtStartOfDay().toDate();
	}

	public static Date getPreMonth(Date date ,int pre){
		return new DateTime(date).minusMonths(pre).toDate();
	}

	public static Date getPreDay(Date date , int pre){
		return new DateTime(date).minusDays(pre).toDate();
	}

	public static Date getDate(String simpleDate){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date ref = null;
		try {
			ref = simpleDateFormat.parse(simpleDate);
		}catch (ParseException e){
			e.printStackTrace();
		}
		return ref;
	}



	public static void main (String args[]){
		//System.out.println(getPreDay(new Date(),-1));
	//	DateTime dateTime = new DateTime();
		//System.out.println(getFirstDayOfWeek(new Date(),1));
		//System.out.println(addWeeks(getFirstDayOfWeek(new Date(),1),1));

		//	System.out.println(getPreMonth(new Date(),2));
	/*	List<Date> list = allMonthDuring(ref,new Date());
		System.out.println(getFirstDayOfWeek(new Date(),5));
		//DateTime dateTime = new DateTime(new Date());
		System.out.println(dateTime.withDayOfWeek(1));*/
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

		try {
			System.out.println(simpleDateFormat.parse("20150101"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public static SimpleDateFormat getDayFormatter() {
		return new SimpleDateFormat("yyyy-MM-dd");
	}

	public static SimpleDateFormat getMinuteFormatter() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm");
	}

	public static SimpleDateFormat getMonthFormatter() {
		return new SimpleDateFormat("yyyy-MM");
	}

	public static SimpleDateFormat getSecondFormatter() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}

	public static SimpleDateFormat getMilliSecondFormatter() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
	}

	public static SimpleDateFormat getTimeFormatter() {
		return new SimpleDateFormat("HH:mm");
	}

	public static SimpleDateFormat getNoYearDateFormatter() {
		return new SimpleDateFormat("MM-dd");
	}

	public static SimpleDateFormat getNoYearTimeFormatter() {
		return new SimpleDateFormat("MM-dd HH:mm");
	}

	public static SimpleDateFormat getYearFormatter() {
		return new SimpleDateFormat("yyyy");
	}
	public static SimpleDateFormat getSpecialFormatter() {
		return new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
	}

}
