package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 社区服务表
 * @author Mr.Deng
 * @date 2018/12/5 11:27
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("community_service_info")
public class CommunityServiceInfo extends BaseEntity {
    /**
     * 小区code
     */
    @TableField("community_code")
    private String communityCode;
    /**
     * 名称
     */
    private String name;
    /**
     * 地址
     */
    private String address;
    /**
     * 联系电话
     */
    private String cellphone;
    /**
     * 距离
     */
    private Integer distance;
    /**
     * 坐标
     */
    private String coordinate;
    /**
     * 图片
     */
    private String image;
    /**
     * 社区服务类型。关联字典code community_service_type 社区服务类型 1、社区门诊2、开锁换锁3、送水到家
     */
    private String type;
    /**
     * 创建人。关联user表id
     */
    @TableField("creator_user_id")
    private Integer creatorUserId;

}
