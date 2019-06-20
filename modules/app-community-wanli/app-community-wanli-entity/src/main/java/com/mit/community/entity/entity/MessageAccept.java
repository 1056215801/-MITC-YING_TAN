package com.mit.community.entity.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.mit.community.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("message_accept")
public class MessageAccept extends BaseEntity{
    private Integer messageId;
    private Integer userId;
    private int status;
}
