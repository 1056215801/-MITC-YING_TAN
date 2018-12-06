package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 生活黄页类型表
 * @author Mr.Deng
 * @date 2018/12/5 17:07
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("yellow_pages_type")
public class YellowPagesType extends BaseEntity {

    /**
     * 子菜单名称
     */
    @TableField("submenu_name")
    private String submenuName;
    /**
     * 父级菜单名称
     */
    @TableField("parent_name")
    private String parentName;
}
