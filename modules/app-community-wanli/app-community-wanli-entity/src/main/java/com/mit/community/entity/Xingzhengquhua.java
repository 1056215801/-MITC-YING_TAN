package com.mit.community.entity;


import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 全国行政区划信息
 * </p>
 *
 * @author qsj
 * @since 2019-08-26
 */
@Data
@ApiModel(value="Xingzhengquhua对象", description="全国行政区划信息")
@AllArgsConstructor
@NoArgsConstructor
@TableName("Xingzhengquhua")
public class Xingzhengquhua implements Serializable{

    private static final long serialVersionUID = 1L;


    @TableField("province_name")
    private String provinceName;

    @TableField("province_id")
    private String provinceId;

    @TableField("city_name")
    private String cityName;

    @TableField("city_id")
    private String cityId;

    @TableField("district_name")
    private String districtName;

    @TableField("district_id")
    private String districtId;

    @TableField("town_name")
    private String townName;

    @TableField("town_id")
    private String townId;

    @TableField("committee_name")
    private String committeeName;

    @TableId(value = "committee_id",type = IdType.ID_WORKER_STR)
    private String committeeId;

    @TableField("type_name")
    private String typeName;

    @TableField("type_id")
    private String typeId;

    @TableField("lng")
    private String lng;

    @TableField("lat")
    private String lat;


}
