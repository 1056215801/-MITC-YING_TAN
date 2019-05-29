package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 促销表
 *
 * @author Mr.Deng
 * @date 2018/12/18 15:41
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("promotion")
public class Promotion extends BaseEntity {
    /**
     * 促销类型，关联数据字典code   promotion_type
     */
    @TableField("promotion_type")
    private String promotionType;
    /**
     * 标题
     */
    private String title;
    /**
     * 封面图片地址
     */
    @TableField("img_url")
    private String imgUrl;
    /**
     * 发布人
     */
    private String issuer;
    /**
     * 发布人联系电话
     */
    @TableField("issuer_phone")
    private String issuerPhone;
    /**
     * 促销地址
     */
    @TableField("promotion_address")
    private String promotionAddress;
    /**
     * 发布时间
     */
    @TableField("issue_time")
    private LocalDateTime issueTime;
    /**
     * 折扣
     */
    private Float discount;
    /**
     * 活动内容
     */
    @TableField("activity_content")
    private String activityContent;
    /**
     * 活动开始时间
     */
    @TableField("start_time")
    private LocalDateTime startTime;
    /**
     * 活动结束时间
     */
    @TableField("end_time")
    private LocalDateTime endTime;
    /**
     * 小区code
     */
    @TableField("community_code")
    private String communityCode;
    /**
     * 浏览量
     */
    @TableField("read_num")
    private Integer readNum;
    /**
     * 促销状态
     */
    @TableField(exist = false)
    private String promotionStatus;
    /**
     * 已读状态
     */
    @TableField(exist = false)
    private Boolean readStatus;
    /**
     * 详情
     */
    @TableField(exist = false)
    private String content;

}
