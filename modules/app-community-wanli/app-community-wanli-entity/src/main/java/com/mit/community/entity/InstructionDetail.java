package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 操作指南详情
 *
 * @author shuyy
 * @date 2018/12/20
 * @company mitesofor
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("instruction_detail")
public class InstructionDetail extends BaseEntity {
    /**
     * 指南详情
     */
    private String detail;
    /**
     * 指南id
     */
    @TableField("instruction_id")
    private Integer instruction_id;

}
