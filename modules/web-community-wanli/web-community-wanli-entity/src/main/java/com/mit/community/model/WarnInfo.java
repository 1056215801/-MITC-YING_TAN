package com.mit.community.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.mit.community.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("linshi_a")
public class WarnInfo extends BaseEntity {
    private String place;
    private String phone;
    private String problem;
    private String warnInfo;
    private String name;
    private String cyrPhone;
    private String jhrPhone;
    private String communityCode;
    private Integer problemId;

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(exist = false)
    private LocalDateTime otherTime;

    @TableField(exist = false)
    private String deviceName;
    @TableField(exist = false)
    private String deviceNum;
    @TableField(exist = false)
    private String deviceType;

    @TableField(exist = false)
    private String clr;

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(exist = false)
    private LocalDateTime clrq;
    @TableField(exist = false)
    private String clsm;
    @TableField(exist = false)
    private int status;
    @TableField(exist = false)
    private List<ReportProblemPhotos> photos;

}
