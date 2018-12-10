package com.mit.common.constant;

/**
 * 短信常量
 *
 * @author shuyy
 * @date 2018/12/10
 * @company mitesofor
 */
public class SmsConstants {

    /**
     * 签名
     */
    public static final String SIGN_MXZN = "珉轩智能";

    /**
     * 模板code-注册：
     * 验证码${code}，您正在注册成为新用户，感谢您的支持！
     */
    public static final String MODEL_CODE_REGISTER = "SMS_152470940";

    /**
     * 模板code-修改密码：
     * 验证码${code}，您正在尝试修改登录密码，请妥善保管账户信息。
     */
    public static final String MODEL_CODE_MODIFIED_PASSWORD = "SMS_152470939";

    /**
     * 模板code-登陆确认：
     * 验证码${code}，您正在登录，若非本人操作，请勿泄露。
     */
    public static final String MODEL_CODE_LOGIN_CONFIRM = "SMS_152470942";    /**

     * 模板code-身份验证验证码：
     * 验证码${code}，您正在进行身份验证，打死不要告诉别人哦！
     */
    public static final String MODEL_CODE_AUTHENTICATION = "SMS_152470943";


}