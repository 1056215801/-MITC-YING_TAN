package com.mit.community.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.mit.community.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 消防栓盖帽报警
 *
 * @author shuyy
 * @date 2019-01-04
 * @company mitesofor
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("fire_hydrant_block")
public class FireHydrantBlock extends BaseEntity {

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
     * 报警状态。1、用水、2、撞倒、3、渗漏
     */
    @TableField("warn_type")
    private Short warnType;
    /**
     * 报警内容
     */
    @TableField("warn_content")
    private String warnContent;
    /**
     * 1、正在报警，2、已处理
     */
    @TableField("warn_status")
    private Short warnStatus;
    /**
     * 报警时间
     */
    @TableField("gmt_warn")
    private LocalDateTime gmtWarn;
}
