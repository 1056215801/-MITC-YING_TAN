package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("visitor_invite_code")
public class VisitorInviteCode extends BaseEntity{
    private String cellphone;

    @TableField("data_tag")
    private String dataTag;
    private int times;

    @TableField("device_group_id")
    private String deviceGroupId;

    @TableField("community_code")
    private String communityCode;

    @TableField("invite_code")
    private String inviteCode;

    @TableField("use_times")
    private int useTimes;//已经使用次数

    @TableField("expiry_date")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date expiryDate;//有效期
}
