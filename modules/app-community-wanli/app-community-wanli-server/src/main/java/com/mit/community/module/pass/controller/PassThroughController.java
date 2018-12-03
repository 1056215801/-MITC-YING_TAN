package com.mit.community.module.pass.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.mit.community.entity.Region;
import com.mit.community.service.ApplyKeyService;
import com.mit.community.service.NoticeService;
import com.mit.community.service.RegionService;
import com.mit.community.util.HttpUtil;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 住户-通行模块
 * @author Mr.Deng
 * @date 2018/12/3 14:27
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@RestController
@RequestMapping(value = "/passThrough")
@Slf4j
@Api(tags = "住户-通行模块接口")
public class PassThroughController {

    @Autowired
    private RegionService regionService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private ApplyKeyService applyKeyService;

    /**
     * 查询当地当前天气信息，通过城市英文名
     * @param region 城市英文名
     * @return result
     * @author Mr.Deng
     * @date 15:17 2018/11/29
     */
    @GetMapping("/getWeather")
    @ApiOperation(value = "天气", notes = "输入参数：region为城市英文名")
    public Result getWeather(String region) {
        if (StringUtils.isNotBlank(region)) {
            Region byEnglishName = regionService.getByEnglishName(region);
            if (byEnglishName != null) {
                String s = "http://api.help.bj.cn/apis/weather/?id=" + byEnglishName.getCityCode();
                String s1 = HttpUtil.sendGet(s);
                JSONObject json = JSONObject.parseObject(s1);
                return Result.success(json);
            }
            return Result.error("城市英文名输入有误！");
        } else {
            return Result.error("请输入参数");
        }
    }

    /**
     * 发布通知通告信息
     * @param title       标题
     * @param type        类型
     * @param releaseTime 发布时间
     * @param synopsis    简介
     * @param publisher   发布人
     * @param creator     创建人
     * @param modifier    修改人
     * @param content     发布内容
     * @return Result
     * @author Mr.Deng
     * @date 16:35 2018/11/29
     */
    @PostMapping("/insertByNotice")
    @ApiOperation(value = "发布通知通告信息", notes = "输入参数：title 标题、type 类型、releaseTime 发布时间\n" +
            "synopsis 简介、publisher 发布人、creator 创建人、modifier 修改人")
    public Result insertByNotice(String title, String type, String typeName, String synopsis,
                                 String publisher, String creator, String modifier, String content) {
        noticeService.releaseNotice(title, type, typeName, synopsis, publisher, creator, modifier, content);
        return Result.success("发布成功！");
    }

    /**
     * 申请钥匙
     * @param communityCode    小区code
     * @param communityName    小区名称
     * @param zoneId           分区id
     * @param zoneName         分区名称
     * @param buildingId       楼栋id
     * @param buildingName     楼栋名称
     * @param unitId           单元id
     * @param unitName         单元名称
     * @param roomId           房间id
     * @param roomNum          房间编号
     * @param contactPerson    申请人
     * @param contactCellphone 申请人电话
     * @param content          描述
     * @param creatorUserId    创建人id
     * @return result
     * @author Mr.Deng
     * @date 15:01 2018/12/3
     */
    @PostMapping("/applyKey")
    @ApiOperation(value = "申请钥匙")
    public Result applyKey(String communityCode, String communityName, Integer zoneId, String zoneName,
                           Integer buildingId, String buildingName, Integer unitId, String unitName, Integer roomId,
                           String roomNum, String contactPerson, String contactCellphone, String content,
                           Integer creatorUserId) {
        List<String> images = Lists.newArrayListWithExpectedSize(5);
        applyKeyService.insertApplyKey(communityCode, communityName, zoneId, zoneName, buildingId, buildingName, unitId, unitName,
                roomId, roomNum, contactPerson, contactCellphone, content, creatorUserId, images);
        return Result.success("");
    }

}
