package com.mit.community.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

/**
 * 住户表
 *
 * @author Mr.Deng
 * @date 2018/11/14 19:00
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Data
@TableName("household")
public class HouseHold extends Model<HouseHold> {

    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 小区Code
     */
    @TableField("community_code")
    private String communityCode;
    /**
     * 分区ID
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
     * 房间号
     */
    @TableField("room_num")
    private String roomNum;
    /**
     * 住户ID
     */
    @TableField("household_id")
    private Integer householdId;
    /**
     * 业主名称
     */
    @TableField("household_name")
    private String householdName;
    /**
     * 与户主关系（1：本人；2：配偶；3：父母；4：子女；5：亲属；6：非亲属；7：租赁；8：其他；9：保姆；10：护理人员）
     */
    @TableField("household_type")
    private Integer householdType;
    /**
     * 业主状态（0：注销；1：启用）
     */
    @TableField("household_status")
    private String householdStatus;
    /**
     * 值转成二进制授权状态（未授权：0;卡：1;app：10;人脸：100）；例:人脸和卡授权：101
     */
    @TableField("authorize_status")
    private String authorizeStatus;
    /**
     * 楼栋名称
     */
    @TableField("building_name")
    private String buildingName;
    /**
     * 分区名称
     */
    @TableField("zone_name")
    private String zoneName;
    /**
     * 门禁卡授权设备组id “，” 分隔
     */
    @TableField("door_devicegroup_ids")
    private String doorDeviceGroupIds;
    /**
     * 单元名称
     */
    @TableField("unit_name")
    private String unitName;
    /**
     * 业主性别(0：男；1：女)
     */
    private String gender;
    /**
     * 居住期限
     */
    @TableField("residence_time")
    private String residenceTime;
    /**
     * app授权设备组id “，” 分隔
     */
    @TableField("app_devicegroup_ids")
    private String appDeviceGroupIds;
    /**
     * 业主手机号
     */
    private String mobile;
    /**
     * SIP账号
     */
    @TableField("sip_account")
    private String sipAccount;
    /**
     * SIP密码
     */
    @TableField("sip_password")
    private String sipPassword;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
