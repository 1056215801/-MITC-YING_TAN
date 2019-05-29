package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 老人体检详情表
 *
 * @author Mr.Deng
 * @date 2018/12/18 19:34
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("old_medical_content")
public class OldMedicalContent extends BaseEntity {
    /**
     * 关联old_medical表id
     */
    @TableField("old_medical_id")
    private Integer oldMedicalId;
    /**
     * 详情信息
     */
    private String content;

}
