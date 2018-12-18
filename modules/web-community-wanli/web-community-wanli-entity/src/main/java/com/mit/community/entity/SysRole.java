package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统角色
 *
 * @author shuyy
 * @date 2018/12/17
 * @company mitesofor
 */
@NoArgsConstructor
@AllArgsConstructor
@TableName("web_sys_role")
@Data
public class SysRole extends BaseEntity {

    /**是否可用。1、可用。0、不可用*/
    private Boolean available;
    /**角色名*/
    private String name;
    /**角色标识符*/
    private String role;
}
