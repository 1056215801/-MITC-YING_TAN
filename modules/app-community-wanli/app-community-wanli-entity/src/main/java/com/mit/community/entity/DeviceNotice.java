package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("device_notice")
public class DeviceNotice extends BaseEntity{
    @TableField("community_code")
    private String communityCode;

    @TableField("community_name")
    private String communityName;

    private String title;

    private Integer type;//1紧急；2通知；3活动；4提示；5新闻

    private Integer canal;//1滚动文字；2图片轮播

    @TableField("start_time")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date startTime;

    @TableField("end_time")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date endTime;

    @TableField("play_time")
    private String playTime;

    private String content;

    private Integer status;//1正常；2停用；3过期

    @TableField(exist = false)
    private List<DeviceNoticePhoto> photos;
}
