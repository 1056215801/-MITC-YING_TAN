package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 精选活动
 * @author Mr.Deng
 * @date 2018/12/19 20:14
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("selection_activities")
public class SelectionActivities extends BaseEntity {
    /**
     * 小区code
     */
    @TableField("community_code")
    private String communityCode;
    /**
     * 标题
     */
    private String title;
    /**
     * 活动介绍
     */
    private String introduce;
    /**
     * 外部URL
     */
    @TableField("external_url")
    private String externalUrl;
    /**
     * 有效时间
     */
    @TableField("valid_time")
    private LocalDateTime validTime;
    /**
     * 发布时间
     */
    @TableField("issue_time")
    private LocalDateTime issueTime;
    /**
     * 发布人
     */
    private String issuer;
    /**
     * 总浏览量
     */
    @TableField("read_num")
    private Integer readNum;
    /**
     * 图片
     */
    private String image;
    /**
     * 备注
     */
    private String notes;
    /**
     * 详情
     */
    @TableField(exist = false)
    private String content;

}
