package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;

/**
 * 用户标签
 *
 * @author shuyy
 * @date 2018/11/29
 * @company mitesofor
 */
public class UserLabel extends BaseEntity {

    /**
     * 标签code。关联数据字典code为label。
     */
    @TableField("label_code")
    private String labelCode;
    /**
     * 用户id，关联user表id字段。
     */
    @TableField("user_id")
    private Integer userId;

}
