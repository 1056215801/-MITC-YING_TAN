package com.mit.community.entity.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StayPeopleExcelInfo {
    private String jkzk;
    private String grnsr;
    private String rhizbz;
    private String lsrylx;
    private String jtzyrysfzh;
    private String jtzycyxm;
    private String jtzycyjkzk;
    private String ylsrygx;
    private String jtzycylxfs;
    private String jtzycygzxxdz;
    private String jtnsr;
    private String knjsq;
    private String bfqk;

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
