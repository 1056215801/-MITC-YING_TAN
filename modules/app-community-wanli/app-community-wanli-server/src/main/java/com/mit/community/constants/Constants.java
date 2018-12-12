package com.mit.community.constants;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 常量
 * @author shuyy
 * @date 2018/11/21
 * @company mitesofor
 */
public class Constants {

    public static final String ERROR = "ERROR";

    /**
     * 逻辑为空的LocalDateTime
     */
    public static final LocalDateTime NULL_LOCAL_DATE_TIME = LocalDateTime.of(1900, 01,
            01, 0, 0, 0);

    /**
     * 逻辑为空的LocalDate
     */
    public static final LocalDate NULL_LOCAL_DATE = LocalDate.of(1900, 1,
            1);
    /**
     * 用户足迹开关（true为打开，false为关）
     */
    public static final Boolean USER_TRACK_TYPE = true;

}
