package com.mit.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeviceAddressInfo extends BaseEntity{
    private Integer parentAddressId;
    private String addressName;
    private List<DeviceAddressInfo> deviceAddressInfo;
}
