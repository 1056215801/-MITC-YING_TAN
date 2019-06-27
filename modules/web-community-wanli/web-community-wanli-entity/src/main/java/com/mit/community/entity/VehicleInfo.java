package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author HuShanLin
 * @Date Created in 17:54 2019/6/27
 * @Company: mitesofor </p>
 * @Description:~车辆信息
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("vehicle_info")
public class VehicleInfo extends BaseEntity {

    @TableField("car_num")
    private String carnum;

    @TableField("car_brand")
    private String brand;

    @TableField("car_model")
    private String carmodel;

    @TableField("car_color")
    private String carcolor;

    @TableField("pro_date")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date proDate;

    @TableField("purchase_date")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date purchaseDate;

    private double displacement;

    @TableField("driver_license")
    private String driverLicense;

    @TableField("vehicle_license")
    private String vehicleLicense;

    @TableField("owner_phone")
    private String ownerPhone;

    private int isdel;
}
