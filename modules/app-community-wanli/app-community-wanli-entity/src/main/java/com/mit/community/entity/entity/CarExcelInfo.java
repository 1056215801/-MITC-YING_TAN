package com.mit.community.entity.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CarExcelInfo {
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

    private String cph;
    private String cx;
    private String ys;
    private String pp;
    private String xh;
    private String pl;
    private String fdjh;
    private String jsz;
    private String xsz;
    private String szrq;
    private String gmrq;
}
