package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 小区信息表
 *
 * @author Mr.Deng
 * @date 2018/11/14 11:51
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("cluster_community")
public class ClusterCommunity extends BaseEntity{

    /**
     * 小区编码
     */
    @ApiModelProperty(value = "小区编码")
    @TableField("community_code")
    private String communityCode;
    /**
     * 小区名称
     */
    @ApiModelProperty(value = "小区名称")
    @TableField("community_name")
    private String communityName;
    /**
     * 小区id
     */
    @ApiModelProperty(value = "小区id")
    @TableField("community_id")
    private Integer communityId;
    /**
     * 地区名称
     */
    @ApiModelProperty(value = "地区名称")
    @TableField("area_name")
    private String areaName;

    @TableField("area_id")
    private String areaId;
    /**
     * 地址
     */
    @ApiModelProperty(value = "地址")
    @TableField("address")
    private String address;
    /**
     * 城市名称
     */
    @ApiModelProperty(value = "城市名称")
    @TableField("city_name")
    private String cityName;

    @TableField("city_id")
    private String cityId;
    /**
     * 街道名称
     */
    @ApiModelProperty(value = "街道名称")
    @TableField("street_name")
    private String streetName;

    @TableField("street_id")
    private String streetId;
    /**
     * 省份名称
     */
    @ApiModelProperty(value = "省份名称")
    @TableField("province_name")
    private String provinceName;

    @ApiModelProperty(value = "省份编码")
    @TableField("province_id")
    private String provinceId;
    /**
     * 小区类型
     */
    @ApiModelProperty(value = "小区类型1:住宅;2:商业;3:村中村;4:公租房;5:其他保障住房")
    @TableField("community_type")
    private String communityType;

    @ApiModelProperty(value = "所属区域")
    @TableField("area_belong")
    private String areaBelong;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "居委")
    @TableField("committee")
    private String committee;

    @TableField("committee_id")
    private String committeeId;

    @TableField(exist = false)
    private String username;

    @TableField(exist = false)
    private String adminName;

    @TableField(exist = false)
    private String phone;

    @TableField(exist = false)
    private String password;

}
