package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 楼栋表
 * @author Mr.Deng
 * @date 2018/12/7 17:58
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("building")
public class Building extends BaseEntity {
    /**
     * 小区code
     */
    @TableField("community_code")
    private String communityCode;
    /**
     * 分区id
     */
    @TableField("zone_id")
    private Integer zoneId;
    /**
     * 楼栋ID
     */
    @TableField("building_id")
    private Integer buildingId;
    /**
     * 楼栋编号
     */
    @TableField("building_code")
    private String buildingCode;
    /**
     * 楼栋名称
     */
    @TableField("building_name")
    private String buildingName;
    /**
     * 楼栋状态（0：停用；1：启用）
     */
    @TableField("building_status")
    private Integer buildingStatus;
}
