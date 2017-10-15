package com.piaoniu.biz;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.joda.time.DateTime;

/**
 *
 * Created by Stephen Cai on 2017-05-26 11:18.
 */


public class RandomDateUtils {

	/**
	 * 生成随机时间
	 * @param startDate 起始时间
	 * @param endDate 结束时间
	 * @param totalNum 生成时间数量
	 * @return 时间List
	 */
	public static List<Date> genRandomDate(Date startDate, Date endDate, int totalNum){
		List<Date> dateList = Lists.newArrayList();

		if (startDate.compareTo(endDate) != -1) {
			return Collections.emptyList();
		}
		while (dateList.size() < totalNum){
			long randomDate = (long)(Math.random()*(endDate.getTime() - startDate.getTime())) +startDate.getTime();
			dateList.add(new Date(randomDate));
		}

		return dateList.stream().sorted(Comparator.comparing(Date::getTime)).collect(Collectors.toList());
	}

	public static List<Date> genBiologicalRandomDate(Date startDate, Date endDate, int totalNum){
		List<Date> dateList = Lists.newArrayList();

		if (startDate.compareTo(endDate) != -1) {
			return Collections.emptyList();
		}

		long interval = endDate.getTime() - startDate.getTime();
		DateTime startDateTime = new DateTime(startDate);
		if (startDateTime.getHourOfDay() > 1 && startDateTime.getHourOfDay() < 6) {
			startDateTime = startDateTime.hourOfDay().setCopy(7);
		}
		while (dateList.size() < totalNum){
			long randomDate = (long)(Math.random()* interval) +startDateTime.toDate().getTime();
			DateTime dateTime = new DateTime(randomDate);
			//正常人2点到7点是应该睡觉的
			if (dateTime.getHourOfDay() > 1 && dateTime.getHourOfDay() < 7) {
				continue;
			}
			dateList.add(new Date(randomDate));
		}

		return dateList.stream().sorted(Comparator.comparing(Date::getTime)).collect(Collectors.toList());
	}
}
