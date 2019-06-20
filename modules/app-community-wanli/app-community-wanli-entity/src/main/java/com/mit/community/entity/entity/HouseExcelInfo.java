package com.mit.community.entity.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HouseExcelInfo {
    private Integer zone_id;
    private String zone_name;
    private Integer building_id;
    private String building_name;
    private Integer unit_id;
    private String unit_name;
    private Integer room_id;
    private String room_name;
    private String area;//占地面积
    private String buyTime;
    private String fwdz;//地址
    private String houseAttr;//房屋用途
    private String houseRelation;//房屋关系
    private String houseUsage;//房屋用途
    private String mortgage;//是否按揭

    private String name;
    private String gender;
    private int age;
    private String idCardNum;
    private String cellphone;
    private String placeOfResideDetail;

    private Integer id;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtCreate;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtModified;
    private Integer person_baseinfo_id;
    private int isDelete;
}
