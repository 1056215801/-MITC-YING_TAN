package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("problem_schedule")
public class ProblemSchedule extends BaseEntity{
    private Integer reportProblemId;
    private String dept;//受理部门
    private String status;
    private String content;
    private Integer userId;
}
