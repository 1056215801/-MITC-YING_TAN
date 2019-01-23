package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 申请钥匙表
 * @author Mr.Deng
 * @date 2018/12/3 14:43
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@AllArgsConstructor
@NoArgsConstructor
@TableName("apply_key")
@Data
public class ApplyKey extends BaseEntity {
    /**
     * 小区code
     */
    @TableField("community_code")
    private String communityCode;
    /**
     * 小区名
     */
    @TableField("community_name")
    private String communityName;
    /**
     * 分区id
     */
    @TableField("zone_id")
    private Integer zoneId;
    /**
     * 分区名
     */
    @TableField("zone_name")
    private String zoneName;
    /**
     * 楼栋id
     */
    @TableField("building_id")
    private Integer buildingId;
    /**
     * 楼栋名
     */
    @TableField("building_name")
    private String buildingName;
    /**
     * 单元id
     */
    @TableField("unit_id")
    private Integer unitId;
    /**
     * 单元名
     */
    @TableField("unit_name")
    private String unitName;
    /**
     * 房间id
     */
    @TableField("room_id")
    private Integer roomId;
    /**
     * 房间名
     */
    @TableField("room_num")
    private String roomNum;
    /**
     * 联系人
     */
    @TableField("contact_person")
    private String contactPerson;
    /**
     * 联系电话
     */
    @TableField("contact_cellphone")
    private String contactCellphone;
    /**
     * 申请钥匙状态：1、申请中；2、审批通过;3、拒绝申请
     */
    private Integer status;
    /**
     * 描述
     */
    private String content;
    /**
     * 创建人用户id
     */
    @TableField("creator_user_id")
    private Integer creatorUserId;
    /**
     * 审核人
     */
    @TableField("check_person")
    private String checkPerson;
    /**
     * 审核时间
     */
    @TableField("check_time")
    private LocalDateTime checkTime;
    /**
     * 身份证号
     */
    private String IdCard;
}
