package com.mit.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeviceInfo {
    private String deviceName;
    private String deviceNum;
    private String deviceMac;
    private String deviceCode;
    private String deviceType;
    private int deviceStatus;//1在线；2离线
    private String deviceSip;
    private String buildingCode;
    private String unitCode;
    private String timeDiffi;
    private String verison;
    private String buildingName;
    private String unitName;
}
