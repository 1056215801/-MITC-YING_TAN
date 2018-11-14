package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import javax.persistence.Table;

/**
 * 用户
 * @author shuyy
 * @date 2018/11/14
 * @company mitesofor
 */
@TableName("sys_user")
@Data
public class SysUser {

    @TableId(type = IdType.AUTO)
    private  Integer id;
    private String username;
    private String password;

}
