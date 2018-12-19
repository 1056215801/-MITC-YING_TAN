package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 促销详情表
 * @author Mr.Deng
 * @date 2018/12/18 15:51
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("promotion_content")
public class PromotionContent extends BaseEntity {
    /**
     * 关联promotion表id
     */
    @TableField("promotion_id")
    private Integer promotionId;
    /**
     * 详情内容
     */
    private String content;
}
