package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * 房屋结构
 *
 * @author LW
 * @creatTime 2018-11-23 11:32
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Data
@TableName("room_type_construction")
public class RoomTypeConstruction extends BaseEntity {
    /**
     * 小区Code
     */
    @TableField("community_code")
    private String communityCode;

    /**
     * 外来人口房屋数量
     */
    @TableField("foreign_population")
    private String foreignPopulation;

    /**
     * 外来人口其他房屋数量
     */
    @TableField("foreign_other")
    private String foreignOther;

    /**
     * 外来人口自住房屋数量
     */
    @TableField("foreign_self")
    private String foreignSelf;

    /**
     * 外来人口租赁房屋数量
     */
    @TableField("foreign_rent")
    private String foreignRent;

    /**
     * 外来人口闲置房屋数量
     */
    @TableField("foreign_leisure")
    private String foreignLeisure;

    /**
     * 本市人口房屋数量
     */
    @TableField("inner_population")
    private String innerPopulation;

    /**
     * 本市人口其他房屋数量
     */
    @TableField("inner_other")
    private String innerOther;

    /**
     * 本市人口自住房屋数量
     */
    @TableField("inner_self")
    private String innerSelf;

    /**
     * 本市人口租赁房屋数量
     */
    @TableField("inner_rent")
    private String innerRent;

    /**
     * 本市人口闲置房屋数量
     */
    @TableField("inner_leisure")
    private String innerLeisure;

}
