package com.mit.community.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * dnake登陆响应
 *
 * @author shuyy
 * @date 2018/12/7
 * @company mitesofor
 */
@Data
public class DnakeLoginResponse implements Serializable {

    /**
     * sip呼叫转移手机号
     */
    private String sipMobile;

    /**
     * 用户id
     */
    private String appUserId;

}
