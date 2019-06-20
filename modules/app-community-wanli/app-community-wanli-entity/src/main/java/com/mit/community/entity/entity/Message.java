package com.mit.community.entity.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.mit.community.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("webmessage")
public class Message extends BaseEntity {
    private String title;
    private String outline;
    private String content;
    private Integer userId;
    @TableField(exist = false)
    private String userName;
    @TableField(exist = false)
    private int count;
}
