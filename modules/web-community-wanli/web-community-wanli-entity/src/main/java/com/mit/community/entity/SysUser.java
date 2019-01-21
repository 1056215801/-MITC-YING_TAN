package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户
 * @author shuyy
 * @date 2018/11/14
 * @company mitesofor
 */
@TableName("sys_user")
@Data
public class SysUser implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 小区code
     */
    @TableField("community_code")
    private String communityCode;
    /**
     * 省份名称
     */
    @TableField("province_name")
    private String provinceName;
    /**
     * 城市名称
     */
    @TableField("city_name")
    private String cityName;
    /**
     * 区/县名称
     */
    @TableField("area_name")
    private String areaName;
    /**
     * 镇/街道
     */
    @TableField("street_name")
    private String streetName;
    /**
     * 地址
     */
    private String address;
    /**
     * 管理员名称
     */
    @TableField("admin_name")
    private String adminName;
    /**
     * 账号角色
     */
    private String role;
    /**
     * logo
     */
    private String logo;
    /**
     * 电话号码
     */
    private String phone;
    /**
     * 备注信息
     */
    private String remark;
    /**
     * 所管理小区
     */
    @TableField("management_community")
    private String managementCommunity;
    /**
     * 管理单位
     */
    @TableField("management_unit")
    private String managementUnit;
}
