package com.mit.community.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

/**
 * 门禁记录
 *
 * @author Mr.Deng
 * @date 2018/11/15 11:32
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Data
@TableName("accesscontrol")
public class AccessControl extends Model<AccessControl> {
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 小区编码
     */
    @TableField("community_code")
    private String communityCode;
    /**
     * 访问时间（格式yyyy-MM-dd HH:mm:ss）
     */
    @TableField("access_time")
    private String accessTime;
    /**
     * 开门方式（1：刷卡开门；2：密码开门；3：APP开门；4：对讲开门；5：二维码开门；6：蓝牙开门；7：按钮开门）
     */
    @TableField("interactive_type")
    private String interactiveType;
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
     * 分区名称（interactiveType为:刷卡开门;3:APP开门;4:对讲开门;才有值）
     */
    @TableField("zone_name")
    private String zoneName;
    /**
     * 住户姓名（interactiveType为1:刷卡开门;3:APP开门;4:对讲开门;才有值）
     */
    @TableField("household_name")
    private String householdName;
    /**
     * 住户手机号（interactiveType为1:刷卡开门;3:APP开门;4:对讲开门;才有值）
     */
    @TableField("household_mobile")
    private String householdMobile;
    /**
     * 卡号（interactiveType为1:刷卡开门;才有值）
     */
    @TableField("card_num")
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
     * 唯一记录id
     */
    @TableField("accesscontrol_id")
    private String accessControlId;
    /**
     * 住户id
     */
    @TableField("household_id")
    private String householdId;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
