package com.mit.community.constants;

import java.time.LocalDateTime;

/**
 * 常量
 *
 * @author shuyy
 * @date 2018/11/21
 * @company mitesofor
 */
public class Constants {

    public static final String ERROR = "ERROR";

    /**手机验证码*/
    public static final String VERIFICATION_CODE = "VERIFICATION_CODE";

    /**手机号验证码验证成功*/
    public static final String VERIFICATION_SUCCESS = "VERIFICATION_SUCCESS";

    /**逻辑为空的LocalDateTime*/
    public static LocalDateTime nullLocalDateTime = LocalDateTime.of(1900, 01,
            01, 0, 0, 0);



}
