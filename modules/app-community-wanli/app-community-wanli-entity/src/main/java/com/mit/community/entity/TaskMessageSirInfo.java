package com.mit.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TaskMessageSirInfo {
    private Integer userId;
    private Integer wgyId;
    private String jb;//上级部门名称
}
