package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

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
    /**
     * 设备组类别（1,单元权限组,2,公共权限组,3,特殊权限组）
     */
    @TableField("group_type")
    private String groupType;

}
