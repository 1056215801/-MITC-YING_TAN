package com.mit.community.entity;


import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
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
 * @since 2019-09-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Equipment对象", description="")
@TableName("equipment")
public class Equipment extends BaseEntity {

    private static final long serialVersionUID = 1L;


    @TableField("serial_number")
    @ApiModelProperty(value = "设备编号:群防群治接口获取")
    private String serialNumber;

    @TableField("user_id")
    @ApiModelProperty(value = "设备id:群防群治获取")
    private long userId;

    @TableField("device_name")
    @ApiModelProperty(value = "设备名称")
    private String deviceName;


    @TableField("device_holder")
    @ApiModelProperty(value = "设备持有人")
    private String deviceHolder;

    @TableField("phone")
    @ApiModelProperty(value = "联系电话")
    private String phone;

    @TableField("department_no")
    @ApiModelProperty(value = "部门编号")
    private String departmentNo;

    @TableField("name")
    @ApiModelProperty(value = "部门名称")
    private String name;

    @TableField("job")
    @ApiModelProperty(value = "岗位:民警，巡防员，群防群治，消防大队，交警，重点单位，物业，内保组织，网格员，楼管员")
    private String job;

    @TableField("remark")
    @ApiModelProperty(value = "备注")
    private String remark;
    /**
     * 定位类型：0：没有定位，1：GPS定位，2：wifi定位
     */
    @TableField(exist = false)
    private String  type;

    /**
     * 设备清晰度
     */
    @TableField(exist = false)
    private String EquipmentClarity;
}
