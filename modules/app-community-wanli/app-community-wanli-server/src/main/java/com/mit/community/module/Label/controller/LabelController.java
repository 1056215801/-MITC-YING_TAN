package com.mit.community.module.Label.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 标签
 * @author shuyy
 * @date 2018/12/19
 * @company mitesofor
 */
@RestController
@RequestMapping(value = "/label")
@Slf4j
@Api(tags = "标签")
public class LabelController {
    private final LabelService labelService;
    private final RedisService redisService;
    private final UserLabelService userLabelService;

    @Autowired
    public LabelController(RedisService redisService, UserLabelService userLabelService, LabelService labelService) {
        this.redisService = redisService;
        this.userLabelService = userLabelService;
        this.labelService = labelService;
    }

    /**
     * 查询标签,通过手机号和标签类型
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/19 18:47
     * @company mitesofor
     */
    @GetMapping("/listLabelSystem")
    @ApiOperation(value = "查询标签", notes = "note 1、系统标签。2、自定义标签")
    public Result listLabelSystem(String cellphone, short type) {
        User user = (User) redisService.get(RedisConstant.USER + cellphone);
        //通过用户id查询标签
        List<Label> labels = labelService.listAssociationLabelByUserId(user.getId());
        //通过标签类型进行分组
        Map<Short, List<Label>> typeMap = labels.parallelStream().collect(Collectors.groupingBy(Label::getType));
        //通过输入的类型去查找类型的标签
        List<Label> selectedSystemLabel = typeMap.get(type);
        Map<Integer, Label> map = Maps.newHashMap();
        if (selectedSystemLabel != null) {
            for (Label label : selectedSystemLabel) {
                map.put(label.getId(), label);
            }
        }
        List<Label> dataLabels = labelService.listByType(type, user.getId());
        dataLabels.forEach(item -> {
            if (null == map.get(item.getId())) {
                item.setSelected(false);
            } else {
                item.setSelected(true);
            }
        });
        return Result.success(dataLabels);
    }

    /**
     * 查询选中标签
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/19 18:47
     * @company mitesofor
     */
    @GetMapping("/listChooseLabel")
    @ApiOperation(value = "查询选中标签", notes = "cellphone 手机号")
    public Result listChooseLabel(String cellphone) {
        User user = (User) redisService.get(RedisConstant.USER + cellphone);
        List<Label> labels = labelService.listAssociationLabelByUserId(user.getId());
        return Result.success(labels);
    }

    /**
     * 增加用户自定义标签
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
        List<Label> labels = labelService.listByUserIdAndName(labelName, user.getId());
        if (labels.isEmpty()) {
            Label label = labelService.save(labelName, Constants.LABEL_TYPE_CUSTOMER, user.getId());
            return Result.success(label, "保存成功");
        } else {
            return Result.error("标签已经存在");
        }
    }

    /**
     * 删除用户自定义标签
     * @param cellphone 手机号
     * @param labelId   标签名
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/19 19:05
     * @company mitesofor
     */
    @PostMapping("/removeLabelCustomer")
    @ApiOperation(value = "删除用户自定义标签", notes = "传参：labelName 标签名")
    public Result removeLabelCustomer(String cellphone, Integer labelId) {
        User user = (User) redisService.get(RedisConstant.USER + cellphone);
        labelService.removeByLabelIdAndUserId(labelId, user.getId());
        return Result.success("删除成功");
    }

    /**
     * 关联用户自定义标签
     * @param cellphone 手机号
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/19 19:20
     * @company mitesoforxd
     */
    @PostMapping("/associationLabelCustomer")
    @ApiOperation(value = "关联用户自定义标签", notes = "数据库有这个自定义标签才能关联，所以关联用户自定一标签前，每添加一个自定义标签，" +
            "都需要调用saveLabelCustomer接口添加自定标签。而且，调用本接口流程是，先删除之前的标签，再插入新标签。<br/>" +
            "传参：customerlabelNameList 自定义标签名列表， systemLabelIdList 系统标签id列表")
    public Result associationLabel(String cellphone, String customerlabelId,
                                   String systemLabel) {
        List<Integer> customerlabelIdList;
        if (StringUtils.isBlank(customerlabelId)) {
            customerlabelIdList = Lists.newArrayListWithCapacity(0);
        } else {
            customerlabelIdList = JSON.parseArray(customerlabelId, Integer.class);
        }
        List<Integer> systemLabelList;
        if (StringUtils.isBlank("systemLabel")) {
            systemLabelList = Lists.newArrayListWithCapacity(0);
        } else {
            systemLabelList = JSON.parseArray(systemLabel, Integer.class);
        }
        User user = (User) redisService.get(RedisConstant.USER + cellphone);
        // 先删除，再关联
        userLabelService.removeByUserId(user.getId());
        List<Label> labels = labelService.listByNameListAndUserId(customerlabelIdList, user.getId());
        List<UserLabel> userLabelList = Lists.newArrayListWithCapacity(customerlabelIdList.size() + systemLabelList.size());
        labels.forEach(label -> {
            UserLabel userLabel = new UserLabel(label.getId(), user.getId(), label.getType(), null);
            userLabel.setGmtModified(LocalDateTime.now());
            userLabel.setGmtCreate(LocalDateTime.now());
            userLabelList.add(userLabel);
        });
        systemLabelList.forEach(labelId -> {
            UserLabel userLabel = new UserLabel(labelId, user.getId(), Constants.LABEL_TYPE_SYSTEM, null);
            userLabel.setGmtModified(LocalDateTime.now());
            userLabel.setGmtCreate(LocalDateTime.now());
            userLabelList.add(userLabel);
        });
        if (!userLabelList.isEmpty()) {
            userLabelService.insertBatch(userLabelList);
        }
        return Result.success("关联成功");
    }

}
