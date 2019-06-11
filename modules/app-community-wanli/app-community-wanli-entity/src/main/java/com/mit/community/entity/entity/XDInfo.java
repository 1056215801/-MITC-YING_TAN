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
@TableName("xd_info")
public class XDInfo extends BaseEntity {

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime ccfxsj;
    private String gkqk;
    private String gkrxm;
    private String gkrlxfs;
    private String bfqk;
    private String bfrxm;
    private String bfrlxfs;
    private String ywfzs;
    private String xdqk;
    private String xdyy;
    private String xdhg;
    private Integer person_baseinfo_id;
    private int isDelete;
}
