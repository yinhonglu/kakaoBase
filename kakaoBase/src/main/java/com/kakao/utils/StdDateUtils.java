package com.kakao.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * @author yhl jyb jyb_96@sina.com
 * @version V1.0
 * @Description:时间处理通用类
 * @date 16-4-20 18:54
 * @copyright www.tops001.com
 */
public final class StdDateUtils {
    public static final String DEFAUL_PARSE = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYYMMDD_HHmmss = "yyyy-MM-dd HH:00:00";
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String MM_DD = "MM-dd";
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYYMMDD_CN = "yyyy年MM月dd日";
    public static final String HH_MM_SS = "HH:mm:ss";
    public static final String HHMMSS = "HHmmss";
    public static final String YYYYMMDD_HHMMSS = "yyyyMMdd_HHmmss";
    public static final String YYYYMMDD_HHMM = "yyyyMMdd_HHmm";
    public static final String YYYYMMDDHH = "yyyyMMddHH";
    public static final String YYYYMMDDHH_CN = "yyyy年MM月dd日HH时";
    public static final String YYYYMMDDHHMM = "yyyyMMddHHmm";
    public static final String YYYYMMDDHHDZ = "yyyyMMddHH0000";

    /**
     * @param date
     * @return 统一的时间解析器。即对未知格式的解析 正常的网络时间格式是yyyy-MM-dd HH:mm:ss 这里有对非正常时间格式的支持。
     * <p>
     * 这里增加了空保护，如果为空了，则直接返回当前时间对象
     */
    public static Date parseStringToDate(String date) {
        Date result = null;
        String parse = date;
        parse = parse.replaceFirst("^[0-9]{4}([^0-9]?)", "yyyy$1");
        parse = parse.replaceFirst("^[0-9]{2}([^0-9]?)", "yy$1");
        parse = parse.replaceFirst("([^0-9]?)[0-9]{1,2}([^0-9]?)", "$1MM$2");
        parse = parse.replaceFirst("([^0-9]?)[0-9]{1,2}( ?)", "$1dd$2");
        parse = parse.replaceFirst("( )[0-9]{1,2}([^0-9]?)", "$1HH$2");
        parse = parse.replaceFirst("([^0-9]?)[0-9]{1,2}([^0-9]?)", "$1mm$2");
        parse = parse.replaceFirst("([^0-9]?)[0-9]{1,2}([^0-9]?)", "$1ss$2");

        SimpleDateFormat format = new SimpleDateFormat(parse);
        try {
            result = format.parse(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            result = new Date();
        }
        // 增加空的保护
        return result == null ? new Date() : result;
    }

    /**
     * @return 当前时间的毫秒值
     * E.g., at 10:04:15.250 PM the getMillSec() is 250.
     */
    public static int getMillSec() {
        return getCalendar(getCurrentDate()).get(Calendar.MILLISECOND);
    }

    /**
     * @param date
     * @return date时间的毫秒值
     */
    public static int getMillSec(Date date) {
        return getCalendar(date).get(Calendar.MILLISECOND);
    }

    /**
     * @return 当前时间的秒值
     * E.g., at 10:04:15.250 PM the getSec() is 15.
     */
    public static int getSec() {
        return getCalendar(getCurrentDate()).get(Calendar.SECOND);
    }

    /**
     * @param date
     * @return date时间的秒值
     */
    public static int getSec(Date date) {
        return getCalendar(date).get(Calendar.SECOND);
    }

    /**
     * @return 当前时间的分钟值
     * E.g., at 10:04:15.250 PM the getMin() is 4.
     */
    public static int getMin() {
        return getCalendar(getCurrentDate()).get(Calendar.MINUTE);
    }

    /**
     * @param date
     * @return date时间的分钟值
     */
    public static int getMin(Date date) {
        return getCalendar(date).get(Calendar.MINUTE);
    }

    /**
     * @return 当前时间的小时值
     * E.g., at 10:04:15.250 PM the getHour() is 22.
     */
    public static int getHour() {
        return getCalendar(getCurrentDate()).get(Calendar.HOUR_OF_DAY);
    }

    /**
     * @param date
     * @return date时间的小时值
     */
    public static int getHour(Date date) {
        return getCalendar(date).get(Calendar.HOUR_OF_DAY);
    }

    /**
     * @return 当前时间的在当月的天数值
     */
    public static int getDay() {
        return getCalendar(getCurrentDate()).get(Calendar.DAY_OF_MONTH);
    }

    /**
     * @param date
     * @return date时间的在当月的天数值
     */
    public static int getDay(Date date) {
        return getCalendar(date).get(Calendar.DAY_OF_MONTH);
    }

    /**
     * @return 当前时间在本年的周数
     */
    public static int getWeek() {
        return getCalendar(getCurrentDate()).get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * @param date
     * @return date时间在本年的周数
     */
    public static int getWeek(Date date) {
        return getCalendar(date).get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * @return 当前时间在本年的第几天
     */
    public static int getWeekDay() {
        return getCalendar(getCurrentDate()).get(Calendar.DAY_OF_WEEK);
    }

    /**
     * @param date
     * @return date时间在本年的第几天
     */
    public static int getWeekDay(Date date) {
        return getCalendar(date).get(Calendar.DAY_OF_WEEK);
    }

    /**
     * @return 今天是星期几，中文表示
     */
    public static String getWeekDayCN() {
        return getWeekDayCN(getCurrentDate());
    }

    /**
     * @param date
     * @return date时间是星期几，中文表示
     */
    public static String getWeekDayCN(Date date) {
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        int weekday = getWeekDay(date) - 1;
        if (weekday < 0)
            weekday = 0;
        return weekDays[weekday];
    }

    /**
     * @return 今天是星期几，英文表示
     */
    public static String getWeekDayEN() {
        return getWeekDayCN(getCurrentDate());
    }

    /**
     * @param date
     * @return date时间是星期几，英文表示
     */
    public static String getWeekDayEN(Date date) {
        String[] weekDays = {"SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THRUSDAY", "FRIDAY", "SATURDAY"};
        int weekday = getWeekDay(date) - 1;
        if (weekday < 0)
            weekday = 0;
        return weekDays[weekday];
    }

    /**
     * @return 当前时间在本年的第几月
     */
    public static int getMonth() {
        return (getCalendar(getCurrentDate()).get(Calendar.MONTH) + 1);
    }

    /**
     * @param date
     * @return date时间在本年的第几月
     */
    public static int getMonth(Date date) {
        return (getCalendar(date).get(Calendar.MONTH) + 1);
    }

    /**
     * @return 当前时间在的年份
     */
    public static int getYear() {
        return getCalendar(getCurrentDate()).get(Calendar.YEAR);
    }

    /**
     * @param date
     * @return date时间在的年份
     */
    public static int getYear(Date date) {
        return getCalendar(date).get(Calendar.YEAR);
    }

    /**
     * @param date
     * @param withZeroFill
     * @return desc 取day的操作
     */
    public static String getDayOfMonth(Date date, boolean withZeroFill) {
        Calendar calendar = getCalendar(date);
        String day = (calendar.get(Calendar.DAY_OF_MONTH)) + "";
        return (day.length() == 1 && withZeroFill) ? ("0" + day) : day;
    }

    /**
     * @param date
     * @param withZeroFill 是否需要0进行补位操作
     * @return
     */
    public static String getMonth(Date date, boolean withZeroFill) {
        Calendar calendar = getCalendar(date);
        String month = (calendar.get(Calendar.MONTH) + 1) + "";
        return (month.length() == 1 && withZeroFill) ? ("0" + month) : month;
    }

    /**
     * @param date
     * @return calendar
     */
    public static Calendar getCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * @return the current time in milliseconds since January 1, 1970 00:00:00.0 UTC.
     */
    public static long getCurrentDateToLong() {
        return System.currentTimeMillis();
    }

    /**
     * @param date
     * @return this date as a millisecond value.
     */
    public static long getDateToLong(Date date) {
        return (date.getTime());
    }

    /**
     * @param time
     * @return date
     * Initializes this date instance using the specified millisecond value.
     */
    public static Date getDateByLongTime(long time) {
        return (new Date(time));
    }

    /**
     * @return 当前时间yyyy-MM-dd HH:mm:ss时间格式的字符串表示
     * E.g., 2016-04-19 14:42:48
     */
    public static String getCurrentDateStr() {
        return getParseDateToStr(getCurrentDate(), DEFAUL_PARSE);
    }

    /**
     * @param date
     * @return date时间yyyy-MM-dd HH:mm:ss时间格式的字符串表示
     * E.g., 2016-04-19 14:42:48
     */
    public static String getParseDateToStr(Date date) {
        return getParseDateToStr(date, DEFAUL_PARSE);
    }

    /**
     * @param date
     * @param parsePattern
     * @return date时间以不同parsePattern时间格式的字符串表示
     * E.g., 2016-04-19 if parsePattern is yyyy-MM-dd.
     */
    public static String getParseDateToStr(Date date, String parsePattern) {
        Locale locale = Locale.CHINA;
        SimpleDateFormat dataformat = new SimpleDateFormat(parsePattern, locale);
        return dataformat.format(date);
    }

    /**
     * @return 得到当前时间的Date对象
     */
    public static Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * @param dateStr
     * @return 得到dateStr字符表示时间的Date对象，
     * dateStr格式为yyyy-MM-dd HH:mm:ss
     */
    public static Date getDate(String dateStr) throws ParseException {
        return getDate(dateStr, DEFAUL_PARSE);
    }

    /**
     * @param dateStr
     * @param parsePattern
     * @return 得到dateStr字符表示时间的Date对象，
     * dateStr格式为parsePattern表示
     */
    public static Date getDate(String dateStr, String parsePattern)
            throws ParseException {
        Locale locale = Locale.CHINA;
        SimpleDateFormat dataformat = new SimpleDateFormat(parsePattern, locale);
        Date date = dataformat.parse(dateStr);
        return date;
    }

    /**
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param min
     * @param sec
     * @return 通过参数得到指定时间的Date对象
     */
    public static Date getDate(int year, int month, int day, int hour, int min,
                               int sec) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, (month - 1), day, hour, min, sec);
        return calendar.getTime();
    }

    /**
     * @param dateStr
     * @return dateStr字符串表示的时间得到和最近时间比较后修饰词的表示
     * dateStr格式为yyyy-MM-dd HH:mm:ss
     */
    public static String getSpecialTime(String dateStr) throws ParseException {
        Date date = getDate(dateStr, DEFAUL_PARSE);
        return getSpecialTime(date);
    }

    /**
     * @param dateStr
     * @return dateStr字符串表示的时间得到和最近时间比较后修饰词的表示
     * dateStr格式为parsePattern表示
     */
    public static String getSpecialTime(String dateStr, String parsePattern) throws ParseException {
        Date date = getDate(dateStr, parsePattern);
        return getSpecialTime(date);
    }

    /**
     * @param date
     * @return date对象表示的时间得到和最近时间比较后修饰词的表示
     */
    public static String getSpecialTime(Date date) {
        Date beforeDate = date;
        Date currentDate = getCurrentDate();

        long between = (currentDate.getTime() - beforeDate.getTime()) / 1000;
        long day = between / (24 * 3600);
        long hour = between % (24 * 3600) / 3600;
        long minute = between % 3600 / 60;
        if (between < 0) {
            return getParseDateToStr(date, DEFAUL_PARSE);
        } else if (day > 365 || day == 365) {
            return getParseDateToStr(date, YYYY_MM_DD);
        } else if (day > 1 || day == 1) {
            return getParseDateToStr(date, MM_DD);
        } else if (hour > 1 || hour == 1) {
            return hour + "小时前";
        } else if (minute > 1 || minute == 1) {
            return minute + "分钟前";
        } else {
            return "刚刚";
        }
    }
    /**
     * @param startDate
     * @param endDate
     * @return startDate和endDate之间的天数
     */
    public static int getGapDay(Date startDate, Date endDate) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime()
                .getTime()) / (1000 * 60 * 60 * 24));
    }

    /**
     * 本类使用的例子和测试
     */
    public static void usageAndTest() {
        String TAG = "StdDateUtils";

        Log.v(TAG, "getMillSec(): " + getMillSec());
        Log.v(TAG, "getMillSec(getCurrentDate()): " + getMillSec(getCurrentDate()));

        Log.v(TAG, "getSec(): " + getSec());
        Log.v(TAG, "getSec(getCurrentDate()): " + getSec(getCurrentDate()));

        Log.v(TAG, "getMin(): " + getMin());
        Log.v(TAG, "getMin(getCurrentDate()): " + getMin(getCurrentDate()));

        Log.v(TAG, "getHour(): " + getHour());
        Log.v(TAG, "getHour(getCurrentDate()): " + getHour(getCurrentDate()));

        Log.v(TAG, "getDay(): " + getDay());
        Log.v(TAG, "getDay(getCurrentDate()): " + getDay(getCurrentDate()));

        Log.v(TAG, "getWeek(): " + getWeek());
        Log.v(TAG, "getWeek(getCurrentDate()): " + getWeek(getCurrentDate()));

        Log.v(TAG, "getWeekDay(): " + getWeekDay());
        Log.v(TAG, "getWeekDay(getCurrentDate()): " + getWeekDay(getCurrentDate()));

        Log.v(TAG, "getWeekDayCN(): " + getWeekDayCN());
        Log.v(TAG, "getWeekDayCN(getCurrentDate()): " + getWeekDayCN(getCurrentDate()));

        Log.v(TAG, "getWeekDayEN(): " + getWeekDayEN());
        Log.v(TAG, "getWeekDayEN(getCurrentDate()): " + getWeekDayEN(getCurrentDate()));

        Log.v(TAG, "getMonth(): " + getMonth());
        Log.v(TAG, "getMonth(getCurrentDate()): " + getMonth(getCurrentDate()));

        Log.v(TAG, "getYear(): " + getYear());
        Log.v(TAG, "getYear(getCurrentDate()): " + getYear(getCurrentDate()));

        Log.v(TAG, "getCurrentDateToLong(): " + getCurrentDateToLong());
        Log.v(TAG, "getDateToLong(getDateByLongTime(getCurrentDateToLong())): " + getDateToLong(getDateByLongTime(getCurrentDateToLong())));


        Log.v(TAG, "getCurrentDateStr(): " + getCurrentDateStr());
        Log.v(TAG, "getParseDateToStr(getCurrentDate()): " + getParseDateToStr(getCurrentDate()));
        Log.v(TAG, "getParseDateToStr(getCurrentDate(), YYYYMMDDHHMMSS): " + getParseDateToStr(getCurrentDate(), YYYYMMDDHHMMSS));

        try {
            Log.v(TAG, "getParseDateToStr(getDate(\"2016-04-19 14:42:48\")): " + getParseDateToStr(getDate("2016-04-19 14:42:48")));
        } catch (ParseException e) {
            Log.e(TAG, "Fail e:" + e);
        }

        Log.v(TAG, "getParseDateToStr(getDate(2016, 4, 19, 14, 42, 48)):  " + getParseDateToStr(getDate(2016, 4, 19, 14, 42, 48)));

        try {
            Log.v(TAG, "getSpecialTime(getCurrentDateStr()): " + getSpecialTime(getCurrentDateStr()));
            Log.v(TAG, "getSpecialTime(\"2016-04-19 14:42:48\"): " + getSpecialTime("2016-04-19 14:42:48"));
            Log.v(TAG, "getSpecialTime(\"2016-04-19 04:42:48\"): " + getSpecialTime("2016-04-19 04:42:48"));
            Log.v(TAG, "getSpecialTime(\"2016-04-18 14:42:48\"): " + getSpecialTime("2016-04-18 14:42:48"));
            Log.v(TAG, "getSpecialTime(\"2016-04-10 14:42:48\"): " + getSpecialTime("2016-04-10 14:42:48"));
            Log.v(TAG, "getSpecialTime(\"2016-03-19 14:42:48\"): " + getSpecialTime("2016-03-19 14:42:48"));
            Log.v(TAG, "getSpecialTime(\"2015-04-19 14:42:48\"): " + getSpecialTime("2015-04-19 14:42:48"));

            Log.v(TAG, "getSpecialTime(\"2015-04-13\", YYYY_MM_DD): " + getSpecialTime("2015-04-13", YYYY_MM_DD));
        } catch (ParseException e) {
            Log.e(TAG, "Fail e:" + e);
        }

    }
}
