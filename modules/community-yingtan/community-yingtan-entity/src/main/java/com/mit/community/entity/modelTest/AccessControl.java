package com.mit.community.entity.modelTest;

import lombok.Data;

/**
 * 门禁记录
 *
 * @author Mr.Deng
 * @date 2018/11/14 11:26
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Data
public class AccessControl {
    private String id;
    private String householdName;
    private String accessTime;
    private String buildingName;
    private String householdId;
    private String buildingCode;
    private String accessImgUrl;
    private String zoneName;
    private String householdMobile;
    private String deviceNum;
    private String deviceName;
    private String interactiveType;
    private String unitName;
    private String cardNum;
    private String unitCode;

}
