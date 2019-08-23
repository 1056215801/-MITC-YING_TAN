package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("linshi_a")
public class WarnInfo extends BaseEntity {
    private String place;
    private String phone;
    private String problem;
    private String warnInfo;
    private String name;
    private String cyrPhone;
    private String jhrPhone;
    private String communityCode;
    private Integer problemId;
}
