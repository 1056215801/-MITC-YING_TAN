package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统角色资源关联
 *
 * @author shuyy
 * @date 2018/12/17
 * @company mitesofor
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("web_sys_role_permission")
public class SysRolePermission extends BaseEntity{

    @TableField("role_id")
    private Integer roleId;

    @TableField("permission_id")
    private Integer permissionId;



}
