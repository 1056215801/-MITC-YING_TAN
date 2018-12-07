package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 黄页菜单类型表
 * @author Mr.Deng
 * @date 2018/12/6 20:20
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("yellow_pages_type")
public class YellowPagesType extends BaseEntity {
    /**
     * 父级菜单名称
     */
    @TableField("parent_name")
    private String parentName;
    /**
     * 子菜单图片地址
     */
    private String image;
    /**
     * 子菜单名称
     */
    @TableField("submenu_name")
    private String submenuName;
}
