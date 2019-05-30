package com.mit.community.entity.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.mit.community.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("census_Info")
public class CensusInfo extends BaseEntity {
    private String rhyzbz;
    private String hh;
    private String yhzgx;
    private String hzsfz;
    private Integer person_baseinfo_id;
}
