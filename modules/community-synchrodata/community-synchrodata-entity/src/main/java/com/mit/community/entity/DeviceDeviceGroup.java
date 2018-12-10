package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * 设备和设备组关联
 *
 * @author shuyy
 * @date 2018/12/10
 * @company mitesofor
 */
@Data
@TableName("device_device_group")
public class DeviceDeviceGroup extends BaseEntity {

    /**
     * 设备编号
     */
    @TableField("device_num")
    private String device_num;

    /**
     * 设备组id
     */
    @TableField("device_group_id")
    private Integer deviceGroupId;
}
