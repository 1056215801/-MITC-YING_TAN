package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("ldz_info")
public class LdzInfo extends BaseEntity{
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
}
