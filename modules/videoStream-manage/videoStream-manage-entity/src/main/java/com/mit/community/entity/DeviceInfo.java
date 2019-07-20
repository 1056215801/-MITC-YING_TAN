package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("device_info")
public class DeviceInfo extends BaseEntity {
    @TableField("device_address_info_id")
    private Integer deviceAddressInfoId;

    @TableField("device_id")
    private String deviceId;

    @TableField("is_collect")
    private Integer isCollect;

    private String token;
    @TableField("device_num")
    private String deviceNum;
}
