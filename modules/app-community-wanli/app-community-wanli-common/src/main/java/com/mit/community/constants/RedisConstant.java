package com.mit.community.constants;

/**
 * redis相关常量
 *
 * @author shuyy
 * @date 2018/11/30
 * @company mitesofor
 */
public class RedisConstant {

    /**
     * 手机验证码
     */
    public static final String VERIFICATION_CODE = "VERIFICATION_CODE";

    /**
     * 手机号验证码验证成功
     */
    public static final String VERIFICATION_SUCCESS = "VERIFICATION_SUCCESS";

    /**
     * 校验码过期时间
     */
    public final static long VERIFICATION_CODE_EXPIRE_TIME = 600L;

    /**
     * 校验成功标记过期时间
     */
    public final static long VERIFICATION_SUCCESS_EXPIRE_TIME = 600L;

    /**
     * 登陆过期时间
     */
    public final static long LOGIN_EXPIRE_TIME = 1800L;

    /**
     * dnake 登陆响应
     */
    public final static String DNAKE_LOGIN_RESPONSE = "DNAKE_LOGIN_RESPONSE";

    /**
     * 用户
     */
    public final static String USER = "USER:";

    /**session id*/
    public final static String SESSION_ID = "SESSION_ID:";
    /**验证码*/
    public final static String KAPTCHA = "kaptcha:";

    /**
     * mac地址
     */
    public final static String MAC = "MAC";


}
