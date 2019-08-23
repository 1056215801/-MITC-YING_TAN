package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("task_message")
public class TaskMessage extends BaseEntity{
    private Integer reportProblemId;
    private String title;
    private String content;
    private Integer wgyId;//网格员id
    private int status;//0未查看；1查看
    private int isRepeat;//是否转发上级 0否 1是
    private int mqlzd;
}
