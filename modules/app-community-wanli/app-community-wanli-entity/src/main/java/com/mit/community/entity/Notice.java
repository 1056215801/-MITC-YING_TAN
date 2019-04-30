package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 通知通告表
 * @author Mr.Deng
 * @date 2018/12/3 14:35
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@AllArgsConstructor
@NoArgsConstructor
@TableName("notice")
@Data
public class Notice extends BaseEntity {

    /**
     * 小区
     */
    @TableField("community_code")
    private String communityCode;

    /**
     * 标题
     */
    private String title;
    /**
     * 通知类型。关联数据字典code为notice_type。：1、政策性通知。2、人口普查。3、入学通知。4、物业公告。
     */
    private String code;
    /**
     * 发布渠道：1、app公告；2、轮播图；3、首页广告位；4、预留功能
     */
    @TableField("publish_way")
    private String publishWay;

    /**
     * 发布时间
     */
    @TableField("release_time")
    private LocalDateTime releaseTime;
    /**
     * 过期时间
     */
    @TableField("validate_time")
    private LocalDateTime validateTime;
    /**
     * 简介
     */
    private String synopsis;
    /**
     * 发布人
     */
    private String publisher;
    /**
     * 创建人
     */
    private Integer creator;
    /**
     * 浏览量
     */
    @TableField(exist = false)
    private Integer views;
    /**
     * 详情
     */
    @TableField(exist = false)
    private String content;
    /**
     * 封面图片
     */
    @TableField("cover_picture_url")
    private String coverPictureUrl;
    /**
     * 状态：1、以启用；2、已停用；3、已过期
     */
    private Integer status;
    /**
     * 修改人
     */
    private Integer modifier;
    /**
     * 用户是否已读
     */
    @TableField(exist = false)
    private Boolean readStatus;
}
