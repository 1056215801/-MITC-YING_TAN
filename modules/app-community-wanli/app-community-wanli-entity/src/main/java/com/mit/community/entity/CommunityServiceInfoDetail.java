package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 社区服务详情
 *
 * @author shuyy
 * @date 2018/12/20
 * @company mitesofor
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("community_service_info_detail")
public class CommunityServiceInfoDetail extends BaseEntity {
    /**
     * 社区服务id
     */
    @TableField("community_service_info_id")
    private Integer communityServiceInfoId;
    /**
     * 详情
     */
    private String detail;
}
