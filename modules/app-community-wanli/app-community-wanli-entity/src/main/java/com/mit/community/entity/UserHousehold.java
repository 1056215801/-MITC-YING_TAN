package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * 用户住户关联表
 * @author shuyy
 * @date 2018/11/30
 * @company mitesofor
 */
@Data
@TableName("user_household")
public class UserHousehold extends BaseEntity {

    /**
     * 用户id。关联user表id字段。
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 住户id。关联household表id字段。
     */
    @TableField("household_id")
    private Integer householdId;
    /**
     * 创建人id。关联user表id。
     */
    @TableField("creator_user_id")
    private Integer creatorUserId;

}
