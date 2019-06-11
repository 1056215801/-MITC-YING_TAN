package com.mit.community.entity.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.mit.community.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("staypeople_info")
public class StayPeopleInfo extends BaseEntity {
    private String jkzk;
    private String grnsr;
    private String rhizbz;
    private String lsrylx;
    private String jtzyrysfzh;
    private String jtzycyxm;
    private String jtzycyjkzk;
    private String ylsrygx;
    private String jtzycylxfs;
    private String jtzycygzxxdz;
    private String jtnsr;
    private String knjsq;
    private String bfqk;
    private Integer person_baseinfo_id;
    private int isDelete;
}
