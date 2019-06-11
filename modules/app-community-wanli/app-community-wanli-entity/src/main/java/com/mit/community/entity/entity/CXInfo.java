package com.mit.community.entity.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.mit.community.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("cx_Info")
public class CXInfo extends BaseEntity {
    private String dysxcx;
    private String drsxcx;
    private String dssxcx;
    private String bz;
    private Integer person_baseinfo_id;
    private int isDelete;

}
