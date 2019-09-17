package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author qsj
 * @since 2019-09-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="WebSysDepartment对象", description="")
@TableName("web_sys_department")
public class WebSysDepartment extends BaseEntity{

    private static final long serialVersionUID = 1L;

    @TableField("name")
    @ApiModelProperty(value = "部门名称")
    private String name;

    @TableField("department_no")
    @ApiModelProperty(value = "部门编号")
    private String departmentNo;

    @TableField("province_name")
    @ApiModelProperty(value = "省名称")
    private String provinceName;

    @TableField("province_id")
    @ApiModelProperty(value = "省编码")
    private String provinceId;

    @TableField("city_name")
    @ApiModelProperty(value = "市名称")
    private String cityName;

    @TableField("city_id")
    @ApiModelProperty(value = "市编码")
    private String cityId;

    @TableField("district_name")
    @ApiModelProperty(value = "区名称")
    private String districtName;

    @TableField("district_id")
    @ApiModelProperty(value = "区编码")
    private String districtId;

    @TableField("town_name")
    @ApiModelProperty(value = "镇或街道名称")
    private String townName;

    @TableField("town_id")
    @ApiModelProperty(value = "镇或街道编码")
    private String townId;

    @TableField("village_name")
    @ApiModelProperty(value = "居委或委员会名称")
    private String villageName;

    @TableField("village_id")
    @ApiModelProperty(value = "居委或委员会编码")
    private String villageId;


}
