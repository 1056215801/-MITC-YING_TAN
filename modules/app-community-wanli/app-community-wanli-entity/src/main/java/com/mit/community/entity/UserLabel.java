package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户标签
 *
 * @author shuyy
 * @date 2018/11/29
 * @company mitesofor
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("user_label")
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


    /**
     * 标签名
     */
    @TableField(exist = false)
    private String lableName;
}
