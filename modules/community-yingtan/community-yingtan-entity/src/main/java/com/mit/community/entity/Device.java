package com.mit.community.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

/**
 * 设备表
 *
 * @author Mr.Deng
 * @date 2018/11/15 9:51
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Data
@TableName("device")
public class Device extends Model<Device> {
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 小区Code
     */
    @TableField("community_code")
    private String communityCode;
    /**
     * 分区ID
     */
    @TableField("zone_id")
    private Integer zoneId;
    /**
     * 楼栋ID
     */
    @TableField("building_id")
    private Integer buildingId;
    /**
     * 单元ID
     */
    @TableField("unit_id")
    private Integer unitId;
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
    private String deviceStatus;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
