package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author HuShanLin
 * @Date Created in 17:46 2019/6/27
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

    @TableField("access_type")
    private String accessType;

    private LocalDateTime passtime;

    @TableField("car_owner")
    private String carOwner;

    @TableField("owner_phone")
    private String ownerPhone;

    @TableField("car_num_patch")
    private String image;
}