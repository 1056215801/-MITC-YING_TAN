package com.mit.community.entity.com.mit.community.entity.hik;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.mit.community.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("strange_visitor_info")
public class StrangeVisitorInfo  extends BaseEntity {

    @TableField("community_code")
    private String communityCode;
    @TableField("community_name")
    private String communityName;
    @TableField("visitor_num")
    private int visitorNum;

    @TableField("last_visitor_time")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastVisitorTime;

}
