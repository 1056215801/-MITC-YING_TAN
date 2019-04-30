package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 通知通告表
 * xiong
 */
@AllArgsConstructor//lombok生成全参构造函数
@NoArgsConstructor//生成无参构造函数
@TableName("web_notice")
@Data
public class WebNotice extends BaseEntity{
    /**
     * 小区名称
     */
    @TableField("community_code")
    private String communityCode;
    /**
     * 标题
     */
    private String title;
    /**
     * 公告类型:1、物业公告；2、天气提示；3、政府文件；4、人口普查；5、入学通知
     * 6、活动公告；7、新闻通知
     */
    private String type;
    /**
     * 发布渠道：1、app公告；2、轮播图；3、首页广告位；4、预留功能
     */
    @TableField("publish_way")
    private String publishWay;
    /**
     * 封面图片
     */
    @TableField("cover_picture_url")
    private String coverPictureUrl;
    /**
     * 公告摘要
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
     * 发布时间
     */
    @TableField("release_time")
    private LocalDateTime releaseTime;
    /**
     * 开始时间
     */
    @TableField("begin_time")
    private LocalDateTime beginTime;
    /**
     * 到期时间
     */
    @TableField("validate_time")
    private LocalDateTime validateTime;
    /**
     * 状态：1、以启用；2、已停用；3、已过期
     */
    private Integer status;
    /**
     * 总浏览量
     */
    @TableField("browse_count")
    private Integer browseCount;


}