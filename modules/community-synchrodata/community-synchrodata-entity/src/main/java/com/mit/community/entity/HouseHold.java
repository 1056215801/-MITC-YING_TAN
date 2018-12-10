package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
@AllArgsConstructor
@NoArgsConstructor
public class HouseHold extends BaseEntity {

    /**
     * 小区Code
     */
    @TableField("community_code")
    private String communityCode;

    /**
     * 小区名
     */
    @TableField("community_name")
    private String communityName;

    /**
     * 分区ID
     */
    @TableField("zone_id")
    private Integer zoneId;
    /**
     * 分区名称
     */
    @TableField("zone_name")
    private String zoneName;

    /**
     * 楼栋ID
     */
    @TableField("building_id")
    private Integer buildingId;
    /**
     * 楼栋名称
     */
    @TableField("building_name")
    private String buildingName;
    /**
     * 单元ID
     */
    @TableField("unit_id")
    private Integer unitId;
    /**
     * 单元名称
     */
    @TableField("unit_name")
    private String unitName;
    
    /**
     * 房间id
     */
    @TableField("room_id")
    private Integer roomId;
    
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
    private Integer householdStatus;
    /**
     * 值转成二进制授权状态（未授权：0;卡：1;roomNumapp：10;人脸：100）；例:人脸和卡授权：101
     */
    @TableField("authorize_status")
    private Integer authorizeStatus;
    /**
     * 业主性别(0：男；1：女)
     */
    private Integer gender;
    /**
     * 居住期限
     */
    @TableField("residence_time")
    private LocalDateTime residenceTime;
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

    /**
     * 身份证号码
     */
    @TableField("credential_num")
    private String credentialNum;

    /**
     * 省份
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区县
     */
    private String region;

    /**
     * 出生日期
     */
    private LocalDate birthday;

    /**
     * 身份类型：1、群众、2、境外人员、3、孤寡老人、4、信教人员、5、留守儿童、6、上方人员、99、其他
     */
    @TableField("identity_type")
    private Short identityType;

    @TableField(exist = false)
    private List<AuthorizeHouseholdDeviceGroup> authorizeHouseholdDeviceGroups;

    @TableField(exist = false)
    private List<AuthorizeAppHouseholdDeviceGroup> authorizeAppHouseholdDeviceGroups;

    /**群众*/
    @TableField(exist = false)
    public static final Short NORMAL = 1;

    /**境外人员*/
    @TableField(exist = false)
    public static final Short OVERSEAS = 2;

    /**孤寡老人*/
    @TableField(exist = false)
    public static final Short LONELY = 3;

    /**信教人员*/
    @TableField(exist = false)
    public static final Short RELIGION = 4;

    /**留守儿童*/
    @TableField(exist = false)
    public static final Short STAY_AT_HOME = 5;

    /**上访人员*/
    @TableField(exist = false)
    public static final Short VISITOR = 6;

    /**其他*/
    @TableField(exist = false)
    public static final Short OTHER = 7;







}
