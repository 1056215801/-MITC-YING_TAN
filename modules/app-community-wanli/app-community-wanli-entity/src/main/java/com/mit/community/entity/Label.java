package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 标签
 *
 * @author shuyy
 * @date 2018/12/19
 * @company mitesofor
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("label")
public class Label extends BaseEntity {
    /**
     * 标签名
     */
    private String name;
    /**
     * 菜单类型。1、系统预设。2、用户自定义
     */
    private Short type;
    /**
     * 用户id
     */
    private Integer user_id;
    /***/
    @TableField(exist = false)
    private Boolean selected;
}
