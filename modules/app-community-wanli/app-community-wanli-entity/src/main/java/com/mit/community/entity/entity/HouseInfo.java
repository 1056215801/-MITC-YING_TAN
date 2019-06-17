package com.mit.community.entity.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.mit.community.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("household_info")
public class HouseInfo extends BaseEntity {
    /*@TableField(exist = false)
    private String community_code;
    @TableField(exist = false)
    private String community_name;*/
    private Integer zone_id;
    private String zone_name;
    private Integer building_id;
    private String building_name;
    private Integer unit_id;
    private String unit_name;
    private Integer room_id;
    @TableField("room_num")
    private String room_name;
    /*@TableField(exist = false)
    private String household_type;*/
    private Integer person_baseinfo_id;
    private String area;//占地面积
    private String buyTime;
    private String fwdz;//地址
    private String houseAttr;//房屋用途
    private String houseRelation;//房屋关系
    private String houseUsage;//房屋用途
    private String mortgage;//是否按揭
}
