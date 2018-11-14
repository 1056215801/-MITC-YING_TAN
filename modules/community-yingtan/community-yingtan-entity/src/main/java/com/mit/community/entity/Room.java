package com.mit.community.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

/**
 * 房间表
 *
 * @author Mr.Deng
 * @date 2018/11/14 17:47
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Data
@TableName("room")
public class Room extends Model<Room> {
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 小区Code
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
