package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 报事报修表
 * @author Mr.Deng
 * @date 2018/12/3 19:18
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("report_things_repair")
public class ReportThingsRepair extends BaseEntity {
    /**
     * 工单号
     */
    private String number;
    /**
     * 小区code。关联cluster_community表community_code字段。
     */
    @TableField("community_code")
    private String communityCode;
    /**
     * 小区名称
     */
    @TableField("community_name")
    private String communityName;
    /**
     * 分区id。关联zone表zone_id字段。
     */
    @TableField("zone_id")
    private Integer zoneId;
    /**
     * 分区名
     */
    @TableField("zone_name")
    private String zoneName;
    /**
     * 楼栋id。关联building表building_id字段
     */
    @TableField("building_id")
    private Integer buildingId;
    /**
     * 楼栋名
     */
    @TableField("building_name")
    private String buildingName;
    /**
     * 单元id。关联unit表unit_id字段。
     */
    @TableField("unit_id")
    private Integer unitId;
    /**
     * 单元名
     */
    @TableField("unit_name")
    private String unitName;
    /**
     * 房间id。关联room表room_id字段。
     */
    @TableField("room_id")
    private Integer roomId;
    /**
     * 房间编号
     */
    @TableField("room_num")
    private String roomNum;
    /**
     * 住户id。关联household表household_id字段。
     */
    @TableField("household_id")
    private Integer householdId;
    /**
     * 内容
     */
    private String content;
    /**
     * 状态。关联书字典，code为，report_thing_repair_type。1、报事成功。2、已受理。3、处理中.、4、待评价。5、已评价。
     */
    private String status;
    /**
     * 报事人
     */
    @TableField("report_user")
    private String reportUser;
    /**
     * 联系人电话
     */
    private String cellphone;
    /**
     * 预约时间
     */
    @TableField("appointment_time")
    private LocalDateTime appointmentTime;
    /**
     * 响应速度评价
     */
    @TableField("evaluate_response_speed")
    private Integer evaluateResponseSpeed;
    /**
     * 响应态度评价
     */
    @TableField("evaluate_response_attitude")
    private Integer evaluateResponseAttitude;
    /**
     * 总体评价
     */
    @TableField("evaluate_total")
    private Integer evaluateTotal;
    /**
     * 服务专业度评价
     */
    @TableField("evaluate_service_profession")
    private Integer evaluateServiceProfession;
    /**
     * 评价内容
     */
    @TableField("evaluate_content")
    private String evaluateContent;
    /**
     * 维修类型。关联字典code maintain_type 维修类型：1、水，2、电，3、可燃气，4、锁，5、其他
     */
    @TableField("maintain_type")
    private String maintainType;
    /**
     * 创建用户id
     */
    @TableField("creator_user_id")
    private Integer creatorUserId;
}
