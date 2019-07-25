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
 * @Description:~车辆通行记录
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("access_record")
public class AccessRecord extends BaseEntity {

    @TableField("car_num")
    private String carnum;

    @TableField("access_type")
    private String accessType;

    private LocalDateTime passtime;

    @TableField("car_owner")
    private String carOwner;

    @TableField("owner_phone")
    private String ownerPhone;

    @TableField("owner_house")
    private String ownerHouse;

    @TableField("car_num_patch")
    private String image;

    @TableField("in_gate_name")
    private String inGateName;//入场名称

    @TableField("sf_gate")
    private String sfGate;//收费口号

    @TableField("care_parkno")
    private String careParkNo;//车场编号

    private String community_code;
}
