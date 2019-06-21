package com.mit.community.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ZyzExcelInfo {
    private String isReg;
    private String organization;
    private String regNumber;
    private String serviceExp;
    private String serviceType;
    private String specialSkills;

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
