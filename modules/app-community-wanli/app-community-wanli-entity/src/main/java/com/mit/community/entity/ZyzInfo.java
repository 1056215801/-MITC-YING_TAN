package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("zyz_info")
public class ZyzInfo extends BaseEntity{
    private Integer person_baseinfo_id;
    private int isDelete;
    private String isReg;
    private String organization;
    private String regNumber;
    private String serviceExp;
    private String serviceType;
    private String specialSkills;
}
