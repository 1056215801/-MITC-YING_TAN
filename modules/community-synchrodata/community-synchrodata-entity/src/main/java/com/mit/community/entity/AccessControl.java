package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 门禁记录
 * @author Mr.Deng
 * @date 2018/11/15 11:32
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Data
@TableName("access_control")
public class AccessControl extends BaseEntity {
    /**
     * 小区编码
     */
    @TableField("community_code")
    private String communityCode;

    /**
     * 小区名
     */
    @TableField("community_name")
    private String communityName;
    /**
     * 访问时间（格式yyyy-MM-dd HH:mm:ss）
     */
    @TableField("access_time")
    private LocalDateTime accessTime;
    /**
     * 开门方式（1：刷卡开门；2：密码开门；3：APP开门；4：对讲开门；5：二维码开门；6：蓝牙开门；7：按钮开门）
     */
    @TableField("interactive_type")
    private Short interactiveType;
    /**
     * 设备名称
     */
    @TableField("device_name")
    private String deviceName;
    /**
     * 设备编码
     */
    @TableField("device_num")
    private String deviceNum;

    /**
     * 分区编码
     */
    @TableField("zone_id")
    private Integer zoneId;
    /**
     * 分区名称
     */
    @TableField("zone_name")
    private String zoneName;
    /**
     * 住户姓名
     */
    @TableField("household_name")
    private String householdName;
    /**
     * 住户手机号
     */
    @TableField("household_mobile")
    private String householdMobile;
    /**
     * 卡号（interactiveType为1:刷卡开门;才有值）
     */
    @TableField(value = "card_num")
    private String cardNum;
    /**
     * 访客影像图片URL
     */
    @TableField("access_img_url")
    private String accessImgUrl;
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
     * 住户id
     */
    @TableField("household_id")
    private Integer householdId;

    @TableField("access_control_id")
    private Integer accessControlId;

    /**
     * 房号
     */
    @TableField("room_num")
    private String roomNum;

}
