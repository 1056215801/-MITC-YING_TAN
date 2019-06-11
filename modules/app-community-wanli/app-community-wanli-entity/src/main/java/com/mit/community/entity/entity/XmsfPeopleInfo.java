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
@TableName("xmsfpeople_info")
public class XmsfPeopleInfo extends BaseEntity {

    private String sflf;
    private String yzm;
    private String ypxq;
    private String fxcs;
    private String sfrq;
    private String wxxpglx;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime xjrq;
    private String xjqk;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime azrq;
    private String azqk;
    private String wazyy;
    private String bjqk;
    private String sfcxfz;
    private String cxfzzm;
    private Integer person_baseinfo_id;
    private int isDelete;
}
