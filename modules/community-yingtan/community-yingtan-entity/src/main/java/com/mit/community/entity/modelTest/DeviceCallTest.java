package com.mit.community.entity.modelTest;

import lombok.Data;

/**
 * 呼叫记录
 *
 * @author Mr.Deng
 * @date 2018/11/14 11:37
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Data
public class DeviceCallTest {

    private String deviceNum;
    private String callDuration;
    private String openDoorType;
    private String receiver;
    private String deviceName;
    private String callTime;
    private String callImgUrl;
    private String callType;
    private String roomNum;
}
