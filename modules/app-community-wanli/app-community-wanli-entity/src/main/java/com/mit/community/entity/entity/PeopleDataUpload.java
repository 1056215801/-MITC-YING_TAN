package com.mit.community.entity.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.mit.community.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("people_data_upload")
public class PeopleDataUpload extends BaseEntity{
    private String community;
    private String building;
    private String unit;
    private String room;
    private String relation;
    private String name;
    private String sex;
    private String idCard;
    private String phone;
    private String nation;
    private String jg;
    private String hyzk;
    private String zzmm;
    private String job;
    private String edu;
    private String image;
    private String carNum;
}
