package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 失物招领已读表
 * @author Mr.Deng
 * @date 2018/12/17 20:28
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("lost_fount_read_user")
public class LostFountReadUser extends BaseEntity {
    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 失物招领id
     */
    @TableField("lost_fount_id")
    private Integer lostFountId;
}
