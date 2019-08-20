package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("person_label")
public class PersonLabel extends BaseEntity {
    private String labelName;
    private String remarks;
    private String communityCode;
}
