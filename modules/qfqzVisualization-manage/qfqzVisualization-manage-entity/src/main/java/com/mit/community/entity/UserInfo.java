package com.mit.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
//设备信息
public class UserInfo {

    private String id; // 设备id
    private String serialNumber; //设备编号
    private String  nickName;  //设备名称
    private String  portrait;    //角色
    private String  departmentId; //部门ID
    private String  terminalType; //int	终端类型：1 一期  2 二期  5 三期

}
