package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户角色对应关系表
 *
 * @author shuyy
 * @date 2017年12月7日
 */
@NoArgsConstructor
@AllArgsConstructor
@TableName("web_sys_user_role")
@Data
public class SysUserRole extends BaseEntity {
    /**
     * 角色id
     */
    @TableField("role_id")
    private Integer roleId;
    /**
     * 用户id
     */
    private Integer uid;

}
