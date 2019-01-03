package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * 访客消息
 *
 * @author shuyy
 * @date 2019-01-02
 * @company mitesofor
 */
@Data
@TableName("visitor_message")
public class VisitorMessage extends BaseEntity{

    /**手机号*/
    private String mobile;
    /**标题*/
    private String title;
    /**1、未读、2、已读*/
    private Short status;

}
