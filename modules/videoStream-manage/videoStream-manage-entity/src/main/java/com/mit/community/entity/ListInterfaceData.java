package com.mit.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ListInterfaceData {
    private List<DeviceInfo> deviceInfo;
    private List<DeviceAddressInfo> DeviceAddressInfo;
}
