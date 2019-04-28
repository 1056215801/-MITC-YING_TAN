package com.mit.community.module.system.controller;

import com.mit.community.entity.CompanyInfo;
import com.mit.community.service.CompanyInfoService;
import com.mit.community.util.Result;
import com.mit.community.util.UploadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 企业信息
 */
@RestController
@RequestMapping(value = "/companyInfo")
@Slf4j
@Api(tags = "企业信息")
public class CompanyInfoController {
    @Autowired
    private CompanyInfoService companyInfoService;

    @PostMapping("/saveCompanyInfo")
    @ApiOperation(value = "添加企业信息", notes = "输入参数：companyName 企业名称；companySynopsis 企业简介；email 企业邮箱；officialWebsite 企业官网；"
            + "WeChatSubscription 微信公众号；subscriptionQRCode 公众号二维码；weChatSubscribeNum 订阅号；subscribeNumQRCode 订阅号二维码；status 状态（1已启用，2已停用）")
    public Result saveCompanyInfo(String companyName, String companySynopsis, String email, String officialWebsite,
                                  String WeChatSubscription, MultipartFile subscriptionQRCode, String weChatSubscribeNum, String serviceTelephone,
                                  MultipartFile subscribeNumQRCode, Integer status) throws Exception {
        if (StringUtils.isNotBlank(companyName) && StringUtils.isNotBlank(companySynopsis) && StringUtils.isNotBlank(email) && StringUtils.isNotBlank(officialWebsite)
                && StringUtils.isNotBlank(WeChatSubscription) && StringUtils.isNotBlank(weChatSubscribeNum) && status != null && StringUtils.isNotBlank(serviceTelephone)) {
            String subscriptionQRCodeUrl = UploadUtil.upload(subscriptionQRCode);
            String SubscribeNumQRCodeUrl = UploadUtil.upload(subscribeNumQRCode);
            companyInfoService.save(companyName, companySynopsis, email, officialWebsite, WeChatSubscription, subscriptionQRCodeUrl, weChatSubscribeNum, SubscribeNumQRCodeUrl, status, serviceTelephone);
            return  Result.success("添加成功");
        }
        return Result.error("参数不能为空");

    }

    @PutMapping("/update")
    @ApiOperation(value = "添加企业信息", notes = "输入参数：companyName 企业名称；companySynopsis 企业简介；email 企业邮箱；officialWebsite 企业官网；"
            + "WeChatSubscription 微信公众号；subscriptionQRCode 公众号二维码；weChatSubscribeNum 订阅号；subscribeNumQRCode 订阅号二维码；status 状态（1已启用，2已停用）")
    public Result update(Integer id, String companyName, String companySynopsis, String email, String officialWebsite,
                         String WeChatSubscription, MultipartFile subscriptionQRCode, String weChatSubscribeNum, String serviceTelephone,
                         MultipartFile subscribeNumQRCode, Integer status) throws Exception{
        if (id != null) {
            String subscriptionQRCodeUrl = null;
            String SubscribeNumQRCodeUrl = null;
            if (subscriptionQRCode != null) {
                subscriptionQRCodeUrl = UploadUtil.upload(subscriptionQRCode);
            }
            if (subscribeNumQRCode != null) {
                SubscribeNumQRCodeUrl = UploadUtil.upload(subscribeNumQRCode);
            }
            companyInfoService.update(id,  companyName,  companySynopsis,  email,  officialWebsite,
                     WeChatSubscription, subscriptionQRCodeUrl ,  weChatSubscribeNum,  serviceTelephone,
                    SubscribeNumQRCodeUrl,  status);
        }
        return Result.error("id不能为空");
    }

    @GetMapping("/list")
    @ApiOperation("获取企业信息列表")
    public Result list() {
        List<CompanyInfo> list = companyInfoService.list();
        return Result.success(list);
    }

    @DeleteMapping("/remove")
    @ApiOperation(value = "删除企业信息", notes = "传参：id")
    public Result remove(Integer id) {
        companyInfoService.remove(id);
        return Result.success("删除成功");
    }
}
