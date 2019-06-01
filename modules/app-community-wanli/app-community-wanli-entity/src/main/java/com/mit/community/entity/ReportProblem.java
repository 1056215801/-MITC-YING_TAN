package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("report_problem")
public class ReportProblem extends BaseEntity {
    private Integer userId;
    private String content;
    private String problemType;
    private String address;
    private int isOpen;
    private int status;
}
