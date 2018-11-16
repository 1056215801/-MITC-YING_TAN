package com.mit.community.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

/**
 * 楼栋表
 *
 * @author Mr.Deng
 * @date 2018/11/14 16:20
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Data
@TableName("building")
public class Building{

    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 小区Code
     */
    @TableField("community_code")
    private String communityCode;
    /**
     * 分区Id
     */
    @TableField("zone_id")
    private Integer zoneId;
    /**
     * 楼栋状态（0：停用；1：启用）
     */
    @TableField("building_status")
    private Integer buildingStatus;
    /**
     * 楼栋名称
     */
    @TableField("building_name")
    private String buildingName;
    /**
     * 楼栋编号
     */
    @TableField("building_code")
    private String buildingCode;
    /**
     * 楼栋ID
     */
    @TableField("building_id")
    private Integer buildingId;

}
