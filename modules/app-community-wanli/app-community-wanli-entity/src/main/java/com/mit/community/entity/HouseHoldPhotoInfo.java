package com.mit.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HouseHoldPhotoInfo {
    private Integer id;
    private String name;
    private String mobile;
    private String photoNetUrl;
    private String deviceName;
    private String diffiTime;
    private int isOnline;//1在线，2离线
    private int isUpload;
}
