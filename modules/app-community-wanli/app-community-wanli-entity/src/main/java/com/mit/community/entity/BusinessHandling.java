package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 业务办理表
 * @author Mr.Deng
 * @date 2018/12/5 13:43
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("business_handling")
public class BusinessHandling extends BaseEntity {
    /**
     * 工单号
     */
    private String number;
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
     * 描述
     */
    private String content;
    /**
     * 状态。关联书字典，code为，report_thing_repair_type。1、报事成功。2、已受理。3、处理中.、4、待评价。5、已评价。
     */
    private Integer status;

    /**
     * 业务类型，关联字典表，code为business_handling_type。1、入住证明。2、装修完工申请。3、大物件搬出申报。4、装修许可证。
     * 5、装修出入证。6、钥匙托管。7、业主卡。99、其他。
     */
    private Integer type;
    /**
     * 创建人用户id,关联user表
     */
    @TableField("creator_user_id")
    private Integer creatorUserId;
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
}
