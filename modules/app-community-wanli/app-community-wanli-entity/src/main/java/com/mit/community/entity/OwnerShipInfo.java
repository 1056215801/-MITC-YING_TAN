package com.mit.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OwnerShipInfo {
    private String carOwner;
    private String ownerPhone;
    private String zoneName;
    private String buildingName;
    private String unitName;
    private String roomNum;
}
