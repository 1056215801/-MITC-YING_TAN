package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 人流高峰
 *
 * @author shuyy
 * @date 2018/11/23
 * @company mitesofor
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("population_rush")
public class PopulationRush extends BaseEntity {

    /**
     * 小区code
     */
    @TableField("community_code")
    private String communityCode;

    /**
     * 星期一人数
     */
    private Integer monday;

    /**
     * 星期二人数
     */
    private Integer tuesday;
    /**
     * 星期三人数
     */
    private Integer wednesday;
    /**
     * 星期四人数
     */
    private Integer thursday;
    /**
     * 星期五人数
     */
    private Integer friday;
    /**
     * 星期六人数
     */
    private Integer saturday;
    /**
     * 星期天人数
     */
    private Integer sunday;
}
