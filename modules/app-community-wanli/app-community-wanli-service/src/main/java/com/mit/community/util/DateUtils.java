package com.mit.community.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * 日期工具类
 */
public class DateUtils {

    public static Long getLocalDateInter(LocalDate start,LocalDate end){
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

    public static Long getDateInter(Date start,Date end){
        long mis = end.getTime() - start.getTime();
        Long integer = mis / (24 * 60 * 60 * 1000);
        return integer;
    }
}
