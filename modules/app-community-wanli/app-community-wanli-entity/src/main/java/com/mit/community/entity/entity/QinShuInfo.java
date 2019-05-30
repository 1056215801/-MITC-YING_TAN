package com.mit.community.entity.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.mit.community.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("qinshu_info")
public class QinShuInfo extends BaseEntity {
    private String gx;
    private String xm;
    private String gmsszhm;
    private String lxdh;
    private Integer person_baseinfo_id;
}
