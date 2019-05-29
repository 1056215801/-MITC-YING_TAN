package com.mit.community.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.mit.community.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 防栓盖帽感知
 *
 * @author shuyy
 * @date 2019-01-04
 * @company mitesofor
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("fire_hydrant_block_status")
public class FireHydrantBlockStatus extends BaseEntity {

    @TableField("community_code")
    private String communityCode;
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
    /**事件类型（用水、撞倒、渗漏、在位）*/
    private Short status;
    /**设备状态（正常，故障，掉线）*/
    @TableField("device_status")
    private Short deviceStatus;
    /**上传时间*/
    @TableField("gmt_upload")
    private LocalDateTime gmtUpload;




}
