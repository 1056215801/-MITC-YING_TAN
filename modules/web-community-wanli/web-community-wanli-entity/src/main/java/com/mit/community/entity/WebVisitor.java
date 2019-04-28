package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 访客表
 * @author xiong
 */
@TableName("visitor")
@Data
public class WebVisitor {
    /**
     * 小区名称
     */
    @TableField("community_name")
    private String communityName;
    /**
     * 邀请人
     */
    @TableField("invite_name")
    private String inviteName;
    /**
     * 邀请人手机号码
     */
    @TableField("invite_moblie")
    private String inviteMobile;
    /**
     * 以下为到访房屋信息
     */
    /**
     * 分区id
     */
    @TableField("zone_id")
    private Integer zoneId;
    /**
     * 到访分区名称
     */
    @TableField("zone_name")
    private String zoneName;
    /**
     * 楼栋id
     */
    @TableField("building_id")
    private Integer buildingId;
    /**
     * 楼栋名称
     */
    @TableField("building_name")
    private String buildingName;
    /**
     * 单元id
     */
    @TableField("unit_id")
    private Integer unitId;
    /**
     * 单元名称
     */
    @TableField("unit_name")
    private String unitName;
    /**
     * 房间号
     */
    @TableField("room_num")
    private String roomNum;
    /**
     *邀请码
     */
    @TableField("invite_Code")
    private String inviteCode;
    /**
     *邀请码类型（今日无限次数，高级设置）？
     */
    @TableField("invitecode_type")
    private String inviteCodeType;
    /**
     * 邀请码使用状态（未使用、已使用、已过期）？
     */
    @TableField("data_status")
    private String dataStatus;
    /**
     * 到访时间？？
     */
    @TableField("visit_date")
    private LocalDateTime visitDate;
    /**
     * 有效期限
     */
    @TableField("expiry_date")
    private LocalDateTime expiryDate;
}
