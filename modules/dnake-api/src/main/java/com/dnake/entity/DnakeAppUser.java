package com.dnake.entity;

import lombok.Data;

/**
 * 登录用户
 *
 * @author shuyy
 * @date 2018/11/12
 * @company mitesofor
 */
@Data
public class DnakeAppUser {
    /**
     * 用户名
     */
    private String loginName;
    /**
     * 密码
     */
    private String password;
    /**
     * 登入后用户id
     */
    private String appUserId;
    /**
     * 登入后唯一标示
     */
    private String uuid;
    /**
     * 推送平台唯一id
     */
    private String registrationId = "xxxx";
    /**
     * 0:极光-IOS;1:极光-android;2:华为;3:小米;4:魅族;5:leancloud-IOS;6：leancloud-Android；
     */
    private String platform = "1";

}
