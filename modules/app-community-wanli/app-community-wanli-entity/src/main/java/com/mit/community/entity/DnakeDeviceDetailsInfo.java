package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("dnake_device_details_info")
public class DnakeDeviceDetailsInfo extends BaseEntity {
    /**
     * 小区code
     */
    @TableField("community_code")
    private String communityCode;
    /**
     * 小区名称
     */
    @TableField("community_name")
    private String communityName;
    /**
     * 分区id
     */
    @TableField("zone_id")
    private Integer zoneId;
    /**
     * 到访分区名称
     */
    @TableField("zone_name")
    private String zoneName;
    /**
     * 楼栋id
     */
    @TableField("building_id")
    private Integer buildingId;
    /**
     * 楼栋名称
     */
    @TableField("building_name")
    private String buildingName;
    /**
     * 单元id
     */
    @TableField("unit_id")
    private Integer unitId;
    /**
     * 单元名称
     */
    @TableField("unit_name")
    private String unitName;

    @TableField("dnake_device_info_id")
    private Integer DnakeDeviceInfoId;

    private String deviceName;

    private String deviceCode;//设备code（自定义）

}
