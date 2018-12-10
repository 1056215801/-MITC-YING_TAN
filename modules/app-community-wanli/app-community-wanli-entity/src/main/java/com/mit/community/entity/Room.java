package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 房间表
 * @author Mr.Deng
 * @date 2018/12/8 8:56
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("room")
public class Room extends BaseEntity {
    /**
     * 小区code
     */
    @TableField("community_code")
    private String communityCode;
    /**
     * 分区ID(为空取默认分区)
     */
    @TableField("zone_id")
    private Integer zoneId;
    /**
     * 楼栋ID
     */
    @TableField("building_id")
    private Integer buildingId;
    /**
     * 单元ID
     */
    @TableField("unit_id")
    private Integer unitId;
    /**
     * 房间ID
     */
    @TableField("room_id")
    private Integer roomId;
    /**
     * 房间编号
     */
    @TableField("room_num")
    private String roomNum;
    /**
     * 房间状态（0：停用；1：启用）
     */
    @TableField("room_status")
    private Integer roomStatus;
}
