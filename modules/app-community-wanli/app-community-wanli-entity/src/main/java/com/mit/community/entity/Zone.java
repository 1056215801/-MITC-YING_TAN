package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分区表
 * @author Mr.Deng
 * @date 2018/12/7 17:45
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("zone")
public class Zone extends BaseEntity {
    /**
     * 小区code
     */
    @TableField("community_code")
    private String communityCode;
    /**
     * 分区状态（0：停用；1：启用）
     */
    @TableField("zone_status")
    private Integer zoneStatus;
    /**
     * 分区类型表(1:住宅;2:商业;3:城中村;4:公租房;5;其他保障房)
     */
    @TableField("zone_type")
    private Integer zoneType;
    /**
     * 分区编号
     */
    @TableField("zone_code")
    private String zoneCode;
    /**
     * 分区名称（长度2~32）
     */
    @TableField("zone_name")
    private String zoneName;
    /**
     * 分区ID
     */
    @TableField("zone_id")
    private Integer zoneId;
}
