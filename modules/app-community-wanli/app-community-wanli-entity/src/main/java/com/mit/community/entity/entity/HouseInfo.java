package com.mit.community.entity.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.mit.community.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("house_info")
public class HouseInfo extends BaseEntity {
    private String community_code;
    private String community_name;
    private Integer zone_id;
    private String zone_name;
    private Integer building_id;
    private String building_name;
    private Integer unit_id;
    private String unit_name;
    private Integer room_id;
    private String room_num;
    private String household_type;
    private Integer person_baseinfo_id;
}
