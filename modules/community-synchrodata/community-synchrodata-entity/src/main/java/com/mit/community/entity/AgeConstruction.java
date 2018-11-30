package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.omg.CORBA.INTERNAL;
import org.omg.PortableInterceptor.INACTIVE;

/**
 * 年龄结构
 *
 * @author shuyy
 * @date 2018/11/22
 * @company mitesofor
 */
@NoArgsConstructor
@AllArgsConstructor
@TableName("age_construction")
@Data
public class AgeConstruction extends BaseEntity{

    /**小区code*/
    @TableField("community_code")
    private String communityCode;

    /**小孩数量*/
    @TableField("child_num")
    private Integer childNum;

    /**少年数量*/
    @TableField("young_num")
    private Integer youngNum;

    /**青年数量*/
    @TableField("youth_num")
    private Integer youthNum;

    /**中年数量*/
    @TableField("middle_num")
    private Integer middleNum;

    /**老年数量*/
    @TableField("old_num")
    private Integer oldNum;

}
