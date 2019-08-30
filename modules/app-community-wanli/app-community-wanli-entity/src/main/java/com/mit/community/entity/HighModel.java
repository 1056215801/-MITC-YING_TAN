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
@TableName("High_Model")
public class HighModel extends BaseEntity{
    @TableField("visitor_invite_code_id")
    private Integer visitorInviteCodeId;

    @TableField("time_day")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date timeDay;

    @TableField("time_start")
    private String timeStart;

    @TableField("time_end")
    private String timeEnd;

}
