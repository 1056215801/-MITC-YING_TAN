package com.mit.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccessCardPageInfo {
    private Integer id;

    private int cardType;//卡类型

    private int cardMedia;//卡介质(1ic卡)

    private String houseHoldName;
    private Integer houseType;//与户主关系
    private String mobile;
    private String validityTime;
    private List<HouseholdRoom> houses;
}
