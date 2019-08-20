package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("wgy_info")
public class WgyInfo extends BaseEntity{
    private Integer person_baseinfo_id;
    private int isDelete;
    private String gddh;
    private String jtzycygzdwjzw;
    private String jtzycyjkzk;
    private String jtzycylxfs;
    private String jtzycyxm;
    private String jtzyrysfzh;
    private String officeTime;
    private String workCondition;
    private String jb;//是街道还是管理处
    private int isFzr;//是否为负责人，0否，1是
    private String dept;//所在单位
    private String sjDept;//上级单位
}
