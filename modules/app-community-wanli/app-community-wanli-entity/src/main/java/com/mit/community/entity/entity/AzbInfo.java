package com.mit.community.entity.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.mit.community.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("azb_info")
public class AzbInfo extends BaseEntity {
    /**
     *
     */
    private String grtj;
    private String sfwf;
    private String wffzqk;
    private String ajlb;
    private String gzlx;
    private String bfqk;
    private String bfrdh;
    private String bfrxm;
    private String szqk;
    private String szjgmc;
    private Integer person_baseinfo_id;
    private int isDelete;
}
