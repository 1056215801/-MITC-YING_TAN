package com.mit.community.entity.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.mit.community.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("zdqsn_info")
public class ZDQSNCInfo extends BaseEntity {
    private String rylx;
    private String jtqk;
    private String jhrsfz;
    private String jhrxm;
    private String yjhrgx;
    private String jhrlxfs;
    private String jhrjzxxdz;
    private String sfwffz;
    private String wffzqk;
    private String bfrlxfs;
    private String bfsd;
    private String bfqk;
    private Integer person_baseinfo_id;
}
