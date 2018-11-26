package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

/**
 * 房屋结构
 *
 * @author LW
 * @creatTime 2018-11-23 11:32
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    @Value("0")
    private Integer foreignPopulation;

    /**
     * 外来人口其他房屋数量
     */
    @TableField("foreign_other")
    private Integer foreignOther;

    /**
     * 外来人口自住房屋数量
     */
    @TableField("foreign_self")
    private Integer foreignSelf;

    /**
     * 外来人口租赁房屋数量
     */
    @TableField("foreign_rent")
    private Integer foreignRent;

    /**
     * 外来人口闲置房屋数量
     */
    @TableField("foreign_leisure")
    private Integer foreignLeisure;

    /**
     * 本市人口房屋数量
     */
    @TableField("inner_population")
    private Integer innerPopulation;

    /**
     * 本市人口其他房屋数量
     */
    @TableField("inner_other")
    private Integer innerOther;

    /**
     * 本市人口自住房屋数量
     */
    @TableField("inner_self")
    private Integer innerSelf;

    /**
     * 本市人口租赁房屋数量
     */
    @TableField("inner_rent")
    private Integer innerRent;

    /**
     * 本市人口闲置房屋数量
     */
    @TableField("inner_leisure")
    private Integer innerLeisure;

}
