package com.mit.community.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 我的钥匙
 * @author Mr.Deng
 * @date 2018/12/4 11:54
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Data
public class MyKey {
    /**
     * sip账户
     */
    private String sipAccount;
    /**
     * 设备编号
     */
    private String deviceNum;
    /**
     * 设备名称
     */
    private String deviceName;
    /**
     * 房间编号
     */
    private String roomNum;
    /**
     * 设备地址
     */
    private String deviceAddress;
    /**
     * 经纬度信息
     */
    private String deviceGps;
    /**
     * 楼栋编号
     */
    private String buildingCode;
    /**
     * 在线状态:1在线，0离线
     */
    private Integer onlineStatus;
    /**
     * 到期时间
     */
    private LocalDateTime dueDate;
    /**
     * 单元号
     */
    private String unitCode;

    /**
     * 小区名称
     */
    private String communityName;
    /**
     * 楼栋名称
     */
    private String buildingName;
    /**
     * 单元名称
     */
    private String unitName;
    /**
     * 分区名称
     */
    private String zoneName;
}
