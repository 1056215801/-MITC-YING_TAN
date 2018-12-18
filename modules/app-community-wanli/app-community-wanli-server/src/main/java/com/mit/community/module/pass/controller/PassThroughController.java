package com.mit.community.module.pass.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.*;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    private final RegionService regionService;
    private final NoticeService noticeService;
    private final ApplyKeyService applyKeyService;
    private final VisitorService visitorService;
    private final DnakeAppApiService dnakeAppApiService;
    private final HouseHoldService houseHoldService;
    private final ZoneService zoneService;
    private final UnitService unitService;
    private final RoomService roomService;
    private final BuildingService buildingService;
    private final AccessControlService accessControlService;
    private final DeviceGroupService deviceGroupService;
    private final RedisService redisService;
    private final ClusterCommunityService clusterCommunityService;
    private final WeatherService weatherService;
    private final DeviceService deviceService;
    private final UserTrackService userTrackService;
    private final DictionaryService dictionaryService;
    private final NoticeReadUserService noticeReadUserService;

    @Autowired
    public PassThroughController(RegionService regionService, NoticeService noticeService, ApplyKeyService applyKeyService,
                                 VisitorService visitorService,
                                 DnakeAppApiService dnakeAppApiService, HouseHoldService houseHoldService,
                                 ZoneService zoneService, UnitService unitService, RoomService roomService,
                                 BuildingService buildingService, AccessControlService accessControlService,
                                 DeviceGroupService deviceGroupService, WeatherService weatherService,
                                 RedisService redisService, ClusterCommunityService clusterCommunityService,
                                 DeviceService deviceService, UserTrackService userTrackService,
                                 DictionaryService dictionaryService, NoticeReadUserService noticeReadUserService) {
        this.regionService = regionService;
        this.noticeService = noticeService;
        this.applyKeyService = applyKeyService;
        this.visitorService = visitorService;
        this.dnakeAppApiService = dnakeAppApiService;
        this.houseHoldService = houseHoldService;
        this.zoneService = zoneService;
        this.unitService = unitService;
        this.roomService = roomService;
        this.buildingService = buildingService;
        this.accessControlService = accessControlService;
        this.deviceGroupService = deviceGroupService;
        this.weatherService = weatherService;
        this.redisService = redisService;
        this.clusterCommunityService = clusterCommunityService;
        this.deviceService = deviceService;
        this.userTrackService = userTrackService;
        this.dictionaryService = dictionaryService;
        this.noticeReadUserService = noticeReadUserService;
    }

    /**
     * 查询当地当前天气信息，通过城市英文名
     * @param region 城市英文名
     * @return result
     * @author Mr.Deng
     * @date 15:17 2018/11/29
     */
    @GetMapping("/getWeather")
    @ApiOperation(value = "天气", notes = "输入参数：region为城市英文名")
    public Result getWeather(String mac, String cellphone, String region) {
        if (StringUtils.isNotBlank(region) && StringUtils.isNotBlank(mac) && StringUtils.isNotBlank(cellphone)) {
            Weather weather = weatherService.ByCityeEnglish(region);
            return Result.success(weather);
        }
        return Result.error("参数不能为空");
    }

    /**
     * 查询当天通行限号
     * @return result
     * @author Mr.Deng
     * @date 10:34 2018/12/10
     */
    @GetMapping("/getRestrictionsPassNum")
    @ApiOperation(value = "通行限号", notes = "目前只支持南昌通行限号查询")
    public Result getRestrictionsPassNum() {
        LocalDate localDate = LocalDate.now();
        Integer dateType = dateInit(localDate);
        int value = localDate.getDayOfWeek().getValue();
        String[] s = {"1和6", "2和7", "3和8", "4和9", "5和0"};
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(2);
        String week;
        map.put("date", localDate);
        switch (value) {
            case 1:
                week = "星期一";
                break;
            case 2:
                week = "星期二";
                break;
            case 3:
                week = "星期三";
                break;
            case 4:
                week = "星期四";
                break;
            case 5:
                week = "星期五";
                break;
            case 6:
                week = "星期六";
                break;
            case 7:
                week = "星期日";
                break;
            default:
                week = StringUtils.EMPTY;
        }
        map.put("week", week);
        String str;
        if (dateType != null) {
            if (dateType == 0) {
                str = "限行尾号为" + s[value - 1];
            } else {
                str = "不限行";
            }
            map.put("status", str);
            return Result.success(map);
        }
        return Result.error("维修中");
    }

    /**
     * 查询周末和节假日期，通过时间戳
     * @param localDate 时间yyyy-MM-dd
     * @return 工作日对应结果为 0, 休息日对应结果为 1, 节假日对应的结果为 2
     * @author Mr.Deng
     * @date 10:30 2018/12/10
     */
    private Integer dateInit(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String dateStr = localDate.format(formatter);
        String url = "http://api.goseek.cn/Tools/holiday?date=" + dateStr;
        String s = HttpUtil.sendGet(url);
        Integer i = null;
        if (StringUtils.isNotBlank(s)) {
            JSONObject jsonObject = JSONObject.parseObject(s);
            i = jsonObject.getInteger("data");
        }
        return i;
    }

    /**
     * 发布通知通告信息
     * @param cellphone 手机号
     * @param title     标题
     * @param code      类型(查询字典notice_type)
     * @param synopsis  简介
     * @param publisher 发布人
     * @param creator   创建人
     * @param content   发布内容
     * @return Result
     * @author Mr.Deng
     * @date 16:35 2018/11/29
     */
    @PostMapping("/insertByNotice")
    @ApiOperation(value = "发布通知通告信息", notes = "输入参数：cellphone 手机号；title 标题、code 类型(查询字典notice_type)、" +
            "releaseTime 发布时间；synopsis 简介、publisher 发布人、creator 创建人")
    public Result insertByNotice(String cellphone, String title, String code, String synopsis,
                                 String publisher, String creator, String content) {
        if (StringUtils.isNotBlank(cellphone) && StringUtils.isNotBlank(title) && StringUtils.isNotBlank(code)
                && StringUtils.isNotBlank(synopsis) && StringUtils.isNotBlank(publisher) && StringUtils.isNotBlank(creator)
                && StringUtils.isNotBlank(content)) {
            noticeService.releaseNotice(title, code, synopsis, publisher, creator, content);
            //记录足迹
            Dictionary dictionary = dictionaryService.getByCode(code);
            if (dictionary != null) {
                String name = dictionary.getName();
                userTrackService.addUserTrack(cellphone, name, "发布" + name + "成功");
            }
            return Result.success("发布成功！");
        }
        return Result.error("参数不能为空");
    }

    /**
     * 查询所有的通知信息
     * @param cellphone 手机号
     * @return result
     * @author Mr.Deng
     * @date 13:55 2018/12/14
     */
    @GetMapping("/listNotices")
    @ApiOperation(value = "查询所有的通知信息", notes = "返回参数：title 标题；" +
            "type 通知类型。关联数据字典code为notice_type。：1、政策性通知。2、人口普查。3、入学通知。4、物业公告。" +
            "releaseTime 发布时间；synopsis 简介；publisher 发布人；creator 创建人；modifier 修改人")
    public Result listNotices(String cellphone) {
        if (StringUtils.isNotBlank(cellphone)) {
            User user = (User) redisService.get(RedisConstant.USER + cellphone);
            if (user != null) {
                List<Map<String, Object>> list = noticeService.listNotices(user.getId());
                return Result.success(list);
            }
            return Result.error("请登录");
        }
        return Result.error("参数不能为空");
    }

    /**
     * 查询通知详情，通过手机号和通告id
     * @param cellphone 手机号
     * @param noticeId  通告id
     * @return result
     * @author Mr.Deng
     * @date 13:49 2018/12/14
     */
    @GetMapping("/selectNotices")
    @ApiOperation(value = "查询通知详情，通过手机号和通告id", notes = "传参：cellphone 手机号 ；noticeId 通知id")
    public Result selectNotices(String cellphone, Integer noticeId) {
        if (StringUtils.isNotBlank(cellphone) && noticeId != null) {
            List<Object> notice = noticeService.getNoticInfoByNotiveId(noticeId);
            if (!notice.isEmpty()) {
                User user = (User) redisService.get(RedisConstant.USER + cellphone);
                if (user != null) {
                    //标记已读，如果已经读过了则不再进行标记记录
                    NoticeReadUser noticeReadUser1 = noticeReadUserService.getNoticeReadUser(noticeId, user.getId());
                    if (noticeReadUser1 == null) {
                        NoticeReadUser noticeReadUser = new NoticeReadUser(noticeId, user.getId());
                        noticeReadUserService.save(noticeReadUser);
                    }
                } else {
                    return Result.error("请登录,标记已读失败");
                }
            }
            return Result.success(notice);
        }
        return Result.error("参数不能为空");
    }

    /**
     * 查询通知信息浏览量
     * @param cellphone 手机号
     * @param noticeId  通知id
     * @return result
     * @author Mr.Deng
     * @date 14:17 2018/12/14
     */
    @GetMapping("/countViewNum")
    @ApiOperation(value = "查询通知信息浏览量")
    public Result countViewNum(String cellphone, Integer noticeId) {
        if (StringUtils.isNotBlank(cellphone) && noticeId != null) {
            Integer viewNum = noticeReadUserService.countViewNum(noticeId);
            return Result.success(viewNum);
        }
        return Result.error("参数不能为空");
    }

    /**
     * 申请钥匙
     * @param cellphone        手机号
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
     * @param cellphone        手机号
     * @return result
     * @author Mr.Deng
     * @date 15:01 2018/12/3
     */
    @PostMapping(value = "/applyKey", produces = {"application/json"})
    @ApiOperation(value = "申请钥匙", notes = "输入参数：cellphone 手机号，communityCode 小区code;communityName 小区名；zoneId 分区id;" +
            "zoneName 分区名；buildingId 楼栋id ;buildingName 楼栋名；unitId 单元id；unitName 单元名；roomId 房间id;" +
            "roomNum 房间编号；contactPerson 申请人；contactCellphone 申请人电话；content 描述；cellphone 手机号；" +
            "images 图片列表（可不传）")
    public Result applyKey(String cellphone, String communityCode, String communityName, Integer zoneId, String zoneName,
                           Integer buildingId, String buildingName, Integer unitId, String unitName, Integer roomId,
                           String roomNum, String contactPerson, String contactCellphone, String content,
                           String idCard, MultipartFile[] images) throws Exception {
        if (StringUtils.isNotBlank(cellphone) && StringUtils.isNotBlank(communityCode) && StringUtils.isNotBlank(communityName)
                && zoneId != null && StringUtils.isNotBlank(zoneName) && buildingId != null && StringUtils.isNotBlank(buildingName)
                && unitId != null && StringUtils.isNotBlank(unitName) && roomId != null && StringUtils.isNotBlank(roomNum)
                && StringUtils.isNotBlank(contactPerson) && StringUtils.isNotBlank(contactCellphone) && StringUtils.isNotBlank(content)
                && StringUtils.isNotBlank(idCard)) {
            List<String> imageUrls = Lists.newArrayListWithExpectedSize(5);
            if (images != null) {
                for (MultipartFile image : images) {
                    String imageUrl = Objects.requireNonNull(FastDFSClient.getInstance()).uploadFile(image);
                    imageUrls.add(imageUrl);
                }
            }
            User user = (User) redisService.get(RedisConstant.USER + cellphone);
            applyKeyService.insertApplyKey(communityCode, communityName, zoneId, zoneName, buildingId, buildingName, unitId,
                    unitName, roomId, roomNum, contactPerson, contactCellphone, content, user.getId(), idCard, imageUrls);
            //记录足迹
            userTrackService.addUserTrack(cellphone, "申请钥匙", "开门钥匙申请成功");
            return Result.success("发布成功");
        }
        return Result.error("参数不能为空");
    }

    /**
     * 审批钥匙
     * @param cellphone   手机号
     * @param applyKeyId  申请钥匙id
     * @param checkPerson 审批人
     * @return Result
     * @author Mr.Deng
     * @date 15:36 2018/12/3
     */
    @PatchMapping("/approveKey")
    @ApiOperation(value = "审批钥匙", notes = "传参：cellphone 手机号，applyKeyId 申请钥匙id，checkPerson 审批人 ")
    public Result approveKey(String cellphone, Integer applyKeyId, String checkPerson) {
        applyKeyService.updateByCheckPerson(applyKeyId, checkPerson);
        //记录足迹
        ApplyKey applyKey = applyKeyService.selectById(applyKeyId);
        if (applyKey != null) {
            String contactPerson = applyKey.getContactPerson();
            userTrackService.addUserTrack(cellphone, "审批钥匙", contactPerson + "钥匙审批成功");
        }
        return Result.success("审批成功");
    }

    /**
     * 查询申请钥匙信息，通过钥匙申请状态
     * @param cellphone 手机号
     * @param status    钥匙申请状态
     * @return result
     * @author Mr.Deng
     * @date 15:48 2018/12/3
     */
    @GetMapping("/listAppKeyByStatus")
    @ApiOperation(value = "查找相应状态的申请钥匙数据", notes = "输入参数：cellphone 手机号；status: 1、申请中，2、审批通过。不传则全部")
    public Result listAppKeyByStatusPage(String cellphone, Integer status, Integer pageNum, Integer pageSize) {
        if (StringUtils.isNotBlank(cellphone) && status != null) {
            User user = (User) redisService.get(RedisConstant.USER + cellphone);
            List<ApplyKey> applyKeys = applyKeyService.listByPage(user.getId(), null,
                    null, null, null, null, null,
                    null, status, pageNum, pageSize);
            //记录足迹
            String message;
            switch (status) {
                case -1:
                    message = "全部";
                    break;
                case 1:
                    message = "申请中";
                    break;
                case 2:
                    message = "审批通过";
                    break;
                default:
                    message = StringUtils.EMPTY;
            }
            userTrackService.addUserTrack(cellphone, "查询钥匙申请状态", "查询" + message + "钥匙成功");
            return Result.success(applyKeys);
        }
        return Result.error("参数不能为空");
    }

    /**
     * http开门
     * @param cellphone     手机号
     * @param communityCode 小区code
     * @param cellphone     电话号码
     * @param deviceNum     设备编号
     * @return result
     * @author Mr.Deng
     * @date 11:28 2018/12/4
     */
    @PostMapping("/httpOpenDoor")
    @ApiOperation(value = "http开门", notes = "输入参数：cellphone 手机号；communityCode 小区code。cellphone 电话号码。deviceNum 设备编号")
    public Result httpOpenDoor(String cellphone, String communityCode, String deviceNum) {
        if (StringUtils.isNotBlank(communityCode) && StringUtils.isNotBlank(deviceNum) && StringUtils.isNotBlank(cellphone)) {
            dnakeAppApiService.httpOpenDoor(cellphone, communityCode, deviceNum);
            //添加足迹
            Device device = deviceService.getByDeviceNumAndCommunityCode(communityCode, deviceNum);
            if (device != null) {
                userTrackService.addUserTrack(cellphone, "Http开门", device.getDeviceName() + "通过Http开门成功");
            }
            return Result.success("开锁成功");
        }
        return Result.error("参数不能为空");
    }

    /**
     * 获取我的钥匙
     * @param cellphone     电话号码
     * @param communityCode 小区code
     * @return result
     * @author Mr.Deng
     * @date 14:02 2018/12/4
     */
    @PostMapping("/getMyKey")
    @ApiOperation(value = "获取我的钥匙", notes = "输入参数：cellphone 手机号。communityCode 小区code \n" +
            "返回参数: unitKeys 单元钥匙、 CommunityKeys 小区钥匙 ")
    public Result getMyKey(String cellphone, String communityCode) {
        if (StringUtils.isNotBlank(cellphone) && StringUtils.isNotBlank(communityCode)) {
            Map<String, Object> myKey = dnakeAppApiService.getMyKey(cellphone, communityCode);
            //添加足迹
            userTrackService.addUserTrack(cellphone, "查询我的钥匙", "钥匙查询成功");
            return Result.success(myKey);
        }
        return Result.error("参数不能为空");
    }

    /**
     * 查询小区设备组信息,通过小区code
     * @param communityCode 小区code
     * @return result
     * @author Mr.Deng
     * @date 16:53 2018/12/4
     */
    @GetMapping("/getDeviceGroup")
    @ApiOperation(value = "查询小区设备组信息,通过小区code", notes = "输入参数：communityCode 小区编号")
    public Result getDeviceGroup(String mac, String cellphone, String communityCode) {
        if (StringUtils.isNotBlank(communityCode)) {
            List<DeviceGroup> deviceGroups = deviceGroupService.getByCommunityCode(communityCode);
            return Result.success(deviceGroups);
        }
        return Result.error("参数不能为空");
    }

    /**
     * 申请访客邀请码
     * @param dateTag       日期标志：今天:0；明天：1;
     * @param times         开锁次数：无限次：0；一次：1；
     * @param deviceGroupId 设备分组id，默认只传公共权限组
     * @param communityCode 社区编号
     * @param cellphone     电话号码
     * @return result
     * @author Mr.Deng
     * @date 16:38 2018/12/4
     */
    @PostMapping("/getInviteCode")
    @ApiOperation(value = "申请访客邀请码", notes = "传参：cellphone 手机号；dateTag 日期标志：今天:0；明天：1; " +
            "times 开锁次数：无限次：0；一次：1；deviceGroupId 设备分组id，默认只传公共权限组；communityCode 社区编号；")
    public Result getInviteCode(String cellphone, String dateTag, String times, String deviceGroupId, String communityCode) {
        if (StringUtils.isNotBlank(dateTag) && StringUtils.isNotBlank(times) && StringUtils.isNotBlank(deviceGroupId) &&
                StringUtils.isNotBlank(communityCode) && StringUtils.isNotBlank(cellphone)) {
            String inviteCode = dnakeAppApiService.getInviteCode(cellphone, dateTag, times, deviceGroupId, communityCode);
            JSONObject json = JSONObject.parseObject(inviteCode);
            //添加足迹
            userTrackService.addUserTrack(cellphone, "申请访客邀请码", "申请访客邀请码成功");
            return Result.success(json);
        }
        return Result.error("参数不能为空");
    }

    /**
     * 查询邀请码记录
     * @param cellphone 手机号
     * @param pageIndex 页码，从0开始
     * @param pageSize  页大小最大100
     * @return result
     * @author Mr.Deng
     * @date 14:46 2018/12/10
     */
    @GetMapping("/openHistory")
    @ApiOperation(value = "查询邀请码记录", notes = "输入参数：cellphone 手机号；pageIndex 页码，从0开始；pageSize 页大小（最大100）。<br/>" +
            " 输出参数：validityEndTime 过期时间、dataStatus 邀请码是否失效: 1可用，0失效、 " +
            "inviteCodeType 邀请码类型：1，今天无限次；2，今天仅一次；3，明天无限次；4，明天仅一次；5，高级设置、 " +
            "useTimes 使用次数，大于0则已使用")
    public Result openHistory(String cellphone, Integer pageIndex, Integer pageSize) {
        if (StringUtils.isNotBlank(cellphone) && pageIndex != null && pageSize != null) {
            String invoke = dnakeAppApiService.openHistory(cellphone, pageIndex, pageSize);
            JSONObject json = JSONObject.parseObject(invoke);
            //添加足迹
            userTrackService.addUserTrack(cellphone, "查询邀请码记录", "查询邀请码记录成功");
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
     * @param cellphone 手机号
     * @return result
     * @author Mr.Deng
     * @date 18:28 2018/12/3
     */
    @GetMapping("/listSelectNotice")
    @ApiOperation(value = "查询所有访客信息", notes = "传参：cellphone 手机号")
    public Result listSelectNotice(String cellphone) {
        if (StringUtils.isNotBlank(cellphone)) {
            List<Visitor> list = visitorService.list();
            //添加足迹
            userTrackService.addUserTrack(cellphone, "查询所有访客信息", "查询所有访客信息成功");
            return Result.success(list);
        }
        return Result.error("参数不能为空");
    }

    /**
     * 设置呼叫转移号码
     * @param cellphone 手机号
     * @param sipMobile 转移号码
     * @return result
     * @author Mr.Deng
     * @date 10:31 2018/12/5
     */
    @PostMapping("/callForwarding")
    @ApiOperation(value = "设置呼叫转移号码", notes = "输入参数：cellphone 电话号码；sipMobile 转移号码")
    public Result callForwarding(String cellphone, String sipMobile) {
        if (StringUtils.isNotBlank(cellphone) && StringUtils.isNotBlank(sipMobile)) {
            String str = dnakeAppApiService.callForwarding(cellphone, sipMobile);
            JSONObject jsonObject = JSONObject.parseObject(str);
            //添加足迹
            userTrackService.addUserTrack(cellphone, "设置呼叫转移号码", "设置呼叫转移号码设置成功");
            return Result.success(jsonObject);
        }
        return Result.error("参数不能为空");
    }

    /**
     * 查询住户信息，通过用户id
     * @param userId 用户id
     * @return result
     * @author Mr.Deng
     * @date 9:48 2018/12/8
     */
    @ApiOperation(value = "查询住户信息，通过用户id")
    @GetMapping("/listHouseHoldByUserId")
    public Result listHouseHoldByUserId(Integer userId) {
        List<HouseHold> houseHolds = houseHoldService.listByUserId(userId);
        return Result.success(houseHolds);
    }

    /**
     * @param city 城市名
     * @return result
     * @author shuyy
     * @date 2018-12-11
     */
    @ApiOperation(value = "查询小区，通过城市名")
    @GetMapping("/listCommunityByCity")
    public Result listCommunityByCity(String city) {
        List<ClusterCommunity> clusterCommunities = clusterCommunityService.listByCityName(city);
        return Result.success(clusterCommunities);
    }

    /**
     * 查询省份
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/11 14:12
     * @company mitesofor
     */
    @ApiOperation(value = "查询省份")
    @GetMapping("/listProvince")
    public Result listProvince() {
        List<Map<String, Object>> maps = clusterCommunityService.listProvince();
        return Result.success(maps);
    }

    /**
     * 查询城市，通过省份
     * @param province 省份
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/11 14:13
     * @company mitesofor
     */
    @ApiOperation(value = "查询城市")
    @GetMapping("/listCityByProvince")
    public Result listCityByProvince(String province) {
        List<Map<String, Object>> maps = clusterCommunityService.listCityByProvince(province);
        return Result.success(maps);
    }

    /**
     * 查询分区信息，通过小区code
     * @param communityCode 小区code
     * @return result
     * @author Mr.Deng
     * @date 9:24 2018/12/8
     */
    @ApiOperation(value = "查询分区信息，通过小区code")
    @GetMapping("/listZoneByCommunityCode")
    public Result listZoneByCommunityCode(String communityCode) {
        if (StringUtils.isNotBlank(communityCode)) {
            List<Zone> zones = zoneService.listByCommunityCode(communityCode);
            List<Zone> zoneList = Lists.newArrayListWithExpectedSize(20);
            if (!zones.isEmpty()) {
                for (Zone zone : zones) {
                    String zoneName = zone.getZoneName();
                    if (!"默认分区".equals(zoneName)) {
                        zoneList.add(zone);
                    }
                }
            }
            return Result.success(zoneList);
        }
        return Result.error("参数不能为空");
    }

    /**
     * 查询楼栋信息，通过分区id
     * @param zoneId 分区id
     * @return 楼栋信息
     * @author Mr.Deng
     * @date 9:27 2018/12/8
     */
    @ApiOperation(value = "查询楼栋信息，通过分区id")
    @GetMapping("/listBuildingByZoneId")
    public Result listBuildingByZoneId(Integer zoneId) {
        if (zoneId != null) {
            List<Building> buildings = buildingService.listByZoneId(zoneId);
            return Result.success(buildings);
        }
        return Result.error("参数不能为空");
    }

    /**
     * 查询单元信息，通过楼栋id
     * @param buildingId 楼栋id
     * @return result
     * @author Mr.Deng
     * @date 9:29 2018/12/8
     */
    @ApiOperation(value = "查询单元信息，通过楼栋id")
    @GetMapping("/listUnitByBuildingId")
    public Result listUnitByBuildingId(Integer buildingId) {
        if (buildingId != null) {
            List<Unit> units = unitService.listByBuildingId(buildingId);
            return Result.success(units);
        }
        return Result.error("参数不能为空");
    }

    /**
     * 查询房间信息，通过单元id
     * @param unitId 单元id
     * @return result
     * @author Mr.Deng
     * @date 9:39 2018/12/8
     */
    @ApiOperation(value = "查询房间信息，通过单元id")
    @GetMapping("/listRoomByUnitId")
    public Result listRoomByUnitId(Integer unitId) {
        if (unitId != null) {
            List<Room> rooms = roomService.listByUnitId(unitId);
            return Result.success(rooms);
        }
        return Result.error("参数不能为空");
    }

    /**
     * 查询门禁记录，通过手机号
     * @param cellphone     手机号码
     * @param communityCode 小区code
     * @param type          类型；M：单元机；W：门口机；
     * @return result
     * @author Mr.Deng
     * @date 10:47 2018/12/8
     */
    @GetMapping("/listAccessControlByHouseHoldId")
    @ApiOperation(value = "查询门禁记录，通过手机号", notes = "传参：cellphone 手机号，communityCode 社区code,type M：单元机；W：门口机；")
    public Result listAccessControlByHouseHoldId(String cellphone, String communityCode, String type) {
        List<AccessControl> list = Lists.newArrayListWithExpectedSize(100);
        if (StringUtils.isNotBlank(cellphone) && StringUtils.isNotBlank(communityCode)) {
            List<AccessControl> accessControls = accessControlService.listByUserId(communityCode, cellphone);
            if (!accessControls.isEmpty()) {
                for (AccessControl accessControl : accessControls) {
                    Device device = deviceService.getByDeviceNumAndCommunityCode(communityCode, accessControl.getDeviceNum());
                    if (device != null) {
                        String deviceType = device.getDeviceType();
                        if (type.equals(deviceType)) {
                            list.add(accessControl);
                        }
                    } else {
                        continue;
                    }
                }
                //添加足迹
                userTrackService.addUserTrack(cellphone, "查询门禁记录", "门禁记录查询成功");
                return Result.success(list);
            }
        }
        return Result.error("参数不能为空");
    }

    /**
     * 访客高级邀请，
     * @param cellphone     手机号
     * @param times         时间段（时间格式（yyyy-MM-dd HH:mm:ss）：{"startTime,endTime","startTime,endTime"}）
     * @param deviceGroupId 设备组id
     * @param communityCode 小区code
     * @param roomNum       房号
     * @return String
     * @author Mr.Deng
     * @date 15:08 2018/12/17
     */
    @GetMapping("/highGrade")
    @ApiOperation(value = "访客邀请（高级模式）", notes = "传参：cellphone  手机号；deviceGroupId 设备组id；communityCode 小区code" +
            "times  时间段（时间格式（yyyy-MM-dd HH:mm:ss）：{\"startTime,endTime\",\"startTime,endTime\"}）")
    public String highGrade(String cellphone, String[] times, String roomNum, String deviceGroupId, String
            communityCode) {
        Map<String, Object> timeMap = Maps.newHashMapWithExpectedSize(4);
        timeMap.put("start_time", "1517212800");
        timeMap.put("end_time", "1517220000");
        timeMap.put("once", 0);
        timeMap.put("room", roomNum);
        List<Map<String, Object>> list = Lists.newArrayListWithExpectedSize(15);
        list.add(timeMap);
        return dnakeAppApiService.highGrade(cellphone, list, deviceGroupId, communityCode);
    }
}
