package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 反馈图片表
 * @author Mr.Deng
 * @date 2018/12/6 18:01
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("feedback_img")
public class FeedBackImg extends BaseEntity {
    /**
     * 反馈id。关联feedback表id。
     */
    @TableField("feedback_id")
    private Integer feedbackId;
    /**
     * 图片url
     */
    @TableField("img_url")
    private String imgUrl;
}
