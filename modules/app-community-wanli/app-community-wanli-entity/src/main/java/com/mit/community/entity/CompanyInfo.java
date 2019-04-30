package com.mit.community.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("company_info")
public class CompanyInfo extends BaseEntity{
    /**
     * 企业名称
     */
    @TableField("company_name")
    private String companyName;
    /**
     * 企业简介
     */
    @TableField("company_synopsis")
    private String companySynopsis;
    /**
     * 企业邮箱
     */
    private String email;
    /**
     * 企业官网
     */
    @TableField("official_website")
    private String officialWebsite;
    /**
     * 微信公众号
     */
    @TableField("wechat_subscription")
    private String WeChatSubscription;
    /**
     * 公众号二维码
     */
    @TableField("subscription_QRCode_url")
    private String SubscriptionQRCodeUrl;
    /**
     * 微信订阅号
     */
    @TableField("wechat_subscribe_num")
    private String weChatSubscribeNum;
    /**
     * 订阅号二维码
     */
    @TableField("subscribe_num_url")
    private String SubscribeNumUrl;
    /**
     * 客服电话
     */
    @TableField("service_telephone")
    private String serviceTelephone;
    /**
     * 状态（1已启用，2已停用）
     */
    private Integer status;
}
