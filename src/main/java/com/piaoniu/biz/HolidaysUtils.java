package com.piaoniu.biz;

import com.google.common.collect.Maps;
import com.piaoniu.biz.cache.service.impl.CacheManager;
import com.piaoniu.biz.holiday.entity.Holiday;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.joda.time.DateTime;

/**
 * //http://www.easybots.cn/api/holiday.php?d=20151001
 * @author code4crafter@gmail.com
 *         Date: 15/12/11
 *         Time: 下午7:29
 */
public class HolidaysUtils {

	public static final int START_HOUR_OF_WORKDAY = 8;
	private static volatile Map<String, String> map;

	public static final int WORKDAY = 0;
	public static final int WEEKEND = 1;
	public static final int HOLIDAY = 2;

	/**
	 * 0 不是节日 1 周末 2 特殊节日
	 *
	 * @return
	 */
	public static int checkIsHoliday(Date date) {
		String yyyyMMdd = getMap().get(DateFormatUtils.format(date, "yyyyMMdd"));
		if (yyyyMMdd == null) {
			return 0;
		} else {
			return Integer.parseInt(yyyyMMdd);
		}
	}

	/**
	 * 算上今天，不算上当天
	 */
	public static int getWorkdayFromNowToDate(Date date) {
		return getWorkdayBetween(new Date(), date);
	}

	/**
	 * 算上今天，不算上当天
	 */
	public static int getWorkdayBetween(Date startDate,Date endDate){
		DateTime start = trimStart(new DateTime(startDate));
		DateTime end = new DateTime(endDate).withTimeAtStartOfDay();
		int workDays = 0;
		while (start.isBefore(end)) {
			if (WORKDAY == HolidaysUtils.checkIsHoliday(start.toDate()))
				workDays++;
			start = start.plusDays(1);
		}
		return workDays;
	}

	private static DateTime backOneDay(DateTime date,AtomicInteger counter){
		date = date.minusDays(1).withTimeAtStartOfDay();
		if (WORKDAY == HolidaysUtils.checkIsHoliday(date.toDate())) {
			counter.decrementAndGet();
		}
		return date;
	}

	public static DateTime minusWorkDay(DateTime date, int day) {
		AtomicInteger counter = new AtomicInteger(day);
		if (date.getHourOfDay() < START_HOUR_OF_WORKDAY && WORKDAY == HolidaysUtils.checkIsHoliday(date.toDate())) {
			counter.decrementAndGet();
		}
		DateTime current = date;
		while (counter.get() > 0) {
			current = backOneDay(current, counter);
		}
		current = current.withTimeAtStartOfDay();
		return current;
	}

	private static DateTime forwardOneDay(DateTime date,AtomicInteger counter){
		date = date.plusDays(1).withTimeAtStartOfDay();
		if (WORKDAY == HolidaysUtils.checkIsHoliday(date.toDate())) {
			counter.decrementAndGet();
		}
		return date;
	}

	public static DateTime plusWorkDay(DateTime date, int day) {
		AtomicInteger counter = new AtomicInteger(day);
		if (date.getHourOfDay() < START_HOUR_OF_WORKDAY && WORKDAY == HolidaysUtils.checkIsHoliday(date.toDate())) {
			counter.decrementAndGet();
		}
		DateTime current = date;
		while (counter.get() > 0) {
			current = forwardOneDay(current, counter);
		}
		current = current.plusDays(1).withTimeAtStartOfDay();
		return current;
	}

	private static DateTime trimStart(DateTime date) {
		DateTime start;
		if (date.getHourOfDay() < START_HOUR_OF_WORKDAY) {
			start = date.withTimeAtStartOfDay();
		} else {
			start = date.plusDays(1).withTimeAtStartOfDay();
		}
		return start;
	}

	public static void main(String[] args) {
		DateTime parse1 = DateTime.parse("2016-03-23");
		DateTime parse2 = DateTime.parse("2016-03-23");
		//System.out.println(getWorkdayFromNowToDate(parse.toDate()));
		System.out.println(getWorkdayBetween(parse1.toDate(),parse2.toDate()));
	}

	public static Map<String, String> getMap() {
		if (map == null) {
			synchronized (HolidaysUtils.class) {
				if (map == null) {
					map = CacheManager.HOLIDAY_GETTER.apply(1).stream()
								.collect(Collectors.toMap(Holiday::getDate,Holiday::getType));
					if (map == null)
						map = Maps.newHashMap();
				}
			}
		}
		return map;
	}

}
