package com.mit.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
//部门信息
public class DepartmentInfo {

    private String id; // 部门id
    private String name; //部门名称
    private String  parentId;  //上级机构ID
    private String  sort;    //排序（同部门下的显示顺序）


}
