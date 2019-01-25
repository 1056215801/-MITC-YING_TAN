package com.mit.community.entity;

/**
 * 消息
 *
 * @author shuyy
 * @date 2019-01-25
 * @company mitesofor
 */
public class Message {

    /**类别*/
    private Short type;
    /**消息内容*/
    private String message;

    /**审批钥匙成功*/
    public final static Short TYPE_KEY = (short)1;
}
