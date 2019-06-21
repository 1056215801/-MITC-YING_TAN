package com.mit.community.entity.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ZSZHExcelInfo {
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

    private String jtjjzk;
    private String sfnrdb;
    private String jhrsfzh;
    private String jhrxm;
    private String jhrlxfs;
    private String ccfbrq;
    private String mqzdlx;
    private String ywzszhs;
    private int zszhcs;
    private String sczszhrq;
    private String mqwxxpgdj;
    private String zlqk;
    private String zlyy;
    private String sszyzlyy;
    private String jskfxljgmc;
    private String cyglry;
    private String bfqk;
}
