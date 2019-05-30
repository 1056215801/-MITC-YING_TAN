package com.mit.community.entity.entity;

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
@TableName("flow_people_info")
public class FlowPeopleInfo extends BaseEntity {
    private String lryy;
    private String bzlx;
    private String zjhm;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime djrq;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime zjdqrq;
    private String zslx;
    private int sfzdgzry;
    private String tslxfs;
    private String jtzycylxfs;
    private String yhzgx;
    private String hzsfz;
    private Integer person_baseinfo_id;
}
