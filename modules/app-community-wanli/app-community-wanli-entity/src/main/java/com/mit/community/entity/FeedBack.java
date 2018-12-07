package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 反馈信息表
 * @author Mr.Deng
 * @date 2018/12/6 17:56
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("feedback")
public class FeedBack extends BaseEntity {
    /**
     * 标题
     */
    private String title;
    /**
     * 反馈内容
     */
    private String content;
    /**
     * 类型。关联数据字典。code为feedback_type。1、APP功能反馈。2、物业/小区问题
     */
    private Integer type;
    /**
     * 用户id。关联user表id。
     */
    @TableField("user_id")
    private Integer userId;
}
