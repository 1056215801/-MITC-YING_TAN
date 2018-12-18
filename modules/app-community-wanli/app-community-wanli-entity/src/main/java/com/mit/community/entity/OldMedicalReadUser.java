package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 老人体检信息已读表
 * @author Mr.Deng
 * @date 2018/12/18 19:36
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("old_medical_read_user")
public class OldMedicalReadUser extends BaseEntity {
    /**
     * 关联user表id
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 关联old_medical表id
     */
    @TableField("old_medical_id")
    private Integer oldMedicalId;
}
