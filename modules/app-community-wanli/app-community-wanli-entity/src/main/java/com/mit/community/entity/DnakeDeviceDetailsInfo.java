package com.mit.community.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DnakeDeviceDetailsInfo{
    private Integer deviceId;
    /**
     * 小区code
     */
    private String communityCode;
    /**
     * 小区名称
     */
    private String communityName;
    /**
     * 分区id
     */
    private Integer zoneId;
    /**
     * 到访分区名称
     */
    private String zoneName;
    /**
     * 楼栋id
     */
    private Integer buildingId;
    /**
     * 楼栋名称
     */
    private String buildingName;
    /**
     * 单元id
     */
    private Integer unitId;
    /**
     * 单元名称
     */
    private String unitName;

    private Integer DnakeDeviceInfoId;

    private String deviceName;

    private String deviceCode;//设备code（自定义）

    private String deviceIp;

    private String deviceMac;

    private String deviceNum;

}
