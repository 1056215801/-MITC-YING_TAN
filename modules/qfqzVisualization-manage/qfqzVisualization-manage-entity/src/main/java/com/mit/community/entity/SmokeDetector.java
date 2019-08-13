package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 烟感
 * @author shuyy
 * @date 2019-01-04
 * @company mitesofor
 */
@NoArgsConstructor
@Data
@TableName("smoke_detector")
public class SmokeDetector extends BaseEntity {


    /**设备编号*/
    @TableField("device_num")
    private String deviceNum;
    /**设备名*/
    @TableField("device_name")
    private String deviceName;
    /**设备位置*/
    @TableField("device_place")
    private String devicePlace;
    /**设备类型*/
    @TableField("device_type")
    private String deviceType;
    /**报警状态。1、出现大量烟雾*/
    @TableField("warn_type")
    private Short warnType;
    /**报警内容*/
    @TableField("warn_content")
    private String warnContent;
    /**1、正在报警，2、已处理*/
    @TableField("warn_status")
    private Short warnStatus;
    /**报警时间*/
    @TableField("gmt_warn")
    private LocalDateTime gmtWarn;
    @TableField("community_code")
    private String communityCode;

    public SmokeDetector(String deviceNum, String deviceName, String devicePlace, String deviceType, Short warnType, String warnContent, Short warnStatus, LocalDateTime gmtWarn) {
        this.deviceNum = deviceNum;
        this.deviceName = deviceName;
        this.devicePlace = devicePlace;
        this.deviceType = deviceType;
        this.warnType = warnType;
        this.warnContent = warnContent;
        this.warnStatus = warnStatus;
        this.gmtWarn = gmtWarn;
    }

    public SmokeDetector(String deviceNum, String deviceName, String devicePlace, String warnContent, Short warnStatus, LocalDateTime gmtWarn) {
        this.deviceNum = deviceNum;
        this.deviceName = deviceName;
        this.devicePlace = devicePlace;
        this.warnContent = warnContent;
        this.warnStatus = warnStatus;
        this.gmtWarn = gmtWarn;
    }

    public SmokeDetector(String deviceNum, String deviceName, String devicePlace, String deviceType, Short warnType, String warnContent, Short warnStatus, LocalDateTime gmtWarn, String communityCode) {
        this.deviceNum = deviceNum;
        this.deviceName = deviceName;
        this.devicePlace = devicePlace;
        this.deviceType = deviceType;
        this.warnType = warnType;
        this.warnContent = warnContent;
        this.warnStatus = warnStatus;
        this.gmtWarn = gmtWarn;
        this.communityCode = communityCode;
    }
}
