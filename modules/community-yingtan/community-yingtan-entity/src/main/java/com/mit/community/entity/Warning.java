package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 警报表
 * @author Mr.Deng
 * @date 2018/11/27 14:15
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@TableName("warning")
@Data
public class Warning extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 设备类型
     */
    @TableField("device_type")
    private String deviceType;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;
    /**
     * 最大时间
     */
    @TableField("max_time")
    private LocalDateTime maxTime;
    /**
     * 警报信息
     */
    @TableField("alarm_msg")
    private String alarmMsg;
    /**
     * 单位名称
     */
    @TableField("unit_name")
    private String unitName;
    /**
     * 楼栋编码
     */
    @TableField("building_code")
    private String buildingCode;
    /**
     * 数据状态
     */
    @TableField("data_status")
    private Integer dataStatus;
    /**
     * 设备编码
     */
    @TableField("device_code")
    private String deviceCode;
    /**
     * 设备名称
     */
    @TableField("device_name")
    private String deviceName;
    /**
     * 警报级别
     */
    @TableField("alarm_grade")
    private Integer alarmGrade;
    /**
     * 设备号
     */
    @TableField("device_num")
    private String deviceNum;
    /**
     * 警报数据状态
     */
    @TableField("alarm_data_status")
    private Integer alarmDataStatus;
    /**
     * 楼栋名称
     */
    @TableField("building_name")
    private String buildingName;
    /**
     * 进入标记
     */
    @TableField("enter_flag")
    private Integer enterFlag;
    /**
     * 警报类型
     */
    @TableField("alarm_type")
    private Integer alarmType;
    /**
     * 单元编码
     */
    @TableField("unit_code")
    private String unitCode;
    /**
     * 警报id
     */
    @TableField("alarm_id")
    private Integer alarmId;
    /**
     * 设备地址
     */
    @TableField("gps_address")
    private String gpsAddress;
    /**
     * 数据添加时间
     */
    @TableField("gmt_create")
    private LocalDateTime gmtCreate;
    /**
     * 数据修改时间
     */
    @TableField("gmt_modified")
    private LocalDateTime gmtModified;
    /**
     * 小区code
     */
    @TableField("community_code")
    private String communityCode;

}
