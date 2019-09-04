package com.mit.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 接受前端房屋数组实体类映射
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseRoomsVo {

    private String id;

    private String zoneId;

    private String zoneName;

    private String buildingId;

    private String buildingName;

    private String unitId;

    private String unitName;

    private String roomId;

    private String roomNum;

    private String householdType;
}
