package com.mit.community.entity.modelTest;

import lombok.Data;

/**
 * 住户
 *
 * @author Mr.Deng
 * @date 2018/11/13 18:14
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Data
public class HouseTest {
    private String householdName;
    private String householdType;
    private String buildingName;
    private String authorizeStatus;
    private String householdId;
    private String zoneName;
    private String doorDeviceGroupIds;
    private String householdStatus;
    private String unitName;
    private String roomNum;
    private String gender;
    private String residenceTime;
    private String appDeviceGroupIds;
    private String mobile;
    /**
     * SIP账号
     */
    private String sipAccount;
    /**
     * SIP密码
     */
    private String sipPassword;
}
