package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@TableName("visitor_apply")
@Data
public class VisitorApply extends BaseEntity {
    @TableField("visitor_id")
    private Integer visitorId;

    @TableField("device_group_id")
    private Integer deviceGroupId;

    @TableField("invite_code")
    private Integer inviteCode;


}
