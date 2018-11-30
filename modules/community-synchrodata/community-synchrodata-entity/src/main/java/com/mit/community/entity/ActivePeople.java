package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 活跃人数
 *
 * @author shuyy
 * @date 2018/11/22
 * @company mitesofor
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("active_people")
public class ActivePeople extends BaseEntity{

    /**小区code*/
    @TableField("community_code")
    private String communityCode;

    /**活跃人数*/
    @TableField("active_people_num")
    private Long activePeopleNum;

}
