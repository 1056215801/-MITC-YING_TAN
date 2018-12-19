package com.mit.community.module.Label.controller;

import com.google.common.collect.Lists;
import com.mit.community.constants.Constants;
import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.Label;
import com.mit.community.entity.User;
import com.mit.community.entity.UserLabel;
import com.mit.community.service.LabelService;
import com.mit.community.service.RedisService;
import com.mit.community.service.UserLabelService;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 标签
 *
 * @author shuyy
 * @date 2018/12/19
 * @company mitesofor
 */
@RestController
@RequestMapping(value = "/label")
@Slf4j
@Api(tags = "标签")
public class LabelController {
    @Autowired
    private LabelService labelService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserLabelService userLabelService;

    /**
     * @return com.mit.community.util.Result
     * @throws
     * @author shuyy
     * @date 2018/12/19 18:47
     * @company mitesofor
    */
    @GetMapping("/listLabelSystem")
    @ApiOperation(value = "查询系统预设标签")
    public Result listLabelSystem() {
        List<Label> labels = labelService.listByType(Constants.LABEL_TYPE_SYSTEM);
        return Result.success(labels);
    }

    /**
     *
     * @param cellphone 手机号
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/19 18:52
     * @company mitesofor
    */
    @GetMapping("/listLabelByUserId")
    @ApiOperation(value = "查询用户自定义标签",  notes = "传参：cellphone 手机号")
    public Result listLabelByUserId(String cellphone) {
        User user = (User) redisService.get(RedisConstant.USER + cellphone);
        List<Label> labels = labelService.listByUserId(user.getId());
        return Result.success(labels);
    }

    /**
     * @param cellphone 手机号
     * @param labelName 标签名
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/19 19:00
     * @company mitesofor
    */
    @PostMapping("/saveLabelCustomer")
    @ApiOperation(value = "增加用户自定义标签", notes = "传参：labelName 标签名")
    public Result saveLabelCustomer(String cellphone, String labelName) {
        User user = (User) redisService.get(RedisConstant.USER + cellphone);
        Label label = labelService.save(labelName, Constants.LABEL_TYPE_CUSTOMER, user.getId());
        return Result.success(label, "保存成功");
    }

    /**
     * @param cellphone 手机号
     * @param labelName 标签名
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/19 19:05
     * @company mitesofor
    */
    @DeleteMapping("/removeLabelCustomer")
    @ApiOperation(value = "删除用户自定义标签", notes = "传参：labelName 标签名")
    public Result removeLabelCustomer(String cellphone, String labelName) {
        User user = (User) redisService.get(RedisConstant.USER + cellphone);
        labelService.removeByNameAndUserId(user.getId(), labelName);
        return Result.success("删除成功");
    }

    /**
     * @param cellphone 手机号
     * @param customerlabelNameList 自定义标签名列表
     * @param systemLabelIdList 系统标签id列表
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/19 19:20
     * @company mitesofor
    */
    @PostMapping("/associationLabelCustomer")
    @ApiOperation(value = "关联用户自定义标签", notes = "数据库有这个自定义标签才能关联，所以关联用户自定一标签前，每添加一个自定义标签，" +
            "都需要调用saveLabelCustomer接口添加自定标签。而且，调用本接口流程是，先删除之前的标签，再插入新标签。<br/>" +
            "传参：customerlabelNameList 自定义标签名列表， systemLabelIdList 系统标签id列表")
    public Result associationLabel(String cellphone, @RequestParam("customerlabelNameList") List<String> customerlabelNameList,
                                           @RequestParam("SystemLabelIdList") List<Integer> systemLabelIdList) {
        User user = (User) redisService.get(RedisConstant.USER + cellphone);
        // 先删除，再关联
        userLabelService.removeByUserId(user.getId());
        List<Label> labels = labelService.listByNameListAndUserId(customerlabelNameList, user.getId());
        List<UserLabel> userLabelList = Lists.newArrayListWithCapacity(customerlabelNameList.size() + systemLabelIdList.size());
        labels.forEach(label -> {
            UserLabel userLabel = new UserLabel(label.getId(), user.getId(),label.getType(), null);
            userLabel.setGmtModified(LocalDateTime.now());
            userLabel.setGmtCreate(LocalDateTime.now());
            userLabelList.add(userLabel);
        });
        systemLabelIdList.forEach(labelId -> {
            UserLabel userLabel = new UserLabel(labelId, user.getId(), Constants.LABEL_TYPE_SYSTEM, null);
            userLabel.setGmtModified(LocalDateTime.now());
            userLabel.setGmtCreate(LocalDateTime.now());
            userLabelList.add(userLabel);
        });
        userLabelService.insertBatch(userLabelList);
        return Result.success("关联成功");
    }




    /**
     *
     * @param cellphone 手机号
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/19 19:47
     * @company mitesofor
    */
    @GetMapping("/listAssociationLabel")
    @ApiOperation(value = "查询用户关联标签", notes = "返回参数：label的属性type：1、系统标签。2、用户自定义标签")
    public Result listAssociationLabel(String cellphone) {
        User user = (User) redisService.get(RedisConstant.USER + cellphone);
        List<Label> labels = labelService.listAssociationLabelByUserId(user.getId());
        return Result.success(labels);
    }

}
