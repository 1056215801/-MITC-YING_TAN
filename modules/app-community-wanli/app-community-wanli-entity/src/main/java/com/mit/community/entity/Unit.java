package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 单元表
 * @author Mr.Deng
 * @date 2018/12/7 18:11
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("unit")
public class Unit extends BaseEntity {
    /**
     * 小区code
     */
    @TableField("community_code")
    private String communityCode;
    /**
     * 分区ID(为空时取默认分区)
     */
    @TableField("zone_id")
    private Integer zoneId;
    /**
     * 楼栋ID
     */
    @TableField("building_id")
    private Integer buildingId;
    /**
     * 单元id
     */
    @TableField("unit_id")
    private Integer unitId;
    /**
     * 单元编号
     */
    @TableField("unit_code")
    private String unitCode;
    /**
     * 单元名称
     */
    @TableField("unit_name")
    private String unitName;
    /**
     * 单元状态（0：停用；1：启用）
     */
    @TableField("unit_status")
    private Integer unitStatus;

    @TableField("sort")
    private Integer sort;

    @TableField(exist = false)
    private String zoneName;

    @TableField(exist = false)
    private String buildingName;
}
