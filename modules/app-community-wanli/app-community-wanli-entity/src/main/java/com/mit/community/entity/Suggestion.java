package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xiong
 * @date 2019/4/16
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("suggestion")
public class Suggestion extends BaseEntity{
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
     * 创建人(userid)
     */
    @TableField("creator_user_id")
    private Integer creator;
    /**
     * 投诉类型，关联字典表，code为suggestion_type。（1投诉建议，2公共设施，3邻里纠纷，4噪音扰民，5停车秩序
     * 6服务态度，7业务流程，8工程维修，9售后服务，10其他）
     */
    private String type;
    /**
     * 投诉内容描述
     */
    private String content;
    /**
     * 图片地址列表
     */
    @TableField(exist = false)
    private List<String> images;
    /**
     * 投诉人
     */
    @TableField("contact_person")
    private String contactPerson;
    /**
     * 投诉人联系电话
     */
    @TableField("contact_cellphone")
    private String contactCellphone;
    /**
     * 处理状态，关联字典表，code为suggestion_type
     */
    private String status;

}
