package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 住户app授权设备
 *
 * @author shuyy
 * @date 2018/11/30
 * @company mitesofor
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("authorize_app_household_device")
public class AuthorizeAppHouseholdDevice extends BaseEntity{

    /**住户id*/
    @TableField("household_id")
    private Integer householdId;

    /**设备id*/
    @TableField("device_id")
    private Integer device_id;

}
