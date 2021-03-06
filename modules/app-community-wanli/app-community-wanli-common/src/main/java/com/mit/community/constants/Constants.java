package com.mit.community.constants;

import com.mit.community.util.UploadUtil;

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
    /**
     * 用户默认头像地址
     */
    public static final String USER_ICO_DEFULT = UploadUtil.URL + "1ec47936-e19a-43d2-86c1-481ddfe07a8c.png";

    /**标签类型-系统*/
    public static final Short LABEL_TYPE_SYSTEM = 1;
    /**标签类型-自定义*/
    public static final Short LABEL_TYPE_CUSTOMER = 2;

    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /** 默认日期格式 */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    /** 默认时间格式 */
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";
}
