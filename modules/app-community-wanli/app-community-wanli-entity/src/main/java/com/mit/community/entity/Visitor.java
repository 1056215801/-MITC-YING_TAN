package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 访客表
 * @author Mr.Deng
 * @date 2018/12/3 16:31
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@TableName("visitor")
@Data
public class Visitor extends BaseEntity {
    /**
     * 小区code
     */
    @TableField("community_code")
    private String communityCode;
    /**
     * 小区名称
     */
    @TableField("community_name")
    private String communityName;
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
     * 访客id
     */
    @TableField("visitor_id")
    private Integer visitorId;
    /**
     * 邀请方式（1：APP；2：手动登记）
     */
    @TableField("invite_type")
    private Integer inviteType;
    /**
     * 邀请人
     */
    @TableField("invite_name")
    private String inviteName;
    /**
     * 邀请人手机号
     */
    @TableField("invite_mobile")
    private String inviteMobile;
    /**
     * 有效期限
     */
    @TableField("expiry_date")
    private LocalDateTime expiryDate;
    /**
     * 访客状态（1：已到访；2：未到访；3：已离开；4：手动签离；5：已撤销；6：到访超时；7：超时手动签离）
     */
    @TableField("visitor_status")
    private Integer visitorStatus;
    /**
     * 访客姓名
     */
    @TableField("visitor_name")
    private String visitorName;
    /**
     * 访客手机号
     */
    @TableField("visitor_mobile")
    private String visitorMobile;

}
