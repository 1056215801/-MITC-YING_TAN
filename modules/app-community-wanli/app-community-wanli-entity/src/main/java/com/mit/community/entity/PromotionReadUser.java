package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 促销已读表
 * @author Mr.Deng
 * @date 2018/12/18 15:53
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("promotion_read_user")
public class PromotionReadUser extends BaseEntity {
    /**
     * 关联user表id
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 关联promotion表id
     */
    @TableField("promotion_id")
    private Integer promotionId;

}
