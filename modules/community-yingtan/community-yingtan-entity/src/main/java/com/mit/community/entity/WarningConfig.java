package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("warningConfig")
public class WarningConfig extends BaseEntity{
    private String label;
    private int isWarning;
    private String warningInfo;
    private String communityCode;
}
