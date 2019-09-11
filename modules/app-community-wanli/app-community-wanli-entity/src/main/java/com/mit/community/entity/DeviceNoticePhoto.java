package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("device_notice_photo")
public class DeviceNoticePhoto extends BaseEntity{
    @TableField("net_url")
    private String netUrl;

    @TableField("local_url")
    private String localUrl;

    @TableField("device_notice_id")
    private Integer deviceNoticeId;
}
