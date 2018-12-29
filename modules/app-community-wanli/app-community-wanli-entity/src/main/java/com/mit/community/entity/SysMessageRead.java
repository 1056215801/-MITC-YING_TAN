package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统消息已读
 *
 * @author shuyy
 * @date 2018/12/29
 * @company mitesofor
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("sys_message_read")
public class SysMessageRead extends BaseEntity{

    @TableField("user_id")
    private Integer userId;

    @TableField("sys_message_id")
    private Integer sysMessageId;


}
