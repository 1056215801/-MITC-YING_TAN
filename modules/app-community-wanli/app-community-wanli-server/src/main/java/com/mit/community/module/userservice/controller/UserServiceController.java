package com.mit.community.module.userservice.controller;

import com.google.common.collect.Lists;
import com.mit.community.entity.*;
import com.mit.community.module.system.service.DictionaryService;
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

import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    @Autowired
    public UserServiceController(ReportThingsRepairService reportThingsRepairService,
                                 CommunityServiceInfoService communityServiceInfoService,
                                 BusinessHandlingService businessHandlingService,
                                 CommunityPhoneService communityPhoneService, YellowPagesService yellowPagesService,
                                 FeedBackService feedBackService, UserTrackService userTrackService,
                                 DictionaryService dictionaryService) {
        this.reportThingsRepairService = reportThingsRepairService;
        this.communityServiceInfoService = communityServiceInfoService;
        this.businessHandlingService = businessHandlingService;
        this.communityPhoneService = communityPhoneService;
        this.yellowPagesService = yellowPagesService;
        this.feedBackService = feedBackService;
        this.userTrackService = userTrackService;
        this.dictionaryService = dictionaryService;
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
            "creatorUserId 创建用户id；images 图片（可不传）")
    public Result applyReportThingsRepair(String communityCode, String cellphone, Integer roomId, String roomNum, String content,
                                          String reportUser, String reportCellphone, String maintainType,
                                          Integer creatorUserId, MultipartFile[] images) throws Exception {
        //上传图片路径
        List<String> imageUrls = Lists.newArrayListWithExpectedSize(5);
        if (images != null) {
            for (MultipartFile image : images) {
                String imageUrl = Objects.requireNonNull(FastDFSClient.getInstance()).uploadFile(image);
                imageUrls.add(imageUrl);
            }
        }
        reportThingsRepairService.applyReportThingsRepair(communityCode, cellphone, roomId, roomNum, content,
                reportUser, reportCellphone, maintainType, creatorUserId, imageUrls);
        //记录足迹
        Dictionary dictionary = dictionaryService.getByCode(maintainType);
        if (dictionary != null) {
            userTrackService.addUserTrack(cellphone, "申请报事报修", "维修类型" + dictionary.getName() + "申请报事报修成功");
        }
        return Result.success("申请成功");
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
    public Result listReportThingsRepairByStatus(String cellphone, Integer status) {
        if (StringUtils.isNotBlank(cellphone) && status != null) {
            List<ReportThingsRepair> reportThingsRepairs = reportThingsRepairService.listReportThingsRepairByStatus(cellphone, status);
            String st = (status == 0) ? "未完成" : "已完成";
            userTrackService.addUserTrack(cellphone, "查询报事报修", "查询状态" + st + "报事报修成功");
            return Result.success(reportThingsRepairs);
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
    public Result listBusinessHandlingByStatus(String cellphone, Integer creatorUserId, Integer status) {
        if (creatorUserId != null && status != null && StringUtils.isNotBlank(cellphone)) {
            List<BusinessHandling> listBusinessHandling = businessHandlingService.listByStatus(creatorUserId, status);
            String st = (status == 0) ? "未完成" : "已完成";
            userTrackService.addUserTrack(cellphone, "查询业务办理数据", "查询办理状态" + st + "业务数据成功");
            return Result.success(listBusinessHandling);
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
        List<Object> allYellowPages = yellowPagesService.listAllYellowPages();
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
    @ApiOperation(value = "查询生活黄页信息，通过黄页类型id", notes = "输入参数：cellphone  手机号；yellowPagesTypeId 黄页菜单id" +
            "(传入参数查询某一个子菜单的号码，不传入查询所有菜单号码)")
    public Result listByYellowPagesTypeId(String cellphone, Integer yellowPagesTypeId) {
        if (StringUtils.isNotBlank(cellphone)) {
            if (null != yellowPagesTypeId) {
                Map<String, Object> map = yellowPagesService.mapToPhoneByYellowPagesTypeId(yellowPagesTypeId);
                if (!map.isEmpty()) {
                    userTrackService.addUserTrack(cellphone, "查询生活黄页", "查询生活黄页" + map.get("submenuName") + "成功");
                }
                return Result.success(map);
            } else {
                List<Map<String, Object>> list = yellowPagesService.listToPhone();
                if (!list.isEmpty()) {
                    userTrackService.addUserTrack(cellphone, "查询生活黄页", "查询全部生活黄页成功");
                }
                return Result.success(list);
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
}
