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
     * 标题
     */
    private String title;
    /**
     * 类型。关联数据字典code为notice_type。
     * 一、通知公告：1、政策性通知。2、人口普查。3、入学通知。4、物业公告。
     * 二、系统消息。
     * 三、生活服务：2.快递待取消息、3.失物招领、4.周边促销、5.老人体检
     */
    private String type;
    /**
     * 发布时间
     */
    @TableField("release_time")
    private LocalDateTime releaseTime;
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
    private String creator;
    /**
     * 修改人
     */
    private String modifier;

}
