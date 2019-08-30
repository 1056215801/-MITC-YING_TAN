package com.mit.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostHouseHoldInfoOne {
    private Integer houseHoldId;
    private String houseHoldName;
    private Integer gender;//(0：男；1：女)
    private String residenceTime;//居住期限
    private String mobile;
    private String idCard;
    private String householdType;
    private List<HouseRoomsVo> houseRoomsVoList;
}
