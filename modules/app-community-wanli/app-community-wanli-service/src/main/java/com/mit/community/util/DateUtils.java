package com.mit.community.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: HuShanLin
 * @Date: Create in 2019/5/7 13:22
 * @Company mitesofor
 * @Description:~日期工具类
 */
public class DateUtils {

    public static Long getLocalDateInter(LocalDate start, LocalDate end) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime startZone = start.atStartOfDay(zoneId);
        ZonedDateTime endZone = end.atStartOfDay(zoneId);
        Date startDate = Date.from(startZone.toInstant());
        Date endDate = Date.from(endZone.toInstant());
        long mis = endDate.getTime() - startDate.getTime();
        Long integer = mis / (24 * 60 * 60 * 1000);
        return integer;
    }

    public static Long getDateInter(Date start, Date end) {
        long mis = end.getTime() - start.getTime();
        Long integer = mis / (24 * 60 * 60 * 1000);
        return integer;
    }

    public static LocalDateTime dateStrToLocalDateTime(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Pattern pattern = Pattern.compile("[0-9]{4}[-][0-9]{1,2}[-][0-9]{1,2}");
        Matcher matcher = pattern.matcher(dateStr);
        if (matcher.find()) {
            Date date = sdf.parse(dateStr);
            Instant instant = date.toInstant();
            ZoneId zone = ZoneId.systemDefault();
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
            return localDateTime;
        }
        return null;
    }

    public static LocalDateTime strToLocalDateTime(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(dateStr);
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime;
    }
}
