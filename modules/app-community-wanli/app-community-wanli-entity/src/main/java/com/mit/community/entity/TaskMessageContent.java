package com.mit.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TaskMessageContent {
    private String wgyName;
    private String cellPhone;
    private String userName;//事件上报用户的姓名
    private LocalDateTime gmtCreate;//事件上报事件
    private String problemType;//上报的事件类型
}
