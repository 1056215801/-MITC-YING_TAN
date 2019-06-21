package com.mit.community.entity.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class XmsfExcelInfo {
    private String sflf;
    private String yzm;
    private String ypxq;
    private String fxcs;
    private String sfrq;
    private String wxxpglx;
    private String xjrq;
    private String xjqk;
    private String azrq;
    private String azqk;
    private String wazyy;
    private String bjqk;
    private String sfcxfz;
    private String cxfzzm;

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
