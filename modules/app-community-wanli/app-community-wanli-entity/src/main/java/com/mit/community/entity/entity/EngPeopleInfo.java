package com.mit.community.entity.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.mit.community.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("engpeople_info")
public class EngPeopleInfo extends BaseEntity {
    private String wwx;
    private String wwm;
    private String zwm;
    private String gj;
    private String zjxy;
    private String zjdm;
    private String zjhm;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private String zjyxq;
    private String lhmd;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private String ddrq;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private String yjlkrq;
    private int sfzdry;
    private Integer person_baseinfo_id;
    @TableField(exist = false)
    private int isDelete;

}
