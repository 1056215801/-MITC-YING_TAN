package com.mit.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期处理工具类
 *
 * @author shuyy
 * @date 2018年10月19日
 * @company mitesofor
 */
public class DateUtils {

    /**
     * 指定日期
     *
     * @param @param  year
     * @param @param  month
     * @param @param  day
     * @param @return
     * @return Date
     * @throws
     * @date 2018年10月19日
     * @company mitesofor
     * @author shuyy
     */
    public static Date date(Integer year, Integer month, Integer day) {
        Calendar calendar = Calendar.getInstance();
        //设置日历时间，月份必须减一
        calendar.set(year, month - 1, day);
        Date date = calendar.getTime();
        return date;
    }

    /**
     * 日期加指定天数
     *
     * @param @param  date
     * @param @param  days
     * @param @return
     * @return Date
     * @throws
     * @date 2018年10月19日
     * @company mitesofor
     * @author shuyy
     */
    public static Date addDay(Date date, Integer days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    /**
     * 计算时间间隔，以分为单位。
     *
     * @param @param  startData
     * @param @param  endDate
     * @param @return
     * @return Long
     * @throws
     * @date 2018年10月24日
     * @company mitesofor
     * @author shuyy
     */
    public static Long getDataDiff(Date startData, Date endDate) {
        long diffMillSecond = endDate.getTime() - startData.getTime();
        long minute = diffMillSecond / 1000L / 60L;
        return minute;
    }

    /**
     * String转localDateTime。
     * @param time 时间
     * @param format 如果为null，则默认格式：yyyy-MM-dd HH:mm:ss
     * @return LocalDateTime
     * @date 2018年10月31日
     * @company mitesofor
     * @author shuyy
     */
    public static LocalDateTime parseStringToDateTime(String time, String format) {
        if (format == null) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(time, df);
    }

    /**
     * String转LocalDate。
     * @param time 时间
     * @param format 如果为null，则默认格式：yyyy-MM-dd
     * @return LocalDateTime
     * @date 2018年10月31日
     * @company mitesofor
     * @author shuyy
     */
    public static LocalDate parseStringToLocalDate(String time, String format) {
        if (format == null) {
            format = "yyyy-MM-dd";
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return LocalDate.parse(time, df);
    }

    public static void main(String[] args) {
        DateUtils dateUtils = new DateUtils();
        dateUtils.parseStringToDateTime("19950101", "yyyyMMdd");
    }
    /**
     * localDateTime转format
     *
     * @param @param  dateTime
     * @param @param  format format 如果为null，则默认格式：yyyy-MM-dd HH:mm:ss
     * @param @return
     * @return String
     * @throws
     * @date 2018年10月31日
     * @company mitesofor
     * @author shuyy
     */
    public static String format(LocalDateTime dateTime, String format) {
        if (format == null) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        String strDate = dateTime.format(formatter);
        return strDate;
    }
    /**
     * 获取逻辑意义为空，且能存储进数据库的时间
     * @return java.time.LocalDateTime
     * @throws
     * @author shuyy
     * @date 2018/11/30 9:10
     * @company mitesofor
    */
    public static LocalDateTime getNull(){
        return LocalDateTime.of(1990, 1, 1, 0, 0, 0);
    }
}
