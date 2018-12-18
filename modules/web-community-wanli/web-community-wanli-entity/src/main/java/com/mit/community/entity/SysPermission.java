package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("web_sys_permission")
public class SysPermission extends BaseEntity {
    /**
     * 名称
     */
    private String name;

    /**
     * 资源权限类型：1、menu:菜单、2、button：按钮、3、crudButton：增删改查按钮
     */
    @TableField("resource_type")
    private Short resourceType;
    /**
     * 资源路径
     */
    @Column(unique = true)
    private String url;
    /**
     * 权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
     */
    private String permission;
    /**如果是crud权限对象，则parentId指向的是菜单id*/
    /**
     * 父编号
     */
    @TableField("parent_id")
    private Integer parentId;
    /**
     * 父编号列表
     */
    @TableField("parent_ids")
    private String parentIds;

    /**
     * 是否可用
     */
    private Boolean available;

    /**
     * menuType类型：false：系统菜单。true：业务菜单
     */
    @TableField("menu_type")
    private Boolean menuType;

    /**菜单顺序*/
    @TableField("menu_order")
    private Short menuOrder;

    @TableField("menu_icon")
    private String menuIcon;

    /**是否为父菜单。0：否。1：是。*/
    @TableField("have_children")
    private Boolean haveChildren;


}