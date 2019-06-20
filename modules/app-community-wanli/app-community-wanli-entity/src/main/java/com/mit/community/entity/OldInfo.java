package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("old_info")
public class OldInfo extends BaseEntity{
    private Integer person_baseinfo_id;
    private int isDelete;
    private String disease;
    private String householdName;
    private String isInsurance;
    private String jkzk;
    private String jtzycygzdwjzw;
    private String jtzycyjkzk;
    private String jtzycylxfs;
    private String jtzycyxm;
    private String jtzyrysfzh;
    private String knjsq;
}
