package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 社区电话表
 * @author Mr.Deng
 * @date 2018/12/5 15:47
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("community_phone")
public class CommunityPhone extends BaseEntity {
    /**
     * 小区code。关联cluster_community表community_code字段。
     */
    @TableField("community_code")
    private String communityCode;
    /**
     * 电话备注名称
     */
    private String name;
    /**
     * 电话
     */
    private String phone;
    /**
     * 电话类型.关联字典code community_phone_type   社区电话类型1、物业电话；2、紧急电话
     */
    private String type;
    /**
     * 创建人。关联user表id
     */
    @TableField("creator_user_id")
    private Integer creatorUserId;
}
