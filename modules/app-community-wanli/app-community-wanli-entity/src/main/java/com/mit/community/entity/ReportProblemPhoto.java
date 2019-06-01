package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.mit.community.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("report_problem_photo")
public class ReportProblemPhoto extends BaseEntity {
    private Integer reportProblemId;
    private String base64;
}
