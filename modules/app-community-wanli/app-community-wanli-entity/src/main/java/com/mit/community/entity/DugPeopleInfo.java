package com.mit.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DugPeopleInfo {
    private String appUserName;
    private String role;
    private String communityName;
    private String expireTime;
    private String name;
    private String cellPhone;
    private Integer status;//1启用；2停用
    private String accountType;
}
