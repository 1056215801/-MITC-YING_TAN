package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 住户设备授权
 *
 * @author shuyy
 * @date 2018/11/19
 * @company mitesofor
 */
@TableName("authorize_household_device")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizeHouseholdDevice extends BaseEntity{

    /**住户id*/
    @TableField("household_id")
    private Integer householdId;
    /**设备id*/
    @TableField("device_id")
    private  Integer deviceId;

}
