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
@TableName("zszh_info")
public class ZSZHInfo extends BaseEntity {
    private String jtjjzk;
    private String sfnrdb;
    private String jhrsfzh;
    private String jhrxm;
    private String jhrlxfs;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime ccfbrq;
    private String mqzdlx;
    private String ywzszhs;
    private int zszhcs;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime sczszhrq;
    private String mqwxxpgdj;
    private String zlqk;
    private String zlyy;
    private String sszyzlyy;
    private String jskfxljgmc;
    private String cyglry;
    private String bfqk;
    private Integer person_baseinfo_id;
    private int isDelete;
}
