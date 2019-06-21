package com.mit.community.entity.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MilitaryServiceExcelInfo {
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

    private String xyqk;
    private String zybm;
    private String zymc;
    private String zytc;
    private String cylb;
    private String jdxx;
    private String zyzgzs;
    private String hscjdcsjjsxl;
    private String sg;
    private String tz;
    private String zylysl;
    private String yylysl;
    private String jkzk;
    private String stmc;
    private String bsdc;
    private String wcqk;
    private String zzcs;
    private String bydjjl;
    private String yy;
    private String djxs;
    private int sftj;
    private String bz;
}
