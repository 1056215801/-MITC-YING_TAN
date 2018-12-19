package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 系统消息
 * @author Mr.Deng
 * @date 2018/12/19 10:27
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("sys_messages")
public class SysMessages extends BaseEntity {
    /**
     * 用户id关联user表id
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 标题
     */
    private String title;
    /**
     * 详情
     */
    private String details;
    /**
     * 通知消息添加时间
     */
    @TableField("add_time")
    private LocalDateTime addTime;
}
