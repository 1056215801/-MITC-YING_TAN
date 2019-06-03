package com.mit.community.entity.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.mit.community.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("sqjzpeople_info")
public class SQJZPeopleinfo extends BaseEntity {
    private String sqjzrybh;
    private String yjycs;
    private String jzlb;
    private String ajlb;
    private String jtzm;
    private String ypxq;
    //@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private String ypxkssj;
    //@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private String ypxjssj;
    //@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private String jzkssj;
    //@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private String jzjssj;
    private String jsfs;
    private String ssqk;
    private String sflgf;
    private String ssqku;
    private String sfjljzxz;
    private String jzjclx;
    private String sfytg;
    private String tgyy;
    private String jcjdtgqk;
    private String tgjzqk;
    private String sfylg;
    private String lgyy;
    private String jcjdlgqk;
    private String lgjzqk;
    private String jcqk;
    private String xfbgzx;
    private String sfcxfz;
    private String cxfzmc;
    private Integer person_baseinfo_id;

}
