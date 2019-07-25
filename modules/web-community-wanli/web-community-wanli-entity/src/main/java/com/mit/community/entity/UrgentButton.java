package com.mit.community.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UrgentButton {
    private String deviceNum;
    private String deviceName;
    private String devicePlace;
    private String deviceType;
    private String name;
    private String phone;
    private String guarderPhone;//监护人电话
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime addTime;
    private int deviceStatus;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataReportTime;
}
