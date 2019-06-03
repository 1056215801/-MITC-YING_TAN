package com.mit.community.entity.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.mit.community.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("party_info")
public class PartyInfo extends BaseEntity {
    //@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private String rdsq;
    //@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private String zzrq;
   // @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private String cjgzsj;
    private String rdszzb;
    private String zzszzb;
    private String zzszdw;
    private String szdzb;
    //@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private String jrdzbsj;
    private String xrdnzw;
    private String rdjsr;
    private String yyjndf;
    private Integer person_baseinfo_id;
}
