package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 操作指南
 *
 * @author shuyy
 * @date 2018/12/20
 * @company mitesofor
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("instruction")
public class Instruction extends BaseEntity {
    /**
     * 标题
     */
    private String title;
    /**
     * 简介
     */
    private String intro;
    /**
     * 创建人
     */
    @TableField("user_create")
    private Integer userCreate;

    /**详情*/
    @TableField(exist = false)
    private String detail;

}
