package com.mit.community.entity.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.mit.community.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("bear_info")
public class BearInfo extends BaseEntity {
    private String poxm;
    private String poxb;
    private String xgzdw;
    private String djjhny;
    private String hkxz;
    private String hyzk;
    private String jysssj;
    private String sslx;
    private String ssyy;
    private String ccyy;
    private Integer person_baseinfo_id;
    private  int isDelete;
}
