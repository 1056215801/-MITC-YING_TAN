package com.mit.community.util;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;

/**
 * 星座
 *
 * @author shuyy
 * @date 2018/12/12
 * @company mitesofor
 */
public class ConstellationUtil {

    /**
     * 计算星座
     * @param birthday 出生日期
     * @return java.lang.String
     * @throws
     * @author shuyy
     * @date 2018/12/12 16:18
     * @company mitesofor
    */
    public static String calc(LocalDate birthday) {
        LocalDate now = LocalDate.now();
        int month = birthday.getMonth().getValue();
        int day = birthday.getDayOfMonth();
        int year = birthday.getYear();
        if (birthday.isAfter(LocalDate.of(year, 3, 21))
                && birthday.isBefore(LocalDate.of(year, 4, 19))){
            return "白羊座";
        }
        if (birthday.equals(LocalDate.of(year, 3, 21))
                || birthday.equals(LocalDate.of(year, 4, 19))){
            return "白羊座";
        }
        if (birthday.isAfter(LocalDate.of(year, 4, 20))
                && birthday.isBefore(LocalDate.of(year, 5, 20))){
            return "金牛座";
        }
        if (birthday.equals(LocalDate.of(year, 4, 20))
                || birthday.equals(LocalDate.of(year, 5, 20))){
            return "金牛座";
        }
        if (birthday.isAfter(LocalDate.of(year, 5, 21))
                && birthday.isBefore(LocalDate.of(year, 6, 20))){
            return "双子座";
        }
        if (birthday.isEqual(LocalDate.of(year, 5, 21))
                || birthday.isEqual(LocalDate.of(year, 6, 20))){
            return "双子座";
        }
        if (birthday.isAfter(LocalDate.of(year, 6, 21))
                && birthday.isBefore(LocalDate.of(year, 7, 21))){
            return "巨蟹座";
        }
        if (birthday.isEqual(LocalDate.of(year, 6, 21))
                || birthday.isEqual(LocalDate.of(year, 7, 21))){
            return "巨蟹座";
        }
        if (birthday.isAfter(LocalDate.of(year, 7, 21))
                && birthday.isBefore(LocalDate.of(year, 8, 22))){
            return "狮子座";
        }
        if (birthday.equals(LocalDate.of(year, 7, 21))
                || birthday.equals(LocalDate.of(year, 8, 22))){
            return "狮子座";
        }
        if (birthday.isAfter(LocalDate.of(year, 8, 23))
                && birthday.isBefore(LocalDate.of(year, 9, 22))){
            return "处女座";
        }
        if (birthday.equals(LocalDate.of(year, 8, 23))
                || birthday.equals(LocalDate.of(year, 9, 22))){
            return "处女座";
        }
        if (birthday.isAfter(LocalDate.of(year, 9, 23))
                && birthday.isBefore(LocalDate.of(year, 10, 22))){
            return "天秤座";
        }
        if (birthday.equals(LocalDate.of(year, 9, 23))
                || birthday.equals(LocalDate.of(year, 10, 22))){
            return "天秤座";
        }
        if (birthday.isAfter(LocalDate.of(year, 10, 23))
                && birthday.isBefore(LocalDate.of(year, 11, 21))){
            return "天蝎座";
        }
        if (birthday.isAfter(LocalDate.of(year, 10, 23))
                && birthday.isBefore(LocalDate.of(year, 11, 21))){
            return "天蝎座";
        }
        if (birthday.isAfter(LocalDate.of(year, 11, 22))
                && birthday.isBefore(LocalDate.of(year, 12, 21))){
            return "射手座";
        }
        if (birthday.equals(LocalDate.of(year, 11, 22))
                || birthday.equals(LocalDate.of(year, 12, 21))){
            return "射手座";
        }
        if (birthday.isAfter(LocalDate.of(year, 12, 22))
                && birthday.isBefore(LocalDate.of(year + 1, 1, 19))){
            return "摩羯座";
        }
        if (birthday.equals(LocalDate.of(year, 12, 22))
                || birthday.equals(LocalDate.of(year, 1, 19))){
            return "摩羯座";
        }
        if (birthday.isAfter(LocalDate.of(year, 1, 20))
                && birthday.isBefore(LocalDate.of(year, 2, 18))){
            return "水瓶座";
        }
        if (birthday.equals(LocalDate.of(year, 1, 20))
                || birthday.equals(LocalDate.of(year, 2, 18))){
            return "水瓶座";
        }
        if (birthday.isAfter(LocalDate.of(year, 2, 19))
                && birthday.isBefore(LocalDate.of(year, 3, 20))){
            return "双鱼座";
        }
        if (birthday.equals(LocalDate.of(year, 2, 19))
                || birthday.equals(LocalDate.of(year, 3, 20))){
            return "双鱼座";
        }
        return StringUtils.EMPTY;
    }

}
