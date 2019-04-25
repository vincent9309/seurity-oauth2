package com.happylifeplat.security.util;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>Description: .</p>
 * <p>Company:  </p>
 * <p>Date: 2017/9/18 14:09</p>
 * <p>Copyright: 2015-2016 happylifeplat.com All Rights Reserved</p>
 *
 * @author vincent
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    /**
     * 获取当前系统时间(原始格式)
     */
    public static Date getCurrentDate() {
        DateTime dateTime = new DateTime();
        Date now = dateTime.toDate();
        return now;
    }

    /**
     * 获取当前时间日期的字符串
     *
     * @param dateFormatType 时间格式
     * @return 时间格式字符串
     */
    public static String getCurrentDateStr(DateFormatType dateFormatType) {
        return (String) doOperation(getCurrentDate(), dateFormatType.getValue());
    }

    /**
     * 时间、日期格式化成字符串
     *
     * @param date           date
     * @param dateFormatType 时间格式
     * @return 时间字符串
     */
    public static String formatDate(Date date, DateFormatType dateFormatType) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        return (String) doOperation(date, dateFormatType.getValue());
    }

    /**
     * 返回距离当前时间  几天几小时
     *
     * @param date date
     * @return 间隔时间字符串
     */
    public static String getIntervalDayAndHour(Date date) {
        long nowTime = getCurrentDate().getTime();
        int day = (int) ((nowTime - date.getTime()) / MILLIS_PER_DAY);
        int hour = (int) (((nowTime - date.getTime()) % MILLIS_PER_DAY) / MILLIS_PER_HOUR);
        String interval = StringUtils.EMPTY;
        if (day > 0) {
            interval += day + " 天 ";
        }
        if (hour > 0) {
            interval += hour + " 小时";
        }
        return interval;
    }

    /**
     * 从字符串解析成时间、日期
     *
     * @param dateStr        时间字符串
     * @param dateFormatType 时间格式
     * @return date
     */
    public static Date parseDate(String dateStr, DateFormatType dateFormatType) {
        return (Date) doOperation(dateStr, dateFormatType.getValue());
    }

    /**
     * 从字符串解析成时间，时间格式：yyy-MM-dd HH:mm:ss
     *
     * @param dateStr 时间字符串
     * @return date
     */
    public static Date parseDate(String dateStr) {
        return (Date) doOperation(dateStr, DateFormatType.DATE_FORMAT_STR.getValue());
    }

    /**
     * 格式化
     *
     * @param object    object
     * @param formatStr formatStr
     * @return object
     */
    private static Object doOperation(Object object, String formatStr) {
        if (object == null || StringUtils.isBlank(formatStr)) {
            throw new RuntimeException("参数不能为空");
        }
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        try {
            if (object instanceof Date) {
                return format.format(object);
            } else {
                return format.parse(object.toString());
            }
        } catch (Exception e) {
            throw new RuntimeException("操作失败", e);
        }

    }

    /**
     * 时间格式
     */
    public enum DateFormatType {

        /**
         * yyyy-MM-dd HH:mm:ss
         */
        DATE_FORMAT_STR("yyyy-MM-dd HH:mm:ss"),

        /**
         * yyyy-MM-dd HH:mm
         */
        DATE_MINUTE_FORMAT_STR("yyyy-MM-dd HH:mm"),

        /**
         * MM/dd/yyyy HH:mm:ss
         */
        DATE_USA_STYLE("MM/dd/yyyy HH:mm:ss"),

        /**
         * yyyy年MM月dd日 HH时mm分ss秒
         */
        DATE_FORMAT_STR_CHINA("yyyy年MM月dd日 HH时mm分ss秒"),

        /**
         * yyyy年MM月dd日 HH点
         */
        DATE_FORMAT_STR_CHINA_HOUR("yyyy年MM月dd日 HH点"),

        /**
         * yy年MM月dd日 HH点
         */
        DATE_FORMAT_STR_CHINA_HOUR_YY("yy年MM月dd日 HH点"),

        /**
         * yyyyMMddHHmmss
         */
        SIMPLE_DATE_TIME_FORMAT_STR("yyyyMMddHHmmss"),

        /**
         * yyyy-MM-dd
         */
        SIMPLE_DATE_FORMAT_STR("yyyy-MM-dd"),

        /**
         * yyyyMMdd
         */
        SIMPLE_DATE_FORMAT("yyyyMMdd"),

        /**
         * yy年MM月dd日
         */
        SIMPLE_DATE_FORMAT_STR_YY("yy年MM月dd日"),

        /**
         * yyyy年MM月dd日
         */
        SIMPLE_DATE_FORMAT_STR_DAY("yyyy年MM月dd日"),

        /**
         * MM月dd日
         */
        SIMPLE_DATE_FORMAT_STR_MD("MM月dd日"),

        /**
         * yyyy/MM/dd
         */
        SIMPLE_DATE_FORMAT_VIRGULE_STR("yyyy/MM/dd"),

        /**
         * HH:mm:ss MM-dd
         */
        MONTH_DAY_HOUR_MINUTE_SECOND("HH:mm:ss MM-dd"),

        /**
         * HH:mm:ss
         */
        HOUR_MINUTE_SECOND("HH:mm:ss"),

        /**
         * HH:mm
         */
        HOUR_MINUTE("HH:mm"),

        /**
         * yyyy/M/d HH:mm:ss
         */
        DATE_FORMAT_STR_WITHOUT_ZERO("yyyy/M/d HH:mm:ss");

        /**
         * 时间格式字符串
         */
        private final String value;

        DateFormatType(String formatStr) {
            this.value = formatStr;
        }

        public String getValue() {
            return value;
        }
    }
}
