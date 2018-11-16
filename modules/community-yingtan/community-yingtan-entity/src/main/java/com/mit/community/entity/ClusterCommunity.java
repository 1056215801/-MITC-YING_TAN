package com.mit.community.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

/**
 * 小区信息表
 *
 * @author Mr.Deng
 * @date 2018/11/14 11:51
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Data
@TableName("cluster_community")
public class ClusterCommunity {

    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 小区编码
     */
    @TableField("community_code")
    private String communityCode;
    /**
     * 小区名称
     */
    @TableField("community_name")
    private String communityName;
    /**
     * 小区id
     */
    @TableField("community_id")
    private Integer communityId;
    /**
     * 地区名称
     */
    @TableField("area_name")
    private String areaName;
    /**
     * 地址
     */
    @TableField("address")
    private String address;
    /**
     * 城市名称
     */
    @TableField("city_name")
    private String cityName;
    /**
     * 街道名称
     */
    @TableField("street_name")
    private String streetName;
    /**
     * 省份名称
     */
    @TableField("province_name")
    private String provinceName;

}
