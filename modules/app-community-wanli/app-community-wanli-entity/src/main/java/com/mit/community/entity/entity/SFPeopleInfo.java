package com.mit.community.entity.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.mit.community.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("sfpeople_info")
public class SFPeopleInfo extends BaseEntity {
    private String sfqzxf;
    private int lxcs;
    private int ldcs;
    //@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private String sfsj;
    private int sfrs;
    private String sffsdd;
    private String sfrysq;
    private String clqkbf;
    private Integer person_baseinfo_id;
}
