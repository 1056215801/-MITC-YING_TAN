package com.mit.community.entity.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.mit.community.entity.BaseEntity;
import com.mit.community.entity.Device;
import com.mit.community.entity.DeviceDeviceGroup;
import lombok.Data;

import java.util.List;

/**
 * 设备组信息
 *
 * @author shuyy
 * @date 2018/12/10 10:52
 * @company mitesofor
 */
@Data
@TableName("device_group")
public class DeviceGroup extends BaseEntity {

    /**
     * 小区code
     */
    @TableField("community_code")
    private String communityCode;
    /**
     * 设备组id
     */
    @TableField("device_group_Id")
    private Integer deviceGroupId;

    /**设备组*/
    @TableField("device_group_name")
    private String deviceGroupName;
    /**
     * 设备组类别（1,单元权限组,2,公共权限组,3,特殊权限组）
     */
    @TableField("group_type")
    private Short groupType;

    @TableField(exist = false)
    private List<DeviceDeviceGroup> deviceDeviceGroups;

    @TableField(exist = false)
    private List<Device> device;

}
