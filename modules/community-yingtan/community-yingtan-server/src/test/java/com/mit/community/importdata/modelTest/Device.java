package com.mit.community.importdata.modelTest;

import lombok.Data;

/**
 * 设备
 *
 * @author Mr.Deng
 * @date 2018/11/14 10:04
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Data
public class Device {
    private String deviceNum;
    private String deviceName;
    private String deviceType;
    private String deviceCode;
    private String deviceStatus;
    private String buildingCode;
    private String deviceSip;
    private String deviceId;
    private String unitCode;
}
