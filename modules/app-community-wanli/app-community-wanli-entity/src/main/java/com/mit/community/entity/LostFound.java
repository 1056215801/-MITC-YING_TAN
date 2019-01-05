package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 失物招领
 * @author Mr.Deng
 * @date 2018/12/17 20:17
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("lost_found")
public class LostFound extends BaseEntity {
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
     * 拾取地址
     */
    @TableField("pick_address")
    private String pickAddress;
    /**
     * 捡到时间
     */
    @TableField("pick_time")
    private LocalDateTime pickTime;
    /**
     * 领取人
     */
    private String receiver;
    /**
     * 领取人联系电话
     */
    @TableField("receive_phone")
    private String receivePhone;
    /**
     * 领取地点
     */
    @TableField("receiver_address")
    private String receiverAddress;
    /**
     * 领取时间
     */
    @TableField("receiver_time")
    private LocalDateTime receiverTime;
    /**
     * 领取状态（1、未领取；2、已领取）
     */
    @TableField("receiver_status")
    private Integer receiverStatus;
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
