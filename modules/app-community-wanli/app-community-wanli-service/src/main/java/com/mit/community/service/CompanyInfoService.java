package com.mit.community.service;

import com.mit.community.entity.CompanyInfo;
import com.mit.community.mapper.CompanyInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 企业信息
 * @author  xiong
 * @date 2019/04/18
 * @company mitesofor
 */
@Service
public class CompanyInfoService {
    @Autowired
    private CompanyInfoMapper companyInfoMapper;

    /**
     * 保存企业信息
     * @param companyName 企业名称
     * @param companySynopsis 企业简介
     * @param email 企业邮箱
     * @param officialWebsite 官网
     * @param WeChatSubscription 微信公众哈
     * @param subscriptionQRCodeUrl 公众号图片url
     * @param weChatSubscribeNum 微信订阅号
     * @param SubscribeNumQRCodeUrl 订阅号二维码url
     * @param status 状态
     * @param serviceTelephone 客服电话
     * @company mitesofor
     */
    public void save(String companyName, String companySynopsis, String email, String officialWebsite, String WeChatSubscription, String subscriptionQRCodeUrl, String weChatSubscribeNum, String SubscribeNumQRCodeUrl, Integer status, String serviceTelephone){
        CompanyInfo companyInfo = new CompanyInfo(companyName, companySynopsis, email, officialWebsite, WeChatSubscription, subscriptionQRCodeUrl, weChatSubscribeNum, SubscribeNumQRCodeUrl, serviceTelephone, status);
        companyInfo.setGmtCreate(LocalDateTime.now());
        companyInfo.setGmtModified(LocalDateTime.now());
        companyInfoMapper.insert(companyInfo);
    }
    /**
     * 更新企业信息
     * @company mitesofor
     */
    public void update(Integer id,  String companyName, String companySynopsis, String email, String officialWebsite,
          String WeChatSubscription, String subscriptionQRCodeUrl , String weChatSubscribeNum, String serviceTelephone,
          String subscribeNumQRCodeUrl, Integer status){
        CompanyInfo companyInfo = new CompanyInfo();
        companyInfo.setId(id);
        if (StringUtils.isNotBlank(companyName)) {
            companyInfo.setCompanyName(companyName);
        }
        if (StringUtils.isNotBlank(companySynopsis)) {
            companyInfo.setCompanySynopsis(companySynopsis);
        }
        if (StringUtils.isNotBlank(email)) {
            companyInfo.setEmail(email);
        }
        if (StringUtils.isNotBlank(officialWebsite)) {
            companyInfo.setOfficialWebsite(officialWebsite);
        }
        if (StringUtils.isNotBlank(WeChatSubscription)) {
            companyInfo.setWeChatSubscription(WeChatSubscription);
        }
        if (StringUtils.isNotBlank(subscriptionQRCodeUrl)) {
            companyInfo.setSubscriptionQRCodeUrl(subscriptionQRCodeUrl);
        }
        if (StringUtils.isNotBlank(weChatSubscribeNum)) {
            companyInfo.setWeChatSubscribeNum(weChatSubscribeNum);
        }
        if (StringUtils.isNotBlank(subscribeNumQRCodeUrl)) {
            companyInfo.setSubscribeNumUrl(subscribeNumQRCodeUrl);
        }
        if (StringUtils.isNotBlank(serviceTelephone)) {
            companyInfo.setServiceTelephone(serviceTelephone);
        }
        if (status != null) {
            companyInfo.setStatus(status);
        }
        companyInfo.setGmtModified(LocalDateTime.now());
        companyInfoMapper.updateById(companyInfo);
    }

    /**
     * 获取企业信息列表
     * @company mitesofor
     * @return
     */
    public List<CompanyInfo> list() { return companyInfoMapper.selectList(null); }

    /**
     * 删除企业信息
     * @company mitesofor
     * @param id 记录id
     */
    public void remove(Integer id){
        companyInfoMapper.deleteById(id);
    }
}
