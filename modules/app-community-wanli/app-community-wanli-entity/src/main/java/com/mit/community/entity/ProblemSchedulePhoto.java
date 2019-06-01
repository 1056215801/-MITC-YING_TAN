package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("problem_schedule_photo")
public class ProblemSchedulePhoto extends BaseEntity{
    private Integer problemScheduleId;
    private String base64;
}
