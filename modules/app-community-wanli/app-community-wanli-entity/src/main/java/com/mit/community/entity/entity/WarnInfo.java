package com.mit.community.entity.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.mit.community.entity.BaseEntity;
import com.mit.community.entity.ReportProblemPhotos;
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

    /*@TableField(exist = false)
    private String deviceName;
    @TableField(exist = false)
    private String deviceNum;
    @TableField(exist = false)
    private String deviceType;

    @TableField(exist = false)
    private String clr;
    @TableField(exist = false)
    private LocalDateTime clrq;
    @TableField(exist = false)
    private String clsm;
    @TableField(exist = false)
    private int status;
    @TableField(exist = false)
    private List<ReportProblemPhotos> photos;*/

}
