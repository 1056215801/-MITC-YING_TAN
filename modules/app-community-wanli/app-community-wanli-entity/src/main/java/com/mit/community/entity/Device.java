package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 设备表
 * @author Mr.Deng
 * @date 2018/11/15 9:51
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("device")
public class Device extends BaseEntity {
    /**
     * 小区Code
     */
    @TableField("community_code")
    private String communityCode;
    /**
     * 楼栋id
     */
    @TableField("building_id")
    private String buildingId;
    /**
     * 单元ID
     */
    @TableField("unit_id")
    private String unitId;
    /**
     * 设备名称
     */
    @TableField("device_name")
    private String deviceName;
    /**
     * 设备编号
     */
    @TableField("device_num")
    private String deviceNum;
    /**
     * 设备类型（M：单元门口机；W：围墙机；）
     */
    @TableField("device_type")
    private String deviceType;
    /**
     * 设备状态（0：离线；1在线）
     */
    @TableField("device_status")
    private Integer deviceStatus;
    /**
     * 设备编码
     */
    @TableField("device_code")
    private String deviceCode;
    /**
     * 设备SIP号
     */
    @TableField("device_sip")
    private String deviceSip;
    /**
     * 楼栋编号
     */
    @TableField("building_code")
    private String buildingCode;
    /**
     * 单元编号
     */
    @TableField("unit_code")
    private String unitCode;
    /**
     * 设备ID
     */
    @TableField("device_id")
    private String deviceId;

}
