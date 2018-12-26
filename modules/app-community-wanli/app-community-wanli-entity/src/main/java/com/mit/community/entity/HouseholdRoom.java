package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 住户房屋关联表
 *
 * @author shuyy
 * @date 2018/12/11
 * @company mitesofor
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("household_room")
public class HouseholdRoom extends BaseEntity {
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
     * 与户主关系（1：本人；2：配偶；3：父母；4：子女；5：亲属；6：非亲属；7：租赁；8：其他；9：保姆；10：护理人员）
     */
    @TableField("household_type")
    private Short householdType;

    /**住户*/
    @TableField("household_id")
    private Integer householdId;
    /**小区信息*/
    @TableField(exist = false)
    private ClusterCommunity clusterCommunity;
}
