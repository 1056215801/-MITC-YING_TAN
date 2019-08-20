package com.mit.community.entity;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 烟感感知
 *
 * @author shuyy
 * @date 2019-01-04
 * @company mitesofor
 */
@Data
@TableName("smoke_detector_status")
public class SmokeDetectorStatus extends BaseEntity {
    @TableField("community_code")
    private String communityCode;
    /**
     * 设备编号
     */
    @TableField("device_num")
    private String deviceNum;
    /**
     * 设备名
     */
    @TableField("device_name")
    private String deviceName;
    /**
     * 设备位置
     */
    @TableField("device_place")
    private String devicePlace;
    /**
     * 设备类型
     */
    @TableField("device_type")
    private String deviceType;
    /**
     * 水环境状态（出现大量烟雾，环境正常）
     */
    private Short status;
    /**
     * 设备状态（正常，故障，掉线）
     */
    @TableField("device_status")
    private Short deviceStatus;
    /**
     * 上传时间
     */
    @TableField("gmt_upload")
    private LocalDateTime gmtUpload;

    /**
     * 负责人电话
     */
    @TableField("manager_phone")
    private  String  managerPhone;

    public SmokeDetectorStatus(){

    }

    public SmokeDetectorStatus(String communityCode, String deviceNum, String deviceName, String devicePlace, String deviceType, Short status, Short deviceStatus, LocalDateTime gmtUpload) {
        this.communityCode = communityCode;
        this.deviceNum = deviceNum;
        this.deviceName = deviceName;
        this.devicePlace = devicePlace;
        this.deviceType = deviceType;
        this.status = status;
        this.deviceStatus = deviceStatus;
        this.gmtUpload = gmtUpload;
    }

    public SmokeDetectorStatus(String communityCode, String deviceNum, String deviceName, String devicePlace, String deviceType, Short status, Short deviceStatus, LocalDateTime gmtUpload, String managerPhone) {
        this.communityCode = communityCode;
        this.deviceNum = deviceNum;
        this.deviceName = deviceName;
        this.devicePlace = devicePlace;
        this.deviceType = deviceType;
        this.status = status;
        this.deviceStatus = deviceStatus;
        this.gmtUpload = gmtUpload;
        this.managerPhone = managerPhone;
    }


}
