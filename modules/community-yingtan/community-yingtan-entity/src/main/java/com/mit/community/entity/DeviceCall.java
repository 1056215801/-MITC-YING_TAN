package com.mit.community.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

/**
 * 呼叫记录
 *
 * @author Mr.Deng
 * @date 2018/11/15 12:10
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Data
@TableName("devicecall")
public class DeviceCall extends Model<DeviceCall> {
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 小区code
     */
    @TableField("community_code")
    private String communityCode;
    /**
     * 分区id
     */
    @TableField("zone_id")
    private Integer zoneId;
    /**
     * 楼栋id
     */
    @TableField("building_id")
    private Integer buildingId;
    /**
     * 单元id
     */
    @TableField("unit_id")
    private Integer unitId;
    /**
     * 呼叫时间(格式yyyy-MM-dd HH:mm:ss)
     */
    @TableField("call_time")
    private String callTime;
    /**
     * 设备编码
     */
    @TableField("device_num")
    private String deviceNum;
    /**
     * 房间号(四位数字，例：0001)
     */
    @TableField("room_num")
    private String roomNum;
    /**
     * 呼叫方式：0:未接通;1:APP通话;2:电话通话;3:局域网室内机;4:云室内机;5:管理中心机;6:APP监视;7:云室内机监视;8:局域网室内机监视
     */
    @TableField("call_type")
    private String callType;
    /**
     * 接听人
     */
    private String receiver;
    /**
     * 开门类型："0：其他开门；1：刷卡开门；2：密码开门；3：APP开门；4：分机开门；5：二维码开门； 6：蓝牙开门；7：按钮开门；8：手机开门;9：人脸识别；10:固定密码；11：http开门；
     */
    @TableField("open_door_type")
    private String openDoorType;
    /**
     * 呼叫时长(单位：秒)
     */
    @TableField("call_duration")
    private String callDuration;
    /**
     * 呼叫图片url
     */
    @TableField("call_img_url")
    private String callImgUrl;
    /**
     * 设备名称
     */
    @TableField("device_name")
    private String deviceName;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
