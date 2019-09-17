package com.mit.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
//设备定位信息
public class PosInfo {

    private Long userId; // 设备id
    private String lon; //经度
    private String  lat;  //纬度
    private String  type;    //定位类型：0-没有定位，1-GPS定位，2-wifi定位
    private Long  gpsTime; //最后定位时间（时间戳，精确到毫秒）
    private String power;  //电量


}
