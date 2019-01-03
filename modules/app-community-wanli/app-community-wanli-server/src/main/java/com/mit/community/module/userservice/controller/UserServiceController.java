package com.mit.community.module.userservice.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.*;
import com.mit.community.service.*;
import com.mit.community.util.FastDFSClient;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 住户-服务控制类
 * @author Mr.Deng
 * @date 2018/12/5 11:19
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
@RestController
@RequestMapping("/userService")
@Slf4j
@Api(tags = "住户-服务模块接口")
public class UserServiceController {

    private final ReportThingsRepairService reportThingsRepairService;
    private final CommunityServiceInfoService communityServiceInfoService;
    private final BusinessHandlingService businessHandlingService;
    private final CommunityPhoneService communityPhoneService;
    private final YellowPagesService yellowPagesService;
    private final FeedBackService feedBackService;
    private final UserTrackService userTrackService;
    private final DictionaryService dictionaryService;
    private final LostFoundService lostFoundService;
    private final ExpressAddressService expressAddressService;
    private final ExpressInfoService expressInfoService;
    private final ExpressReadUserService expressReadUserService;
    private final RedisService redisService;
    private final LostFountReadUserService lostFountReadUserService;
    private final PromotionService promotionService;
    private final PromotionReadUserService promotionReadUserService;
    private final OldMedicalReadUserService oldMedicalReadUserService;
    private final OldMedicalService oldMedicalService;
    private final SysMessagesService sysMessagesService;
    private final SelectionActivitiesService selectionActivitiesService;
    private final AccessControlService accessControlService;
    private final DnakeAppApiService dnakeAppApiService;
    private final SysMessageReadService sysMessageReadService;
    private final HouseHoldService houseHoldService;

    @Autowired
    public UserServiceController(ReportThingsRepairService reportThingsRepairService,
                                 CommunityServiceInfoService communityServiceInfoService,
                                 BusinessHandlingService businessHandlingService,
                                 CommunityPhoneService communityPhoneService, YellowPagesService yellowPagesService,
                                 FeedBackService feedBackService, UserTrackService userTrackService,
                                 DictionaryService dictionaryService, LostFoundService lostFoundService,
                                 ExpressAddressService expressAddressService, ExpressInfoService expressInfoService,
                                 ExpressReadUserService expressReadUserService, RedisService redisService,
                                 LostFountReadUserService lostFountReadUserService, PromotionService promotionService,
                                 PromotionReadUserService promotionReadUserService,
                                 OldMedicalReadUserService oldMedicalReadUserService, OldMedicalService oldMedicalService,
                                 SysMessagesService sysMessagesService, SelectionActivitiesService selectionActivitiesService,
                                 AccessControlService accessControlService, DnakeAppApiService dnakeAppApiService,
                                 SysMessageReadService sysMessageReadService, HouseHoldService houseHoldService) {
        this.reportThingsRepairService = reportThingsRepairService;
        this.communityServiceInfoService = communityServiceInfoService;
        this.businessHandlingService = businessHandlingService;
        this.communityPhoneService = communityPhoneService;
        this.yellowPagesService = yellowPagesService;
        this.feedBackService = feedBackService;
        this.userTrackService = userTrackService;
        this.dictionaryService = dictionaryService;
        this.lostFoundService = lostFoundService;
        this.expressAddressService = expressAddressService;
        this.expressInfoService = expressInfoService;
        this.expressReadUserService = expressReadUserService;
        this.redisService = redisService;
        this.lostFountReadUserService = lostFountReadUserService;
        this.promotionService = promotionService;
        this.promotionReadUserService = promotionReadUserService;
        this.oldMedicalReadUserService = oldMedicalReadUserService;
        this.oldMedicalService = oldMedicalService;
        this.sysMessagesService = sysMessagesService;
        this.selectionActivitiesService = selectionActivitiesService;
        this.accessControlService = accessControlService;
        this.dnakeAppApiService = dnakeAppApiService;
        this.sysMessageReadService = sysMessageReadService;
        this.houseHoldService = houseHoldService;
    }

    /**
     * 申请报事报修
     * @param cellphone       手机号
     * @param communityCode   小区code
     * @param roomId          房间id
     * @param roomNum         房间号
     * @param content         报事内容
     * @param reportUser      报事人
     * @param reportCellphone 联系人
     * @param maintainType    维修类型.关联字典code maintain_type 维修类型：1、水，2、电，3、可燃气，4、锁，5、其他
     * @param creatorUserId   创建用户id
     * @return result
     * @author Mr.Deng
     * @date 20:16 2018/12/3
     */
    @PostMapping(value = "/applyReportThingsRepair", produces = {"application/json"})
    @ApiOperation(value = "申请报事报修", notes = "输入参数：输入参数：communityCode 小区code;cellphone 登录用户手机号；roomId 房间id;" +
            "roomNum 房间编号；content 报事内容；reportUser 报事人；reportCellphone 报事人手机号；" +
            "maintainType 维修类型.关联字典code maintain_type 维修类型：1、水，2、电，3、可燃气，4、锁，5、其他；" +
            "creatorUserId 创建用户id；images 图片（可不传），appointmentTime 预约时间（yyyy-MM-dd HH:mm:ss）")
    public Result applyReportThingsRepair(String communityCode, String cellphone, Integer roomId, String roomNum, String content,
                                          String reportUser, String reportCellphone, String maintainType,
                                          Integer creatorUserId, String appointmentTime, MultipartFile[] images) throws Exception {
        if (StringUtils.isNotBlank(communityCode) && StringUtils.isNotBlank(cellphone) && roomId != null && StringUtils.isNotBlank(roomNum)
                && StringUtils.isNotBlank(content) && StringUtils.isNotBlank(reportUser) && StringUtils.isNotBlank(reportCellphone)
                && StringUtils.isNotBlank(maintainType) && creatorUserId != null && StringUtils.isNotBlank(appointmentTime)) {
            if (appointmentTime.length() == 19) {
                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime appointmentTimes = LocalDateTime.parse(appointmentTime, df);
                //上传图片路径
                List<String> imageUrls = Lists.newArrayListWithExpectedSize(5);
                if (images != null) {
                    for (MultipartFile image : images) {
                        String imageUrl = Objects.requireNonNull(FastDFSClient.getInstance()).uploadFile(image);
                        imageUrls.add(imageUrl);
                    }
                }
                reportThingsRepairService.applyReportThingsRepair(communityCode, cellphone, roomId, roomNum, content,
                        reportUser, reportCellphone, maintainType, creatorUserId, appointmentTimes, imageUrls);
                //记录足迹
                Dictionary dictionary = dictionaryService.getByCode(maintainType);
                if (dictionary != null) {
                    userTrackService.addUserTrack(cellphone, "申请报事报修", "维修类型" + dictionary.getName() + "申请报事报修成功");
                }
                return Result.success("申请成功");
            }
            return Result.error("预约时间格式不对");
        }
        return Result.error("参数不能为空");
    }

    /**
     * 查询报事报修状态数据，通过手机号
     * @param cellphone 手机号
     * @param status    保修状态 0、未完成。1、已完成
     * @return result
     * @author Mr.Deng
     * @date 21:02 2018/12/3
     */
    @GetMapping("/listReportThingsRepairByStatus")
    @ApiOperation(value = "查询相应状态的报事报修数据", notes = "输入参数：cellphone 手机号，status 0、未完成。1、已完成")
    public Result listReportThingsRepairByStatus(String cellphone, Integer status, Integer pageNum, Integer pageSize) {
        if (StringUtils.isNotBlank(cellphone) && status != null) {
            Page<ReportThingsRepair> reportThingsRepairs = reportThingsRepairService.listReportThingsRepairByStatus(cellphone, status, pageNum, pageSize);
            String st = (status == 0) ? "未完成" : "已完成";
            userTrackService.addUserTrack(cellphone, "查询报事报修", "查询状态" + st + "报事报修成功");
            return Result.success(reportThingsRepairs);
        }
        return Result.error("参数不能为空");
    }

    /**
     * 查询报事报修详情信息，通告报事报修id
     * @param reportThingsRepairId 报事报修id
     * @return result
     * @author Mr.Deng
     * @date 19:01 2018/12/19
     */
    @GetMapping("/getReportThingsRepairById")
    @ApiOperation(value = "查询报事报修详情信息，通告报事报修id", notes = "输入参数：reportThingsRepairId 报事报修id<br/>" +
            "返回参数:")
    public Result getReportThingsRepairById(Integer reportThingsRepairId) {
        if (reportThingsRepairId != null) {
            ReportThingsRepair reportThingsRepair = reportThingsRepairService.getReportThingsRepair(reportThingsRepairId);
            return Result.success(reportThingsRepair);
        }
        return Result.error("参数不能为空");
    }

    /**
     * 报事报修评价
     * @param applyReportId             报事报修id
     * @param evaluateResponseSpeed     响应速度评价
     * @param evaluateResponseAttitude  响应态度评价
     * @param evaluateTotal             总体评价
     * @param evaluateServiceProfession 服务专业度评价
     * @param evaluateContent           评价内容
     * @return Result
     * @author Mr.Deng
     * @date 11:14 2018/12/5
     */
    @PatchMapping("/evaluateReportThingsRepair")
    @ApiOperation(value = "报事报修评价", notes = "输入参数：applyReportId 报事报修id；evaluateResponseSpeed  响应速度评价；" +
            "evaluateResponseAttitude  响应态度评价；evaluateTotal  总体评价；evaluateServiceProfession 服务专业度评价；" +
            "evaluateContent   评价内容")
    public Result evaluateReportThingsRepair(String cellphone, Integer applyReportId, Integer evaluateResponseSpeed,
                                             Integer evaluateResponseAttitude,
                                             Integer evaluateTotal, Integer evaluateServiceProfession, String evaluateContent) {
        if (StringUtils.isNotBlank(cellphone) && applyReportId != null && evaluateResponseSpeed != null &&
                evaluateResponseAttitude != null && evaluateTotal != null
                && evaluateServiceProfession != null && StringUtils.isNotBlank(evaluateContent)) {
            reportThingsRepairService.evaluateReportThingsRepair(applyReportId, evaluateResponseSpeed,
                    evaluateResponseAttitude, evaluateTotal, evaluateServiceProfession, evaluateContent);
            userTrackService.addUserTrack(cellphone, "报事报修评价", "报事报修评价成功");
            return Result.success("评价成功");
        }
        return Result.error("参数不能为空");
    }

    /**
     * 查询社区服务信息，通过社区code
     * @param cellphone 手机号
     * @param longitude 经度
     * @param latitude  纬度
     * @param type      社区服务类型 关联字典code community_service_type 社区服务类型 1、社区门诊2、开锁换锁3、送水到家
     * @return result
     * @author Mr.Deng
     * @date 11:38 2018/12/5
     */
    @GetMapping("/listCommunityServiceInfoByCommunityCode")
    @ApiOperation(value = "查询社区门诊信息，通过小区code", notes = "输入参数：cellphone 手机号；（坐标为当前用户定位坐标）" +
            "longitude 经度;latitude 纬度；type 社区服务类型.关联字典code community_service_type 社区服务类型" +
            " \n返回参数：name 名称；address 地址；cellphone 电话号码；distance 距离；longitude 经度;latitude 纬度" +
            "image 图片地址；creatorUserId 创建用户id ")
    public Result listCommunityServiceInfoByCommunityCode(String cellphone, Double longitude, Double latitude, String type) {
        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(cellphone) && longitude != null && latitude != null) {
            List<CommunityServiceInfo> communityClinics = communityServiceInfoService.findNeighPosition(longitude, latitude, type);
            //记录足迹
            Dictionary dictionary = dictionaryService.getByCode(type);
            if (dictionary != null) {
                userTrackService.addUserTrack(cellphone, "查询社区服务", "查询" + dictionary.getName() + "信息成功");
            }
            return Result.success(communityClinics);
        }
        return Result.error("参数不能为空");
    }

    /**
     * 查询社区电话，通过小区code和电话类型
     * @param cellphone     手机号
     * @param communityCode 小区code
     * @param type          社区电话类型.关联字典code community_phone_type   社区电话类型1、物业电话；2、紧急电话
     * @return result
     * @author Mr.Deng
     * @date 16:01 2018/12/5
     */
    @GetMapping("/listCommunityPhoneByCommunityCodeAndType")
    @ApiOperation(value = "查询社区电话，通过小区code和电话类型", notes = "输入参数：cellphone 手机号;communityCode 小区code" +
            "type 社区电话类型,关联字典code community_phone_type 1、物业电话；2、紧急电话")
    public Result listCommunityPhoneByCommunityCodeAndType(String cellphone, String communityCode, String type) {
        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(communityCode) && StringUtils.isNotBlank(cellphone)) {
            List<CommunityPhone> communityPhones = communityPhoneService.listByCommunityCodeAndType(communityCode, type);
            //记录足迹
            Dictionary dictionary = dictionaryService.getByCode(type);
            if (dictionary != null) {
                userTrackService.addUserTrack(cellphone, "查询社区电话", "查询" + dictionary.getName() + "信息成功");
            }
            return Result.success(communityPhones);
        }
        return Result.error("参数不能为空");
    }

    /**
     * 申请业务办理
     * @param cellphone        手机号码
     * @param communityCode    小区code
     * @param roomId           房间id
     * @param roomNum          房间号
     * @param contactPerson    申请人
     * @param contactCellphone 申请人电话
     * @param content          申请内容
     * @param type             业务类型(关联字典表，code为business_handling_type。)
     * @param creatorUserId    创建人用户id
     * @return result
     * @author Mr.Deng
     * @date 14:31 2018/12/5
     */
    @PostMapping(value = "/applyBusinessHandling", produces = {"application/json"})
    @ApiOperation(value = "申请业务办理", notes = "输入参数：cellphone 手机号码，communityCode 小区code;roomId 房间id;" +
            "roomNum 房间编号；contactPerson 申请人；contactCellphone 申请人电话；content 申请内容\n" +
            "type 业务类型：(关联字典表，code为business_handling_type。)；" +
            "creatorUserId    创建人用户id;images 图片（可传可不传）")
    public Result applyBusinessHandling(String cellphone, String communityCode, Integer roomId, String roomNum,
                                        String contactPerson, String contactCellphone,
                                        String content, String type, Integer creatorUserId, MultipartFile[] images) throws Exception {
        if (StringUtils.isNotBlank(cellphone) && StringUtils.isNotBlank(communityCode) && StringUtils.isNotBlank(roomNum)
                && roomId != null && StringUtils.isNotBlank(contactPerson) && StringUtils.isNotBlank(contactCellphone)
                && StringUtils.isNotBlank(content) && StringUtils.isNotBlank(type) && creatorUserId != null) {
            //上传图片地址列表
            List<String> imageUrls = Lists.newArrayListWithExpectedSize(5);
            if (images != null) {
                for (MultipartFile image : images) {
                    String imageUrl = Objects.requireNonNull(FastDFSClient.getInstance()).uploadFile(image);
                    imageUrls.add(imageUrl);
                }
            }
            businessHandlingService.applyBusinessHandling(cellphone, communityCode, roomId, roomNum, contactPerson,
                    contactCellphone, content, type, creatorUserId, imageUrls);
            //记录足迹
            Dictionary dictionary = dictionaryService.getByCode(type);
            if (dictionary != null) {
                String name = dictionary.getName();
                userTrackService.addUserTrack(cellphone, "申请业务办理", name + "业务办理成功");
            }
            return Result.success("申请提交成功");
        }
        return Result.error("参数不能为空");
    }

    /**
     * 查询业务办理状态数据，通过用户id
     * @param creatorUserId 用户id
     * @param status        业务办理状态 0、未完成。1、已完成
     * @return result
     * @author Mr.Deng
     * @date 14:47 2018/12/5
     */
    @GetMapping("/listBusinessHandlingByStatus")
    @ApiOperation(value = "查询业务办理状态数据，通过用户id", notes = "输入参数：creatorUserId 用户id；" +
            "业务办理状态 0、未完成。1、已完成")
    public Result listBusinessHandlingByStatus(String cellphone, Integer creatorUserId, Integer status, Integer pageNum, Integer pageSize) {
        User user = (User) redisService.get(RedisConstant.USER + cellphone);
        Integer householdId = user.getHouseholdId();
        HouseHold household = houseHoldService.getByHouseholdId(householdId);
        String communityCode = household.getCommunityCode();
        if (creatorUserId != null && status != null && StringUtils.isNotBlank(cellphone)) {
            Page<BusinessHandling> listBusinessHandling = businessHandlingService
                    .pageByStatus(creatorUserId, status, communityCode, pageNum, pageSize);
            String st = (status == 0) ? "未完成" : "已完成";
            userTrackService.addUserTrack(cellphone, "查询业务办理数据", "查询办理状态" + st + "业务数据成功");
            return Result.success(listBusinessHandling);
        }
        return Result.error("参数不能为空");
    }

    /**
     * 查询业务办理详情信息，通告业务办理id
     * @param businessHandlingId 业务办理id
     * @return result
     * @author Mr.Deng
     * @date 19:41 2018/12/19
     */
    @GetMapping("/getByBusinessHandlingId")
    @ApiOperation(value = "查询业务办理详情信息，通告业务办理id", notes = "输出参数：number 工单号；communityCode 小区code;" +
            "communityName 小区名称；zoneId 分区id；zoneName 分区name;buildingId 楼栋id；buildingName 楼栋名称；unitId 单元id；" +
            "unitName 单元名称；roomId 房间id; roomNum 房间编号；contactPerson 申请人；contactCellphone 申请人电话；content 申请内容" +
            "type 业务类型：(关联字典表，code为business_handling_type。)；creatorUserId  创建人用户id;" +
            "evaluateResponseSpeed 响应速度评价；evaluateResponseAttitude 响应态度评价；evaluateTotal 总体评价；" +
            "evaluateServiceProfession 服务专业度评价；evaluateContent 评价内容；receiver 受理人；receiverTime 受理时间；" +
            "processor 处理人；processorPhone 处理电话；processorStartTime 开始处理时间；processorEndTime 处理完成时间；images 图片")
    public Result getByBusinessHandlingId(Integer businessHandlingId) {
        if (businessHandlingId != null) {
            BusinessHandling businessHanding = businessHandlingService.getByBusinessHandlingId(businessHandlingId);
            return Result.success(businessHanding);
        }
        return Result.error("参数不能为空");
    }

    /**
     * 业务办理评价
     * @param cellphone                 手机号
     * @param businessHandlingId        业务办理id
     * @param evaluateResponseSpeed     响应速度评价
     * @param evaluateResponseAttitude  响应态度评价
     * @param evaluateTotal             总体评价
     * @param evaluateServiceProfession 服务专业度评价
     * @param evaluateContent           评价内容
     * @return result
     * @author Mr.Deng
     * @date 15:00 2018/12/5
     */
    @PatchMapping("/evaluateBusinessHandling")
    @ApiOperation(value = "业务办理评价", notes = "输入参数:cellphone 手机号码，businessHandlingId 业务办理id；" +
            "evaluateResponseSpeed  响应速度评价；" +
            "evaluateResponseAttitude  响应态度评价；evaluateTotal  总体评价；evaluateServiceProfession 服务专业度评价；" +
            "evaluateContent   评价内容；评价范围为0-5")
    public Result evaluateBusinessHandling(String cellphone, Integer businessHandlingId, Integer evaluateResponseSpeed,
                                           Integer evaluateResponseAttitude, Integer evaluateTotal,
                                           Integer evaluateServiceProfession, String evaluateContent) {
        if (businessHandlingId != null && evaluateResponseSpeed != null && evaluateResponseAttitude != null
                && evaluateTotal != null && evaluateServiceProfession != null && StringUtils.isNotBlank(evaluateContent)
                && StringUtils.isNotBlank(cellphone)) {
            businessHandlingService.evaluateBusinessHandling(businessHandlingId, evaluateResponseSpeed,
                    evaluateResponseAttitude, evaluateTotal, evaluateServiceProfession, evaluateContent);
            //记录足迹
            BusinessHandling businessHandling = businessHandlingService.getById(businessHandlingId);
            if (businessHandling != null) {
                Dictionary dictionary = dictionaryService.getByCode(businessHandling.getType());
                if (dictionary != null) {
                    String name = dictionary.getName();
                    userTrackService.addUserTrack(cellphone, name, businessHandling.getContactPerson() + "-" + name + "评价成功");
                }
            }
            return Result.success("评价成功");
        }
        return Result.error("参数不能为空");
    }

    /**
     * 查询所有的黄页菜单
     * @return result
     * @author Mr.Deng
     * @date 9:18 2018/12/6
     */
    @GetMapping("/listAllYellowPages")
    @ApiOperation(value = "查询所有的黄页菜单", notes = "返回参数：submenuNames 子菜单列表，parent_name 父菜单")
    public Result listAllYellowPages() {
        List<Map<String, Object>> allYellowPages = yellowPagesService.listAllYellowPages();
        return Result.success(allYellowPages);
    }

    /**
     * 查询生活黄页信息，通过黄页类型id
     * @param cellphone         手机号
     * @param yellowPagesTypeId 黄页类型id
     * @return Result
     * @author Mr.Deng
     * @date 9:23 2018/12/6
     */
    @GetMapping("/listByYellowPagesTypeId")
    @ApiOperation(value = "查询生活黄页信息，通过黄页类型id", notes = "输入参数：cellphone  手机号；<br/>" +
            "单个查询输入（yellowPagesTypeId 黄页菜单id）(传入参数查询某一个子菜单的号码) <br/>" +
            "点击更多时输入（parentName 黄页父菜单名（银行/生活服务））<br/>")
    public Result listByYellowPagesTypeId(String cellphone, Integer yellowPagesTypeId, String parentName) {
        if (StringUtils.isNotBlank(cellphone)) {
            if (null != yellowPagesTypeId) {
                Map<String, Object> map = yellowPagesService.mapToPhoneByYellowPagesTypeId(yellowPagesTypeId);
                if (!map.isEmpty()) {
                    userTrackService.addUserTrack(cellphone, "查询生活黄页", "查询生活黄页" + map.get("submenuName") + "成功");
                }
                return Result.success(map);
            } else {
                if (StringUtils.isNotBlank(parentName)) {
                    List<Map<String, Object>> list = yellowPagesService.listToPhoneByParentName(parentName);
                    if (!list.isEmpty()) {
                        userTrackService.addUserTrack(cellphone, "查询生活黄页", "查询全部生活黄页成功");
                    }
                    return Result.success(list);
                }
            }
        }
        return Result.error("参数不能为空");
    }

    /**
     * 提交反馈意见
     * @param cellphone 手机号
     * @param title     标题
     * @param content   反馈内容
     * @param type      类型。关联数据字典。code为feedback_type。1、APP功能反馈。2、物业/小区问题
     * @param userId    用户id。关联user表id
     * @param images    图片列表
     * @return result
     * @author Mr.Deng
     * @date 18:29 2018/12/6
     */
    @PostMapping(value = "/submitFeedBack", produces = {"application/json"})
    @ApiOperation(value = "提交反馈意见", notes = "输入参数：cellphone 手机号； title 标题；content 反馈内容；" +
            "type 类型。关联数据字典。code为feedback_type。1、APP功能反馈。2、物业/小区问题;userId 用户id；image 图片列表")
    public Result submitFeedBack(String cellphone, String title, String content, String type, Integer userId, MultipartFile[] images) throws Exception {
        if (StringUtils.isNotBlank(title) && StringUtils.isNotBlank(content) && StringUtils.isNotBlank(type)
                && userId != null && StringUtils.isNotBlank(cellphone)) {
            List<String> imageUrls = Lists.newArrayListWithCapacity(5);
            if (null != images) {
                for (MultipartFile image : images) {
                    String imageUrl = Objects.requireNonNull(FastDFSClient.getInstance()).uploadFile(image);
                    imageUrls.add(imageUrl);
                }
            }
            feedBackService.submitFeedBack(title, content, type, userId, imageUrls);
            //记录足迹
            Dictionary dictionary = dictionaryService.getByCode(type);
            if (dictionary != null) {
                userTrackService.addUserTrack(cellphone, "反馈意见", "提交" + dictionary.getName() + "成功");
            }
            return Result.success("提交成功");
        }
        return Result.error("参数不能为空");
    }

    /**
     * 查询快递位置信息
     * @param cellphone     手机号
     * @param communityCode 小区code
     * @return result
     * @author Mr.Deng
     * @date 17:21 2018/12/14
     */
    @GetMapping("/listExpressAddress")
    @ApiOperation(value = "查询快递位置信息", notes = "输出参数：imgUrl 图标；address 地址；name 快递名称；expressNum 未领快递数；" +
            "communityCode 小区code;id 快递地址id；createUserName 创建人名称,readStatus 是否已读")
    public Result listExpressAddress(String cellphone, String communityCode, Integer pageNum, Integer pageSize) {
        if (StringUtils.isNotBlank(cellphone) && StringUtils.isNotBlank(communityCode)) {
            User user = (User) redisService.get(RedisConstant.USER + cellphone);
            if (user != null) {
                Page<ExpressAddress> page = expressAddressService.listByCommunityCodePage(user.getId(), communityCode, pageNum, pageSize);
                userTrackService.addUserTrack(cellphone, "查看我的快递", "未领取快递信息查看成功");
                return Result.success(page);
            }
            return Result.error("请登录");
        }
        return Result.error("参数不能为空");
    }

    /**
     * 查询快递详情信息
     * @param cellphone        手机号
     * @param expressAddressId 快递地址id
     * @return result
     * @author Mr.Deng
     * @date 17:55 2018/12/17
     */
    @GetMapping("/listExpressInfo")
    @ApiOperation(value = "查询快递详情信息", notes = "输出参数：userId 用户id，expressAddressId 快递地址id；waybillNum 订单编号" +
            "receiveStatus 领取状态1、已领取2、未领取；receiveTime 领取时间；receiver 领取人；receiverPhone 领取人手机号；" +
            "createUserName 创建人")
    public Result listExpressInfo(String cellphone, Integer expressAddressId, Integer pageNum, Integer pageSize) {
        if (StringUtils.isNotBlank(cellphone) && expressAddressId != null) {
            User user = (User) redisService.get(RedisConstant.USER + cellphone);
            Page<ExpressInfo> page = expressInfoService.listExpressInfoPage(user.getId(), expressAddressId, pageNum, pageSize);
            List<ExpressInfo> expressInfos = page.getRecords();
            for (ExpressInfo expressInfo : expressInfos) {
                ExpressReadUser expressReadUser1 = expressReadUserService.ByUserIdAndExpressInfoId(user.getId(), expressInfo.getId());
                if (expressReadUser1 == null) {
                    //添加已读
                    ExpressReadUser expressReadUser = new ExpressReadUser(user.getId(), expressInfo.getId(), expressInfo.getExpressAddressId());
                    expressReadUserService.save(expressReadUser);
                }
            }
            //记录足迹
            userTrackService.addUserTrack(cellphone, "查看快递详情", "查看快递详情成功");
            return Result.success(page);
        }
        return Result.success("参数不能为空");
    }

    /**
     * 查询所有的失物招领信息
     * @param cellphone 手机号
     * @return result
     * @author Mr.Deng
     * @date 10:12 2018/12/18
     */
    @GetMapping("/listLostFount")
    @ApiOperation(value = "查询所有的失物招领信息", notes = "返回参数：readStatus 是否已读，id 失物招领id，" +
            "title 标题,imgUrl 图片url,receiverAddress 领取地址,receiverStatus 领取状态（1、未领取；2、已领取）")
    public Result listLostFount(String cellphone, String communityCode, Integer pageNum, Integer pageSize) {
        if (StringUtils.isNotBlank(cellphone) && StringUtils.isNotBlank(communityCode)) {
            User user = (User) redisService.get(RedisConstant.USER + cellphone);
            if (user != null) {
                Page<LostFound> page = lostFoundService.listPage(user.getId(),
                        communityCode, pageNum, pageSize);
                return Result.success(page);
            }
            return Result.error("请登录");
        }
        return Result.error("参数不能为空");
    }

    /**
     * 查询失物招领详情
     * @param cellphone   手机号
     * @param lostFountId 失物招领id
     * @return result
     * @author Mr.Deng
     * @date 10:24 2018/12/18
     */
    @GetMapping("/getLostFountInfo")
    @ApiOperation(value = "查询失物招领详情", notes = "输出参数：id 失物招领id；issuer 发布人；issuer_phone 发布人电话；" +
            "title 标题,imgUrl 图片url,receiverAddress 领取地址,receiverStatus 领取状态（1、未领取；2、已领取）")
    public Result getLostFountInfo(String cellphone, Integer lostFountId) {
        if (lostFountId != null && StringUtils.isNotBlank(cellphone)) {
            User user = (User) redisService.get(RedisConstant.USER + cellphone);
            if (user != null) {
                LostFound lostFount = lostFoundService.getLostFountInfo(lostFountId);
                if (lostFount != null) {
                    //标记已读
                    LostFountReadUser lostFountReadUser1 = new LostFountReadUser(user.getId(), lostFountId);
                    lostFountReadUserService.save(lostFountReadUser1);
                    //添加足迹
                    userTrackService.addUserTrack(cellphone, "查询失物招领", "查询" + lostFount.getTitle() + "成功");
                    return Result.success(lostFount);
                }
                return Result.error("失物招领不存在");
            }
            return Result.error("请登录");
        }
        return Result.error("参数不能为空");
    }

    /**
     * 查询所有的促销信息
     * @param cellphone     手机号
     * @param communityCode 小区code
     * @return result
     * @author Mr.Deng
     * @date 17:42 2018/12/18
     */
    @GetMapping("/listPromotion")
    @ApiOperation(value = "查询所有的促销信息", notes = "返回参数：promotionType 促销类型，关联数据字典code promotion_type；" +
            " title 标题；imgUrl封面url ;issuer 发布者；issuerPhone 发布者手机号；promotionAddress 促销地点；issueTime 发布时间" +
            " discount 折扣;activityContent 活动内容；startTime 活动开始时间；endTime 活动结束时间；communityCode 小区code；" +
            " promotionStatus 促销状态；content 促销详情；id 促销id ；readStatus 已读状态")
    public Result listPromotion(String cellphone, String communityCode, Integer pageNum, Integer pageSize) {
        if (StringUtils.isNotBlank(cellphone) && StringUtils.isNotBlank(communityCode)) {
            User user = (User) redisService.get(RedisConstant.USER + cellphone);
            if (user != null) {
                Page<Promotion> page = promotionService.listPage(user.getId(), communityCode, pageNum, pageSize);
                return Result.success(page);
            }
            return Result.error("请登录");
        }
        return Result.error("参数不能为空");
    }

    /**
     * 查询促销详情信息,通过促销信息id
     * @param cellphone   手机号
     * @param promotionId 促销id
     * @return result
     * @author Mr.Deng
     * @date 17:54 2018/12/18
     */
    @GetMapping("/getPromotionInfo")
    @ApiOperation(value = "查询促销详情信息,通过促销信息id", notes = "输出参数：promotionType 促销类型，关联数据字典code promotion_type；" +
            "title 标题；imgUrl封面url ;issuer 发布者；issuerPhone 发布者手机号；promotionAddress 促销地点；issueTime 发布时间" +
            "discount 折扣;activityContent 活动内容；startTime 活动开始时间；endTime 活动结束时间；communityCode 小区code；" +
            "promotionStatus 促销状态；content 促销详情；id 促销id ；readNum 浏览量")
    public Result getPromotionInfo(String cellphone, Integer promotionId) {
        if (StringUtils.isNotBlank(cellphone) && promotionId != null) {
            User user = (User) redisService.get(RedisConstant.USER + cellphone);
            if (user != null) {
                Promotion promotion = promotionService.getPromotionInfo(promotionId);
                if (promotion != null) {
                    //添加已读
                    PromotionReadUser promotionReadUser = new PromotionReadUser(user.getId(), promotionId);
                    promotionReadUserService.save(promotionReadUser);
                    //添加足迹
                    userTrackService.addUserTrack(cellphone, "查询促销信息", "促销信息" + promotion.getTitle() + "查询成功");
                    return Result.success(promotion);
                }
                return Result.error("促销活动不存在");
            }
            return Result.error("请登录");
        }
        return Result.error("参数不能为空");
    }

    /**
     * 查询所有老人体检信息，通过小区code
     * @param cellphone     手机号
     * @param communityCode 小区code
     * @return result
     * @author Mr.Deng
     * @date 20:16 2018/12/18
     */
    @GetMapping("/listOldMedical")
    @ApiOperation(value = "查询所有老人体检信息，通过小区code", notes = "返回参数：communityCode小区code,title标题，issuer发布人，" +
            "issuerTime发布时间，contacts联系人，phone联系电话，address 登记地址，startTime 开始时间，endTime 结束时间，" +
            "oldMedicalStatus 活动状态，readStatus 已读状态")
    public Result listOldMedical(String cellphone, String communityCode, Integer pageNum, Integer pageSize) {
        if (StringUtils.isNotBlank(cellphone) && StringUtils.isNotBlank(communityCode)) {
            User user = (User) redisService.get(RedisConstant.USER + cellphone);
            if (user != null) {
                Page<OldMedical> page = oldMedicalService.listPageByCommunityCode(user.getId(), communityCode, pageNum, pageSize);
                return Result.success(page);
            }
            return Result.error("请登录");
        }
        return Result.error("参数不能为空");
    }

    /**
     * 查询老人体检详情信息，老人体检id
     * @param cellphone    手机号
     * @param oldMedicalId 老人体检id
     * @return result
     * @author Mr.Deng
     * @date 20:23 2018/12/18
     */
    @GetMapping("/getOldMedical")
    @ApiOperation(value = "查询老人体检详情信息，老人体检id", notes = "输入参数：oldMedicalId 老人体检id;<br/>" +
            "返回参数：communityCode小区code,title标题，issuer发布人，issuerTime发布时间，contacts联系人，phone联系电话，" +
            "address 登记地址，startTime 开始时间，endTime 结束时间，oldMedicalStatus 活动状态，content详情，readNum浏览量")
    public Result getOldMedical(String cellphone, Integer oldMedicalId) {
        if (StringUtils.isNotBlank(cellphone) && oldMedicalId != null) {
            User user = (User) redisService.get(RedisConstant.USER + cellphone);
            if (user != null) {
                OldMedical oldMedical = oldMedicalService.getByOldMedicalId(oldMedicalId);
                if (oldMedical != null) {
                    //添加已读
                    OldMedicalReadUser oldMedicalReadUser = new OldMedicalReadUser(user.getId(), oldMedicalId);
                    oldMedicalReadUserService.save(oldMedicalReadUser);
                    //记录足迹
                    userTrackService.addUserTrack(cellphone, "查看老人体检", "查询老人体检-" + oldMedical.getTitle() + "成功");
                    return Result.success(oldMedical);
                }
                return Result.error("该老人体检活动不存在");
            }
        }
        return Result.error("参数不能为空");
    }

    /**
     * 我的足迹
     * @param cellphone 手机号
     * @returnc result
     * @author Mr.Deng
     * @date 11:33 2018/12/19
     */
    @GetMapping("/listUserTrackInfo")
    @ApiOperation(value = "我的足迹", notes = "输出参数：userId用户id，title 标题，content 内容，gmtVisit 访问时间")
    public Result listUserTrackInfo(String cellphone, String startTime, String endTime) {
        if (StringUtils.isNotBlank(cellphone)) {
            User user = (User) redisService.get(RedisConstant.USER + cellphone);
            if (user != null) {
                List<UserTrack> userTracks = userTrackService.listByUserId(user.getId(), startTime + "00:00:00",
                        endTime + "00:00:00");
                return Result.success(userTracks);
            }
            return Result.error("请登录");
        }
        return Result.error("参数不能为空");
    }

    /**
     * 查询所有系统消息
     * @param cellphone 手机号
     * @return result
     * @author Mr.Deng
     * @date 11:23 2018/12/19
     */
    @GetMapping("/listSysMessages")
    @ApiOperation(value = "查询所有系统消息", notes = "返回参数：userId 用户id；title 标题；details 详情；addTime 添加时间")
    public Result listSysMessages(String cellphone, Integer pageNum, Integer pageSize) {
        if (StringUtils.isNotBlank(cellphone)) {
            User user = (User) redisService.get(RedisConstant.USER + cellphone);
            if (user != null) {
                Page<SysMessages> page = sysMessagesService.listByUserIdPage(user.getId(), pageNum, pageSize);
                List<SysMessages> sysMessages = page.getRecords();
                // 记录系统消息已读
                List<Integer> list = sysMessages.parallelStream().map(SysMessages::getId).collect(Collectors.toList());
                sysMessageReadService.saveNotRead(user.getId(), list);
                return Result.success(page);
            }
            return Result.error("请登录");
        }
        return Result.error("参数不能为空");
    }

    /**
     * 统计未读数量
     * @param cellphone
     * @return com.mit.community.util.Result
     * @throws
     * @author shuyy
     * @date 2018/12/29 10:17
     * @company mitesofor
     */
    @GetMapping("/countNotReadNum")
    @ApiOperation(value = "统计未读系统消息", notes = "传参：cellphone 电话")
    public Result countNotReadNum(String cellphone) {
        if (StringUtils.isNotBlank(cellphone)) {
            User user = (User) redisService.get(RedisConstant.USER + cellphone);
            if (user != null) {
                Integer num = sysMessagesService.countNotReadNum(user.getId());
                return Result.success(num);
            }
            return Result.error("请登录");
        }
        return Result.error("参数不能为空");
    }

    /**
     * 查询所有的精品活动信息
     * @return result
     * @author Mr.Deng
     * @date 14:13 2018/12/22
     */
    @GetMapping("/listSelectionActivities")
    @ApiOperation(value = "查询所有的精品活动信息", notes = "输出参数：title 标题,introduce 活动介绍,externalUrl 外部URL," +
            "validTime 有效时间,issueTime 发布时间,issuer 发布人，readNum 浏览量，image 图片地址，notes 备注")
    public Result listSelectionActivities() {
        List<SelectionActivities> list = selectionActivitiesService.list();
        return Result.success(list);
    }

    /**
     * 查询精品活动详情，通过精品活动id
     * @param selectionActivitiesId 精品活动id
     * @return result
     * @author Mr.Deng
     * @date 14:29 2018/12/22
     */
    @GetMapping("/getBySelectionActivitiesId")
    @ApiOperation(value = "查询精品活动详情，通过精品活动id", notes = "输入参数：selectionActivitiesId 精品活动id<br/>" +
            "输出参数：title 标题,introduce 活动介绍,externalUrl 外部URL," +
            "validTime 有效时间,issueTime 发布时间,issuer 发布人，readNum 浏览量，image 图片地址，notes 备注，content 信息详情")
    public Result getBySelectionActivitiesId(Integer selectionActivitiesId) {
        SelectionActivities selectionActivities = selectionActivitiesService.getBySelectionActivitiesId(selectionActivitiesId);
        if (selectionActivities != null) {
            //记录浏览量
            selectionActivitiesService.AddSelectionActivitiesReadNum(selectionActivities);
        }
        return Result.success(selectionActivities);
    }

    /**
     * 我的-统计数据
     * @param cellphone     手机号
     * @param communityCode 小区code
     * @author Mr.Deng
     * @date 10:41 2018/12/24
     */
    @GetMapping("/getTotal")
    @ApiOperation(value = "我的-统计数据", notes = "输入参数：cellphone 手机号；communityCode小区code")
    public Result getTotal(String cellphone, String communityCode) {
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(4);
        if (StringUtils.isNotBlank(cellphone) && StringUtils.isNotBlank(communityCode)) {
            int myKeyNum = 0;
            Integer reportThingsRepairNum = reportThingsRepairService.countReportThingsRepair(cellphone, communityCode);
            User user = (User) redisService.get(RedisConstant.USER + cellphone);
            Integer handlingNum = businessHandlingService.countByCellphoneAndCommunityCode(user.getId(), communityCode);
            Integer accessControlNum = accessControlService.countByCellphoneAndCommunityCode(cellphone, communityCode);
            List<MyKey> myKey = dnakeAppApiService.getMyKey(cellphone, communityCode);
            if (!myKey.isEmpty()) {
                myKeyNum = myKey.size();
            }
            map.put("myKeyNum", myKeyNum);
            map.put("reportThingsRepairNum", reportThingsRepairNum);
            map.put("handlingNum", handlingNum);
            map.put("accessControlNum", accessControlNum);
            return Result.success(map);
        }
        return Result.error("参数不能为空");
    }

}
