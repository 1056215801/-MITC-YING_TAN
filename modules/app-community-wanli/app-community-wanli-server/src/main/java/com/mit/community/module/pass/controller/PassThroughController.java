package com.mit.community.module.pass.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.mit.community.entity.ApplyKey;
import com.mit.community.entity.Region;
import com.mit.community.entity.Visitor;
import com.mit.community.service.*;
import com.mit.community.util.FastDFSClient;
import com.mit.community.util.HttpUtil;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

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

    @Autowired
    private VisitorService visitorService;

    @Autowired
    private OpenDoorService openDoorService;

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
     * @param title     标题
     * @param type      类型
     * @param synopsis  简介
     * @param publisher 发布人
     * @param creator   创建人
     * @param content   发布内容
     * @return Result
     * @author Mr.Deng
     * @date 16:35 2018/11/29
     */
    @PostMapping("/insertByNotice")
    @ApiOperation(value = "发布通知通告信息", notes = "输入参数：title 标题、type 类型、releaseTime 发布时间" +
            "synopsis 简介、publisher 发布人、creator 创建人")
    public Result insertByNotice(String title, String type, String typeName, String synopsis,
                                 String publisher, String creator, String content) {
        noticeService.releaseNotice(title, type, typeName, synopsis, publisher, creator, content);
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
    @PostMapping(value = "/applyKey", produces = {"application/json"})
    @ApiOperation(value = "申请钥匙", notes = "输入参数：communityCode 小区code;communityName 小区名；zoneId 分区id;" +
            "zoneName 分区名；buildingId 楼栋id ;buildingName 楼栋名；unitId 单元id；unitName 单元名；roomId 房间id;" +
            "roomNum 房间编号；contactPerson 申请人；contactCellphone 申请人电话；content 描述；creatorUserId 创建人用户id；" +
            "image")
    public Result applyKey(String communityCode, String communityName, Integer zoneId, String zoneName,
                           Integer buildingId, String buildingName, Integer unitId, String unitName, Integer roomId,
                           String roomNum, String contactPerson, String contactCellphone, String content,
                           Integer creatorUserId, String IDCord, MultipartFile[] images) {
        List<String> imageUrls = Lists.newArrayListWithExpectedSize(5);
        for (MultipartFile anImage : images) {
            String imageUrl = updateImages(anImage);
            imageUrls.add(imageUrl);
        }
        applyKeyService.insertApplyKey(communityCode, communityName, zoneId, zoneName, buildingId, buildingName, unitId,
                unitName, roomId, roomNum, contactPerson, contactCellphone, content, creatorUserId, IDCord, imageUrls);
        return Result.success("发布成功");
    }

    /**
     * 审批钥匙
     * @param applyKeyId  申请钥匙id
     * @param checkPerson 审批人
     * @return Result
     * @author Mr.Deng
     * @date 15:36 2018/12/3
     */
    @PatchMapping("/approveKey")
    @ApiOperation(value = "审批钥匙", notes = "applyKeyId 申请钥匙id ")
    public Result approveKey(Integer applyKeyId, String checkPerson) {
        applyKeyService.updateByCheckPerson(applyKeyId, checkPerson);
        return Result.success("审批成功");
    }

    /**
     * 查询申请钥匙信息，通过钥匙申请状态
     * @param status 钥匙申请状态
     * @return result
     * @author Mr.Deng
     * @date 15:48 2018/12/3
     */
    @GetMapping("/selectByStatus")
    @ApiOperation(value = "查找相应状态的申请钥匙数据", notes = "输入参数：status 1、申请中，2、审批通过")
    public Result selectByStatus(Integer status) {
        List<ApplyKey> applyKeys = applyKeyService.listByStatus(status);
        return Result.success(applyKeys);
    }

    /**
     * http开门
     * @param communityCode 小区code
     * @param username      用户名
     * @param password      密码
     * @param deviceNum     设备编号
     * @return result
     * @author Mr.Deng
     * @date 11:28 2018/12/4
     */
    @PostMapping("/httpOpenDoor")
    @ApiOperation(value = "http开门", notes = "输入参数：communityCode 小区code。username 用户名。" +
            "password 密码。deviceNum 设备编号")
    public Result httpOpenDoor(String communityCode, String username, String password, String deviceNum) {
        if (StringUtils.isNotBlank(communityCode) && StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)
                && StringUtils.isNotBlank(deviceNum)) {
            openDoorService.httpOpenDoor(communityCode, username, password, deviceNum);
            return Result.success("开锁成功");
        }
        return Result.error("参数不能为空");
    }

    /**
     * 获取我的钥匙
     * @param username      用户名
     * @param password      密码
     * @param communityCode 小区code
     * @return result
     * @author Mr.Deng
     * @date 14:02 2018/12/4
     */
    @PostMapping("/getMyKey")
    @ApiOperation(value = "获取我的钥匙", notes = "输入参数：username 用户名。password 密码。communityCode 小区code \n" +
            "返回参数: unitKeys 单元钥匙、 CommunityKeys 小区钥匙 ")
    public Result getMyKey(String username, String password, String communityCode) {
        if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password) && StringUtils.isNotBlank(communityCode)) {
            Map<String, Object> myKey = openDoorService.getMyKey(username, password, communityCode);
            return Result.success(myKey);
        }
        return Result.error("参数不能为空");
    }

    /**
     * 查询小区设备组信息,通过小区code
     * @param username      用户名
     * @param password      密码
     * @param communityCode 小区code
     * @return result
     * @author Mr.Deng
     * @date 16:53 2018/12/4
     */
    @GetMapping("/getDeviceGroup")
    @ApiOperation(value = "查询小区设备组信息,通过小区code", notes = "输入参数：username 用户名；password 密码；" +
            "communityCode 小区编号")
    public Result getDeviceGroup(String username, String password, String communityCode) {
        if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password) && StringUtils.isNotBlank(communityCode)) {
            String deviceGroup = openDoorService.getDeviceGroup(username, password, communityCode);
            JSONObject json = JSONObject.parseObject(deviceGroup);
            return Result.success(json);
        }
        return Result.error("参数不能为空");
    }

    /**
     * 申请访客邀请码
     * @param dateTag       日期标志：今天:0；明天：1;
     * @param times         开锁次数：无限次：0；一次：1；
     * @param deviceGroupId 设备分组id，默认只传公共权限组
     * @param communityCode 社区编号
     * @param username      用户名
     * @param password      密码
     * @return result
     * @author Mr.Deng
     * @date 16:38 2018/12/4
     */
    @PostMapping("/getInviteCode")
    @ApiOperation(value = "申请访客邀请码", notes = "返回参数：dateTag 日期标志：今天:0；明天：1; " +
            "times 开锁次数：无限次：0；一次：1；deviceGroupId 设备分组id，默认只传公共权限组；communityCode 社区编号；" +
            "username 用户名；password  密码")
    public Result getInviteCode(String dateTag, String times, String deviceGroupId, String communityCode, String username,
                                String password) {
        if (StringUtils.isNotBlank(dateTag) && StringUtils.isNotBlank(times) && StringUtils.isNotBlank(deviceGroupId) &&
                StringUtils.isNotBlank(communityCode) && StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
            String inviteCode = openDoorService.getInviteCode(dateTag, times, deviceGroupId, communityCode, username, password);
            JSONObject json = JSONObject.parseObject(inviteCode);
            return Result.success(json);
        }
        return Result.error("参数不能为空");
    }

    /**
     * 图片上传
     * @param image 文件
     * @return result
     * @author Mr.Deng
     * @date 9:05 2018/12/5
     */
    @PostMapping(value = "/updateImage", produces = {"application/json"})
    @ApiOperation(value = "上传图片")
    public Result updateImage(MultipartFile image) {
        if (image != null) {
            String result = StringUtils.EMPTY;
            try {
                FastDFSClient instance = FastDFSClient.getInstance();
                if (instance != null) {
                    result = instance.uploadFile(image);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return Result.error("上传失败");
            }
            return Result.success(result);
        } else {
            return Result.error("参数不能为空");
        }
    }

    /**
     * 查询所有访客信息
     * @return result
     * @author Mr.Deng
     * @date 18:28 2018/12/3
     */
    @GetMapping("/listSelectNotice")
    @ApiOperation(value = "查询所有访客信息")
    public Result listSelectNotice() {
        List<Visitor> list = visitorService.list();
        return Result.success(list);
    }

    /**
     * 设置呼叫转移号码
     * @param username  用户名
     * @param password  密码
     * @param sipMobile 转移号码
     * @return result
     * @author Mr.Deng
     * @date 10:31 2018/12/5
     */
    @PostMapping("/callForwarding")
    @ApiOperation(value = "设置呼叫转移号码", notes = "输入参数：username 用户名；password 密码；sipMobile 转移号码")
    public Result callForwarding(String username, String password, String sipMobile) {
        if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password) && StringUtils.isNotBlank(sipMobile)) {
            String str = openDoorService.callForwarding(username, password, sipMobile);
            if (StringUtils.isNotBlank(str)) {
                JSONObject jsonObject = JSONObject.parseObject(str);
                return Result.success(jsonObject);
            }
            return Result.error("设置失败");
        }
        return Result.error("参数不能为空");
    }

    /**
     * 图片上传方法
     * @param image 图片
     * @return 图片访问路径
     * @author Mr.Deng
     * @date 11:36 2018/12/6
     */
    private String updateImages(MultipartFile image) {
        String result = StringUtils.EMPTY;
        if (image != null) {
            try {
                FastDFSClient instance = FastDFSClient.getInstance();
                if (instance != null) {
                    result = instance.uploadFile(image);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
