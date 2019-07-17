package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义人员属性
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("zdy_person_label")
public class ZdyPersonLabel extends BaseEntity {
    @TableField("person_baseinfo_id")
    private Integer personBaseinfoId;

    private String label;

    private String remarks;
}
