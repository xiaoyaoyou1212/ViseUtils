package com.vise.utils.assist;

import com.vise.log.ViseLog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * @Description:
 * @author: <a href="http://www.xiaoyaoyou1212.com">DAWI</a>
 * @date: 2017-01-12 13:17
 */
public class DateUtil {
    private static SimpleDateFormat m = new SimpleDateFormat("MM");
    private static SimpleDateFormat d = new SimpleDateFormat("dd");
    private static SimpleDateFormat md = new SimpleDateFormat("MM-dd");
    private static SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat ymdDot = new SimpleDateFormat("yyyy.MM.dd");
    private static SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat ymdhmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private static SimpleDateFormat ymdhm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static SimpleDateFormat hm = new SimpleDateFormat("HH:mm");
    private static SimpleDateFormat mdhm = new SimpleDateFormat("MM月dd日 HH:mm");
    private static SimpleDateFormat mdhmLink = new SimpleDateFormat("MM-dd HH:mm");

    public static String getYmd(long timeInMills) {
        return ymd.format(new Date(timeInMills));
    }

    public static String getYmdDot(long timeInMills) {
        return ymdDot.format(new Date(timeInMills));
    }

    public static String getYmdhms(long timeInMills) {
        return ymdhms.format(new Date(timeInMills));
    }

    public static String getYmdhmsS(long timeInMills) {
        return ymdhmss.format(new Date(timeInMills));
    }

    public static String getYmdhm(long timeInMills) {
        return ymdhm.format(new Date(timeInMills));
    }

    public static String getHm(long timeInMills) {
        return hm.format(new Date(timeInMills));
    }

    public static String getMd(long timeInMills) {
        return md.format(new Date(timeInMills));
    }

    public static String getMdhm(long timeInMills) {
        return mdhm.format(new Date(timeInMills));
    }

    public static String getMdhmLink(long timeInMills) {
        return mdhmLink.format(new Date(timeInMills));
    }

    public static String getM(long timeInMills) {
        return m.format(new Date(timeInMills));
    }

    public static String getD(long timeInMills) {
        return d.format(new Date(timeInMills));
    }

    public static Date getDateByFormat(String strDate, SimpleDateFormat format) {
        Date date = null;
        try {
            date = format.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            ViseLog.e(e);
        }
        return date;
    }

    public static String getStringByFormat(Date date, SimpleDateFormat format) {
        String strDate = null;
        try {
            strDate = format.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            ViseLog.e(e);
        }
        return strDate;
    }

    /**
     * 将UTC时间转换为本地时间
     *
     * @param utcTime
     * @param utcTimePatten
     * @param localTimePatten
     * @return 本地时间
     */
    public static String utc2Local(String utcTime, SimpleDateFormat utcTimePatten, SimpleDateFormat localTimePatten) {
        utcTimePatten.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date gpsUTCDate = null;
        try {
            gpsUTCDate = utcTimePatten.parse(utcTime);
        } catch (ParseException e) {
            e.printStackTrace();
            ViseLog.e(e);
        }
        localTimePatten.setTimeZone(TimeZone.getDefault());
        String localTime = localTimePatten.format(gpsUTCDate.getTime());
        return localTime;
    }


    /**
     * 将本地时间转换为UTC
     *
     * @param localTime
     * @param localTimePatten
     * @param utcTimePatten
     * @return UTC时间
     */
    public static String local2utc(String localTime, SimpleDateFormat localTimePatten, SimpleDateFormat utcTimePatten) {
        // 1、取得本地时间：
        Calendar cal = Calendar.getInstance();
        cal.setTime(DateUtil.getDateByFormat(localTime, localTimePatten));
        // 2、取得时间偏移量：
        int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
        // 3、取得夏令时差：
        int dstOffset = cal.get(Calendar.DST_OFFSET);
        // 4、从本地时间里扣除这些差量，即可以取得UTC时间：
        cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        int year = cal.get(Calendar.YEAR);
        //int month = cal.get(Calendar.MONTH) + 1;
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        cal.set(year, month, day, hour, minute);
        return DateUtil.getStringByFormat(cal.getTime(), utcTimePatten);
    }

    /**
     * 是否是今天
     *
     * @param timeInMills
     * @return
     */
    public static boolean isToday(long timeInMills) {
        String dest = getYmd(timeInMills);
        String now = getYmd(Calendar.getInstance().getTimeInMillis());
        return dest.equals(now);
    }

    /**
     * 是否是同一天
     *
     * @param aMills
     * @param bMills
     * @return
     */
    public static boolean isSameDay(long aMills, long bMills) {
        String aDay = getYmd(aMills);
        String bDay = getYmd(bMills);
        return aDay.equals(bDay);
    }

    /**
     * 获取年份
     *
     * @param mills
     * @return
     */
    public static int getYear(long mills) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mills);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取月份
     *
     * @param mills
     * @return
     */
    public static int getMonth(long mills) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mills);
        return calendar.get(Calendar.MONTH) + 1;
    }


    /**
     * 获取月份的天数
     *
     * @param mills
     * @return
     */
    public static int getDaysInMonth(long mills) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mills);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.MARCH:
            case Calendar.MAY:
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.OCTOBER:
            case Calendar.DECEMBER:
                return 31;
            case Calendar.APRIL:
            case Calendar.JUNE:
            case Calendar.SEPTEMBER:
            case Calendar.NOVEMBER:
                return 30;
            case Calendar.FEBRUARY:
                return (year % 4 == 0) ? 29 : 28;
            default:
                throw new IllegalArgumentException("Invalid Month");
        }
    }

    /**
     * 获取星期,0-周日,1-周一，2-周二，3-周三，4-周四，5-周五，6-周六
     *
     * @param mills
     * @return
     */
    public static int getWeek(long mills) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mills);
        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 获取当月第一天的时间（毫秒值）
     *
     * @param mills
     * @return
     */
    public static long getFirstOfMonth(long mills) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mills);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTimeInMillis();
    }

    public static long getStartTime() {
        Calendar currentDate = new GregorianCalendar();
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        return currentDate.getTime().getTime();
    }

    public static long getEndTime() {
        Calendar currentDate = new GregorianCalendar();
        currentDate.set(Calendar.HOUR_OF_DAY, 23);
        currentDate.set(Calendar.MINUTE, 59);
        currentDate.set(Calendar.SECOND, 59);
        return currentDate.getTime().getTime();
    }

    // 获得当天0点时间
    public static long getTimesMorning(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    // 获得当天24点时间
    public static long getTimesNight(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    /**
     * 获取utc时间与本地时区相差时间小时
     *
     * @return
     */
    public static int getTimeZoneRawOffset() {
        int time = TimeZone.getTimeZone(TimeZone.getDefault().getID()).getRawOffset();
        return time / (3600 * 1000);
    }

    /**
     * 得到本周周一
     *
     * @return yyyy-MM-dd
     */
    public static String getMondayOfThisWeek() {
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 1);
        return ymd.format(c.getTime());
    }

    /**
     * 得到本周周日
     *
     * @return yyyy-MM-dd
     */
    public static String getSundayOfThisWeek() {
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 7);
        return ymd.format(c.getTime());
    }

    /**
     * 获得指定日期的前一天
     *
     * @param specifiedDay
     * @return
     * @throws Exception
     */
    public static String getSpecifiedDayBefore(String specifiedDay, int num) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = ymd.parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
            ViseLog.e(e);
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - num);

        String dayBefore = ymd.format(c.getTime());
        return dayBefore;
    }


    /**
     * 获得指定日期的后一天
     *
     * @param specifiedDay
     * @return
     */
    public static String getSpecifiedDayAfter(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = ymd.parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
            ViseLog.e(e);
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 1);

        String dayAfter = ymd.format(c.getTime());
        return dayAfter;
    }

    /**
     * <p>描述:当前日期一周中第几天</p>
     *
     * @param pTime 例如："2015-08-12"
     * @return 第几天
     */
    public static int dayForWeek(String pTime) {
        int dayForWeek = 1;
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(ymd.parse(pTime));
            dayForWeek = 0;
            if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                dayForWeek = 7;
            } else {
                dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            ViseLog.e(e);
        }
        return dayForWeek;
    }

    /**
     * <p>描述:当前日期一月中第几天</p>
     *
     * @param pTime 例如："2015-08-12"
     * @return 第几天
     */
    public static int dayForMonth(String pTime) {
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(ymd.parse(pTime));
            int days = c.get(Calendar.DAY_OF_MONTH);
            return days;
        } catch (ParseException e) {
            e.printStackTrace();
            ViseLog.e(e);
        }
        return 0;
    }

    // 获得当前日期与本周一相差的天数
    public static int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            return -6;
        } else {
            return 2 - dayOfWeek;
        }
    }

    /**
     * <p>描述:求当前日期的上num个月的第一天</p>
     *
     * @param date 当前日期
     * @param num  0：表示当前月  num必须大于0  例：num:2 表示上两个月
     * @return 日期
     */
    public static Date getFirstDayOfMonth(Date date, int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) - num, 1);
        return calendar.getTime();
    }

    /**
     * <p>描述:求当前日期的下num个月的最后一天</p>
     *
     * @param date 当前日期
     * @param num  0：表示当前月  num必须大于0  例：num:2 表示上两个月
     * @return 日期
     */
    public static Date getLastDayOfMonth(Date date, int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + num, 1);
        calendar.roll(Calendar.DATE, -1);
        return calendar.getTime();
    }

}
