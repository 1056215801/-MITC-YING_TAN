package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 访客表已读
 * @author Mr.Deng
 * @date 2019/1/2 10:20
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("visitor_read")
public class VisitorRead extends BaseEntity {
    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 访客表id
     */
    @TableField("visitor_id")
    private Integer visitorId;
}
