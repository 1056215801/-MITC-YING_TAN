package com.mit.community.entity.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.mit.community.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("militaryservice_info")
public class MilitaryServiceInfo extends BaseEntity {
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
    private Integer person_baseinfo_id;
    private int isDelete;
}
