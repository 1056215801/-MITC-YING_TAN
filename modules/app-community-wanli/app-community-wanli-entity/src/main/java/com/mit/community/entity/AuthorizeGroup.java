package com.mit.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthorizeGroup {
    private Integer deviceGroupId;
    private String deviceGroupName;
    private int isSelect;//是否勾选（1选，2不选）
}
