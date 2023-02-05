package com.lenovo.research.se.aiexchange.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressWarnings("unused")
public class DatetimeUtils {

    private final static Logger logger = LoggerFactory.getLogger(DatetimeUtils.class);

    public static final String SLASH_DATE_TIME = "yyyy/MM/dd HH:mm:ss";

    public static final String SLASH_DATE = "yyyy/MM/dd";

    public static final String SLASH_DATE_MONTH = "yyyy/MM";

    public static final String HYPHEN_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    public static final String HYPHEN_DATE = "yyyy-MM-dd";

    public static final String HYPHEN_DATE1 = "MM-dd";

    public static final String HYPHEN_TIME = "HH:mm:ss";

    public static final String HYPHEN_TIME1 = "HH:mm";

    public static final String HYPHEN_DATE_MONTH = "yyyy-MM";

    public static final String NO_SIGN_DATE_TIME = "yyyyMMddHHmmss";

    public static final String NO_SIGN_DATE = "yyyyMMdd";

    public static final String NO_SIGN_TIME = "HHmmss";

    public static final String NO_SIGN_YEAR_MON = "yyyyMM";

    public static final int NO_ZERO_DATE = 1;

    public static final int NO_ZERO_YEAR_MON = 2;

    public static final String CHINA_DATE = "yyyy年MM月dd日";

    public static final String ORA_SLASH_DATE_TIME = "YYYY/MM/DD HH24:MI:SS";

    public static final String ORA_SLASH_DATE = "YYYY/MM/DD";

    public static final String ORA_HYPHEN_DATE_TIME = "YYYY-MM-DD HH24:MI:SS";

    public static final String ORA_HYPHEN_DATE = "YYYY-MM-dd";

    public static final String ORA_NO_SIGN_DATE_TIME = "YYYYMMDDHH24MISS";

    public static final String ORA_NO_SIGN_DATE = "YYYYMMDD";

    public static final String ORA_NO_SIGN_TIME = "HH24MISS";

    public static final String ORA_SLASH_DATE_MONTH = "YYYY/MM";

    public static final String ORA_HYPHEN_DATE_MONTH = "YYYY-MM";

    public static final String ORA_NO_SIGN_DATE_MONTH = "YYYYMM";

    public static final String ORA_HYPHEN_TIME = "HH24:MI:SS";

    private final static String TIME_MICROSECOND_PATTERN = "yyyy-MM-dd hh:mm:ss.SSS";

    /**
     * 系统时间取得<br>
     *
     * @param FORMAT 日期格式
     * @return 格式化后的字符串
     */
    public static String getSystemTime(String FORMAT) {
        String strDate;
        Calendar calendar = Calendar.getInstance();
        Date systemDate = calendar.getTime();
        strDate = formatDate(systemDate, FORMAT);
        return strDate;
    }

    public static Date getSystemTime() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    public static String formatDate(Date date, String FORMAT) {
        String strDate;
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT);
        strDate = dateFormat.format(date);
        return strDate;
    }

    public static String formatDateString(String dateStr, String srcFormat,
                                          String desFormat) {
        if (StringUtils.isEmpty(dateStr)
                || StringUtils.isEmpty(srcFormat)
                || StringUtils.isEmpty(desFormat)) {
            return null;
        }

        try {
            SimpleDateFormat formatFrom = new SimpleDateFormat(srcFormat);
            SimpleDateFormat formatTo = new SimpleDateFormat(desFormat);
            Date date = formatFrom.parse(dateStr);
            if (date == null) {
                return null;
            }
            return formatTo.format(date);
        } catch (ParseException pe) {
            logger.error("Parse date string:" + dateStr + " error.", pe);
            return null;
        }
    }

    public static Integer getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    public static String getCurrentYearStartStr() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return formatDate(calendar.getTime(), HYPHEN_DATE_TIME);
    }

    public static String time2Str(Time time) {
        return time.toString();
    }

    public static String getDateStringForPageView(String dateStr, int dateType) {
        if (dateStr == null || dateStr.length() == 0) {
            return "";
        }
        String year = dateStr.substring(0, 4);
        String month = dateStr.substring(5, 7);

        if (dateType == NO_ZERO_DATE) {
            String day = dateStr.substring(8, 10);
            return year + "/" + Integer.parseInt(month) + "/" + Integer.parseInt(day);
        }
        if (dateType == NO_ZERO_YEAR_MON)
            return year + "/" + Integer.parseInt(month);

        return "";
    }

    public static String getMicrosecondTimeStr(Long timeStamp) {
        Date time = new Date(timeStamp);
        DateFormat dateFormat = new SimpleDateFormat(TIME_MICROSECOND_PATTERN);
        return dateFormat.format(time);
    }

    public static Date getToday() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    public static Date getDate(String strDate, String format) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(strDate);
    }


    public static Timestamp getTimestamp(String strTime) {
        Timestamp time = null;
        return Timestamp.valueOf(strTime);
    }

    public static String getTodayString() {
        return formatDate(getToday(), HYPHEN_DATE);
    }

    public static String getYesterdayString() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return formatDate(calendar.getTime(), HYPHEN_DATE);
    }

    public static Date getMondayOfThisWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static String getMondayStringOfThisWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return formatDate(calendar.getTime(), HYPHEN_DATE);
    }

    public static Date getFirstDayDateOfThisMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date getLastDayDateOfThisMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1);
        calendar.roll(Calendar.DATE, -1);
        return calendar.getTime();
    }

    public static String getFirstDayDateStringOfThisMonth() {
        return formatDate(getFirstDayDateOfThisMonth(), HYPHEN_DATE);
    }

    public static String getLastDayDateStringOfThisMonth() {
        return formatDate(getLastDayDateOfThisMonth(), HYPHEN_DATE);
    }

    public static Date getFirstDayDateOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    public static Date getLastDayDateOfLastMonth() {
        Calendar cl = Calendar.getInstance();
        cl.set(Calendar.DAY_OF_MONTH, 1);
        cl.add(Calendar.DAY_OF_MONTH, -1);
        return new Date(cl.getTimeInMillis());
    }

    public static String getFirstDayDateStringOfLastMonth() {
        return formatDate(getFirstDayDateOfLastMonth(), HYPHEN_DATE);
    }

    public static String getLastDayDateStringOfLastMonth() {
        return formatDate(getLastDayDateOfLastMonth(), HYPHEN_DATE);
    }

    public static Date getTomorrowZero(Date today) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    public static String formatDate2YYYYMMDD(String str, String FORMAT) {
        if (StringUtils.isEmpty(str) || str.length() != 8) {
            return str;
        }
        String strDate = "";
        SimpleDateFormat format = new SimpleDateFormat(NO_SIGN_DATE);
        try {
            Date date = format.parse(str);
            if (date == null) {
                return str;
            }
            strDate = formatDate(date, FORMAT);

        } catch (ParseException ex) {
            return str;
        }

        return strDate;
    }

    public static String getDateStringForPageView(String dateStr) {
        return getDateStringForPageView(dateStr, NO_ZERO_DATE);
    }

    public static Date getZeroTime(Date today) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

}