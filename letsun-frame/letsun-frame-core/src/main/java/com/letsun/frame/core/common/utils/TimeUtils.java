package com.letsun.frame.core.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 时间日期工具类
 * 
 * @author zhongy
 *
 */
public class TimeUtils {

	public static String YYYY = "yyyy";
	public static String YYYYMMDD = "yyyyMMdd";
	public static String YYYY_MM = "yyyy-MM";

	public static String YYYY_MM_DD = "yyyy-MM-dd";

	public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	private static Logger logger = LoggerFactory.getLogger(TimeUtils.class);

	public static String parseDateToStr(Date date, String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		String str = sdf.format(date);
		return str;
	}

	public static Date format(String dateStr, String dateFormat) {
		if (StringUtils.isNotBlank(dateStr)) {
			try {
				DateFormat format = new SimpleDateFormat(dateFormat);
				return format.parse(dateStr);
			} catch (ParseException e) {
				logger.error("时间日期转换异常", e);
			}
		}
		return null;
	}

	/**
	 * 获取当前时间需要传递时间格式
	 * @param dateFormat
	 * @return
	 */
	public static String getCurrentMonthFirst(String dateFormat) {
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 0);
		// 设置为1号,当前日期既为本月第一天
		c.set(Calendar.DAY_OF_MONTH, 1);
		String first = format.format(c.getTime());
		return first;
	}

	/**
	 * 获取当前时间需要传递时间格式
	 * @param dateFormat
	 * @return
	 */
	public static String getCurrentTime(String dateFormat) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		String str = sdf.format(date);
		return str;
	}

	public static Date addDay(Date date, Integer num) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		// 把日期往后增加一天.整数往后推,负数往前移动
		calendar.add(Calendar.DATE, num);
		return calendar.getTime();
	}

	/**
	 * 获取上一个月
	 * @param date
	 * @param month
	 * @return
	 * @throws ParseException
	 */
	public static String beforeMonth(Date date, Integer month) throws ParseException {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, month);
		Date dateTime = calendar.getTime();
		return TimeUtils.parseDateToStr(dateTime, TimeUtils.YYYY_MM);
	}

	/**
	 * 获得本周一与当前日期相差的天数
	 * @return
	 */
	public static int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek == 1) {
			return -6;
		} else {
			return 2 - dayOfWeek;
		}
	}

	/**
	 * 获取传入日期该周开始日期、周一
	 * @param date
	 * @param dateFormat
	 * @return
	 */
	public static Date getCurrentMonday() {
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return TimeUtils.format(preMonday, TimeUtils.YYYY_MM_DD);
	}

	/**
	 * 获取传入日期该周结束日期、周日
	 * @param date
	 * @param dateFormat
	 * @return
	 */
	public static Date getPreviousSunday() {
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return TimeUtils.format(preMonday, TimeUtils.YYYY_MM_DD);
	}

	/**
	 * 获取传入日期该月开始日期
	 * @param date
	 * @param dateFormat
	 * @return
	 */
	public static Date getMinMonthDate(String date, String dateFormat) {
		Calendar calendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			calendar.setTime(sdf.parse(date));
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
			String minMonthDate = sdf.format(calendar.getTime());
			return TimeUtils.format(minMonthDate, dateFormat);
		} catch (java.text.ParseException e) {
			logger.error("时间日期转换异常", e);
		}
		return null;
	}

	/**
	 * 获取传入日期该月结束日期
	 * @param date
	 * @param dateFormat
	 * @return
	 */
	public static Date getMaxMonthDate(String date, String dateFormat) {
		Calendar calendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			calendar.setTime(sdf.parse(date));
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			String maxMonthDate = sdf.format(calendar.getTime());
			return TimeUtils.format(maxMonthDate, dateFormat);
		} catch (java.text.ParseException e) {
			logger.error("时间日期转换异常", e);
		}
		return null;
	}

	/**
	 * 获取某段时这里写代码片间内的所有日期
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static List<String> findDates(String startDate, String endDate) {
		List<String> dateStr = new ArrayList<String>();
		if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
			Date begin = format(startDate, YYYY_MM_DD);
			Date end = format(endDate, YYYY_MM_DD);
			List<Date> dateList = new ArrayList<Date>();
			dateList.add(begin);
			Calendar calBegin = Calendar.getInstance();
			// 使用给定的 Date 设置此 Calendar 的时间
			calBegin.setTime(begin);
			Calendar calEnd = Calendar.getInstance();
			// 使用给定的 Date 设置此 Calendar 的时间
			calEnd.setTime(end);
			// 测试此日期是否在指定日期之后
			while (end.after(calBegin.getTime())) {
				// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
				calBegin.add(Calendar.DAY_OF_MONTH, 1);
				dateList.add(calBegin.getTime());
			}
			if (CollectionUtils.isNotEmpty(dateList)) {
				for (Date date : dateList) {
					dateStr.add(parseDateToStr(date, YYYY_MM_DD));
				}
			} else {
				dateStr.add(parseDateToStr(end, YYYY_MM_DD));
			}
		}
		return dateStr;
	}

	/**
	 * 获取某段时这里写代码片间内的所有日期
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static List<Integer> findDatesYmd(String startDate, String endDate) {
		List<Integer> dateStr = new ArrayList<Integer>();
		if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
			Date begin = format(startDate, YYYYMMDD);
			Date end = format(endDate, YYYYMMDD);
			List<Date> dateList = new ArrayList<Date>();
			dateList.add(begin);
			Calendar calBegin = Calendar.getInstance();
			// 使用给定的 Date 设置此 Calendar 的时间
			calBegin.setTime(begin);
			Calendar calEnd = Calendar.getInstance();
			// 使用给定的 Date 设置此 Calendar 的时间
			calEnd.setTime(end);
			// 测试此日期是否在指定日期之后
			while (end.after(calBegin.getTime())) {
				// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
				calBegin.add(Calendar.DAY_OF_MONTH, 1);
				dateList.add(calBegin.getTime());
			}
			if (CollectionUtils.isNotEmpty(dateList)) {
				for (Date date : dateList) {
					dateStr.add(Integer.valueOf(parseDateToStr(date, YYYYMMDD)));
				}
			} else {
				dateStr.add(Integer.valueOf(parseDateToStr(end, YYYYMMDD)));
			}
		}
		return dateStr;
	}

	/**
	 * 获取某一个时间范围类的所有月份
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws ParseException
	 */
	public static List<String> findMonths(String startDate, String endDate) {
		List<String> dateStr = new ArrayList<String>();
		try {
			if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
				Date d1 = format(startDate, YYYY_MM);
				Date d2 = format(endDate, YYYY_MM);
				Calendar dd = Calendar.getInstance();
				dd.setTime(d1);
				while (dd.getTime().before(d2)) {
					SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM);
					String str = sdf.format(dd.getTime());
					dateStr.add(str);
					dd.add(Calendar.MONTH, 1);
				}
				if (CollectionUtils.isEmpty(dateStr)) {
					dateStr.add(parseDateToStr(d2, YYYY_MM));
				}
			}
		} catch (Exception e) {
			logger.error("时间日期转换异常", e);
		}
		return dateStr;
	}

	/**
	 * 获取过去第几天的日期
	 * 
	 * @param past
	 * @return
	 */
	public static String getPastDate(int past) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
		Date today = calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD);
		String result = format.format(today);
		return result;
	}

	/**
	 * 获取过去或者未来 任意天内的日期数组
	 * 
	 * @param intervals
	 *            intervals天内
	 * @return 日期数组
	 */
	public static ArrayList<String> getPastDateArray(int intervals) {
		ArrayList<String> pastDaysList = new ArrayList<>();
		for (int i = 0; i < intervals; i++) {
			pastDaysList.add(getPastDate(i));
		}
		return pastDaysList;
	}

	/**
	 * 获取最近7天的日期
	 * 
	 * @param days
	 * 
	 * @return
	 */
	public static List<String> getSevenDay(int days) {
		List<String> sevenDay = new ArrayList<>();
		for (int i = days; i < 1; i++) {
			Date dat = null;
			Calendar cd = Calendar.getInstance();
			cd.add(Calendar.DATE, i);
			dat = cd.getTime();
			SimpleDateFormat dformat = new SimpleDateFormat("MMdd");
			sevenDay.add(dformat.format(dat));
		}
		return sevenDay;
	}

	/**
	 * 计算两个日期之间相差的天数，此结果是自然天
	 * 
	 * @param fDate
	 * @param oDate
	 * @return
	 */
	public static int daysOfTwo(Date fDate, Date oDate) {

		Calendar aCalendar = Calendar.getInstance();

		aCalendar.setTime(fDate);

		int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);

		aCalendar.setTime(oDate);

		int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);

		return day2 - day1;

	}

	/**
	 * 计算两个时间之间相差的天，此结果是按满24小时算一天的算法
	 * 
	 * @param fDate
	 * 
	 * @param oDate
	 * 
	 * @return
	 */
	public static int getIntervalDays(Date fDate, Date oDate) {

		if (null == fDate || null == oDate) {

			return -1;

		}

		long intervalMilli = oDate.getTime() - fDate.getTime();

		return (int) (intervalMilli / (24 * 60 * 60 * 1000));

	}

	/**
	 * 得到几天后的日期
	 * 
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateAfter(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}

	/**
	 * 获取本周一和周日的日期
	 * 
	 * @return
	 */
	public static Map<String, Date> getWeekFirstAndLastDate() {
		Map<String, Date> map = new HashMap<String, Date>(10);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		// 获取本周一的日期
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		map.put("monday", cal.getTime());
		// 这种输出的是上个星期周日的日期，因为老外那边把周日当成第一天
		cal.add(Calendar.DATE, -1);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		// 增加一个星期，才是我们中国人理解的本周日的日期
		cal.add(Calendar.WEEK_OF_YEAR, 1);
		map.put("sunday", cal.getTime());
		return map;
	}

	/**
	 * 获取本月第一天和最后一天
	 * 
	 * @return
	 */
	public static Map<String, Date> getMothFirstAndLastDate() {
		Map<String, Date> map = new HashMap<String, Date>(10);
		// 获取当前月第一天：
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 0);
		// 设置为1号,当前日期既为本月第一天
		c.set(Calendar.DAY_OF_MONTH, 1);
		map.put("firstDate", c.getTime());
		// 获取当前月最后一天
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		map.put("lastDate", ca.getTime());
		return map;
	}

	/**
	 * 获取本年第一天和最后一天
	 * 
	 * @return
	 */
	public static Map<String, Date> getYearFirstAndLastDate() {
		Map<String, Date> map = new HashMap<String, Date>(10);
		Calendar currCal = Calendar.getInstance();
		int currentYear = currCal.get(Calendar.YEAR);
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, currentYear);
		Date currYearFirst = calendar.getTime();
        calendar.clear();
        calendar.set(Calendar.YEAR, currentYear);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();
		map.put("lastDate", currYearLast);
		map.put("firstDate", currYearFirst);
		return map;
	}

	/***
	 * 开始时间小于结束时间为true
	 * @param startTime
	 * @param endTime
	 */
	public static Boolean compareToDate(String beginTime,String endTime) {
		if(beginTime.compareTo(endTime)<0){
			return true;
		}else{
			return false;
		}
	}
}
