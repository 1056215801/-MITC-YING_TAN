package com.mit.community.module.userservice.controller;

import com.google.common.collect.Lists;
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

import java.util.List;
import java.util.Map;

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

    @Autowired
    private ReportThingsRepairService reportThingsRepairService;

    @Autowired
    private CommunityServiceInfoService communityServiceInfoService;

    @Autowired
    private BusinessHandlingService businessHandlingService;

    @Autowired
    private CommunityPhoneService communityPhoneService;

    @Autowired
    private YellowPagesService yellowPagesService;

    @Autowired
    private FeedBackService feedBackService;

    /**
     * 申请报事报修
     * @param communityCode 小区code
     * @param communityName 小区名
     * @param zoneId        分区id
     * @param zoneName      分区名
     * @param buildingId    楼栋id
     * @param buildingName  楼栋名
     * @param unitId        单元id
     * @param unitName      单元名
     * @param roomId        房间id
     * @param roomNum       房间号
     * @param householdId   用户id
     * @param content       报事内容
     * @param reportUser    报事人
     * @param cellphone     联系人
     * @param maintainType  维修类型1、水，2、电，3、可燃气，4、锁，5、其他
     * @param creatorUserId 创建用户id
     * @return result
     * @author Mr.Deng
     * @date 20:16 2018/12/3
     */
    @PostMapping(value = "/applyReportThingsRepair", produces = {"application/json"})
    @ApiOperation(value = "申请报事报修", notes = "输入参数：输入参数：communityCode 小区code;communityName 小区名；zoneId 分区id;" +
            " zoneName 分区名；buildingId 楼栋id ;buildingName 楼栋名；unitId 单元id；unitName 单元名；roomId 房间id;" +
            "roomNum 房间编号；householdId 住户id；content 报事内容；reportUser 报事人；cellphone 联系人；" +
            "maintainType 维修类型1、水，2、电，3、可燃气，4、锁，5、其他；creatorUserId 创建用户id；images 图片（可不传）")
    public Result applyReportThingsRepair(String communityCode, String communityName, Integer zoneId, String zoneName,
                                          Integer buildingId, String buildingName, Integer unitId, String unitName,
                                          Integer roomId, String roomNum, Integer householdId, String content,
                                          String reportUser, String cellphone, Integer maintainType,
                                          Integer creatorUserId, MultipartFile[] images) {
        //上传图片路径
        List<String> imageUrls = Lists.newArrayListWithExpectedSize(5);
        if (images != null) {
            for (MultipartFile image : images) {
                String imageUrl = updateImage(image);
                imageUrls.add(imageUrl);
            }
        }
        reportThingsRepairService.applyReportThingsRepair(communityCode, communityName, zoneId,
                zoneName, buildingId, buildingName, unitId, unitName, roomId, roomNum, householdId, content,
                reportUser, cellphone, maintainType, creatorUserId, imageUrls);
        return Result.success("申请成功");
    }

    /**
     * 查询报事报修状态数据，通过住户id
     * @param householdId 住户id
     * @param status      保修状态 0、未完成。1、已完成
     * @return result
     * @author Mr.Deng
     * @date 21:02 2018/12/3
     */
    @GetMapping("/listReportThingsRepairByStatus")
    @ApiOperation(value = "查询相应状态的报事报修数据", notes = "输入参数：householdId 住户id，status 0、未完成。1、已完成")
    public Result listReportThingsRepairByStatus(Integer householdId, Integer status) {
        if (householdId != null && status != null) {
            List<ReportThingsRepair> reportThingsRepairs = reportThingsRepairService.listByStatus(householdId, status);
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
    public Result evaluateReportThingsRepair(Integer applyReportId, Integer evaluateResponseSpeed, Integer evaluateResponseAttitude,
                                             Integer evaluateTotal, Integer evaluateServiceProfession, String evaluateContent) {
        if (applyReportId != null && evaluateResponseSpeed != null && evaluateResponseAttitude != null && evaluateTotal != null
                && evaluateServiceProfession != null && StringUtils.isNotBlank(evaluateContent)) {
            reportThingsRepairService.evaluateReportThingsRepair(applyReportId, evaluateResponseSpeed,
                    evaluateResponseAttitude, evaluateTotal, evaluateServiceProfession, evaluateContent);
            return Result.success("评价成功");
        }
        return Result.error("参数不能为空");
    }

    /**
     * 查询社区门诊信息，通过社区code
     * @param communityCode 小区code
     * @param type          社区服务类型1、社区门诊2、开锁换锁3、送水到家
     * @return result
     * @author Mr.Deng
     * @date 11:38 2018/12/5
     */
    @GetMapping("/listCommunityServiceInfoByCommunityCode")
    @ApiOperation(value = "查询社区门诊信息，通过小区code", notes = "输入参数：communityCode 小区code;" +
            "type 社区服务类型1、社区门诊2、开锁换锁3、送水到家 \n返回参数：name 名称；address 地址；cellphone 电话；" +
            "distance 距离；distance 坐标；image 图片地址；creatorUserId 创建用户id ")
    public Result listCommunityServiceInfoByCommunityCode(String communityCode, Integer type) {
        if (type != null && StringUtils.isNotBlank(communityCode)) {
            List<CommunityServiceInfo> communityClinics = communityServiceInfoService.listByCommunityCode(communityCode, type);
            return Result.success(communityClinics);
        }
        return Result.error("参数不能为空");
    }

    /**
     * 查询社区电话，通过小区code和电话类型
     * @param communityCode 小区code
     * @param type          社区电话类型1、物业电话；2、紧急电话
     * @return result
     * @author Mr.Deng
     * @date 16:01 2018/12/5
     */
    @GetMapping("/listCommunityPhoneByCommunityCodeAndType")
    @ApiOperation(value = "查询社区电话，通过小区code和电话类型", notes = "输入参数：communityCode 小区code" +
            "type 社区电话类型1、物业电话；2、紧急电话")
    public Result listCommunityPhoneByCommunityCodeAndType(String communityCode, Integer type) {
        if (type != null && StringUtils.isNotBlank(communityCode)) {
            List<CommunityPhone> communityPhones = communityPhoneService.listByCommunityCodeAndType(communityCode, type);
            return Result.success(communityPhones);
        }
        return Result.error("参数不能为空");
    }

    /**
     * 申请业务办理
     * @param communityCode    小区code
     * @param communityName    小区名称
     * @param zoneId           分区id
     * @param zoneName         分区名称
     * @param buildingId       楼栋id
     * @param buildingName     楼栋名
     * @param unitId           单元id
     * @param unitName         单元名
     * @param roomId           房间id
     * @param roomNum          房间号
     * @param contactPerson    申请人
     * @param contactCellphone 申请人电话
     * @param content          申请内容
     * @param type             业务类型1、入住证明。2、装修完工申请。3、大物件搬出申报。4、装修许可证。5、装修出入证。6、钥匙托管。7、业主卡。99、其他。
     * @param creatorUserId    创建人用户id
     * @return result
     * @author Mr.Deng
     * @date 14:31 2018/12/5
     */
    @PostMapping(value = "/applyBusinessHandling", produces = {"application/json"})
    @ApiOperation(value = "申请业务办理", notes = "输入参数：communityCode 小区code;communityName 小区名；zoneId 分区id;" +
            "zoneName 分区名；buildingId 楼栋id ;buildingName 楼栋名；unitId 单元id；unitName 单元名；roomId 房间id;" +
            "roomNum 房间编号；contactPerson 申请人；contactCellphone 申请人电话；content 申请内容\n" +
            "type 业务类型：1、入住证明。2、装修完工申请。3、大物件搬出申报。4、装修许可证。5、装修出入证。6、钥匙托管。7、业主卡。99、其他。;" +
            "creatorUserId    创建人用户id;images 图片（可传可不传）")
    public Result applyBusinessHandling(String communityCode, String communityName, Integer zoneId, String zoneName,
                                        Integer buildingId, String buildingName, Integer unitId, String unitName,
                                        Integer roomId, String roomNum, String contactPerson, String contactCellphone,
                                        String content, Integer type, Integer creatorUserId, MultipartFile[] images) {
        //上传图片地址列表
        List<String> imageUrls = Lists.newArrayListWithExpectedSize(5);
        if (images != null) {
            for (MultipartFile image : images) {
                String imageUrl = updateImage(image);
                imageUrls.add(imageUrl);
            }
        }
        businessHandlingService.applyBusinessHandling(communityCode, communityName, zoneId, zoneName, buildingId,
                buildingName, unitId, unitName, roomId, roomNum, contactPerson, contactCellphone, content, type,
                creatorUserId, imageUrls);
        return Result.success("申请提交成功");
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
    public Result listBusinessHandlingByStatus(Integer creatorUserId, Integer status) {
        if (creatorUserId != null && status != null) {
            List<BusinessHandling> listBusinessHandling = businessHandlingService.listByStatus(creatorUserId, status);
            return Result.success(listBusinessHandling);
        }
        return Result.error("参数不能为空");
    }

    /**
     * 业务办理评价
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
    @ApiOperation(value = "业务办理评价", notes = "输入参数:businessHandlingId 业务办理id；evaluateResponseSpeed  响应速度评价；" +
            "evaluateResponseAttitude  响应态度评价；evaluateTotal  总体评价；evaluateServiceProfession 服务专业度评价；" +
            "evaluateContent   评价内容；评价范围为0-5")
    public Result evaluateBusinessHandling(Integer businessHandlingId, Integer evaluateResponseSpeed,
                                           Integer evaluateResponseAttitude,
                                           Integer evaluateTotal, Integer evaluateServiceProfession, String evaluateContent) {
        if (businessHandlingId != null && evaluateResponseSpeed != null && evaluateResponseAttitude != null
                && evaluateTotal != null && evaluateServiceProfession != null && StringUtils.isNotBlank(evaluateContent)) {
            businessHandlingService.evaluateBusinessHandling(businessHandlingId, evaluateResponseSpeed,
                    evaluateResponseAttitude, evaluateTotal, evaluateServiceProfession, evaluateContent);
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
     * @param yellowPagesTypeId 黄页类型id
     * @return Result
     * @author Mr.Deng
     * @date 9:23 2018/12/6
     */
    @GetMapping("/listByYellowPagesTypeId")
    @ApiOperation(value = "查询生活黄页信息，通过黄页类型id", notes = "输入参数：yellowPagesTypeId 黄页类型id" +
            "(传入参数查询某一个子菜单的号码，不传入查询所有菜单号码)")
    public Result listByYellowPagesTypeId(Integer yellowPagesTypeId) {
        if (null != yellowPagesTypeId) {
            Map<String, Object> map = yellowPagesService.mapToPhoneByYellowPagesTypeId(yellowPagesTypeId);
            return Result.success(map);
        } else {
            List<Map<String, Object>> list = yellowPagesService.listToPhone();
            return Result.success(list);
        }
    }

    /**
     * 提交反馈意见
     * @param title   标题
     * @param content 反馈内容
     * @param type    类型。关联数据字典。code为feedback_type。1、APP功能反馈。2、物业/小区问题
     * @param userId  用户id。关联user表id
     * @param image   图片列表
     * @return result
     * @author Mr.Deng
     * @date 18:29 2018/12/6
     */
    @PostMapping(value = "/submitFeedBack", produces = {"application/json"})
    @ApiOperation(value = "提交反馈意见", notes = "输入参数：title 标题；content 反馈内容；" +
            "type 类型。关联数据字典。code为feedback_type。1、APP功能反馈。2、物业/小区问题;userId 用户id；image 图片列表")
    public Result submitFeedBack(String title, String content, Integer type, Integer userId, MultipartFile image) {
        if (StringUtils.isNotBlank(title) && StringUtils.isNotBlank(content) && type != null && userId != null) {
            List<String> imageUrls = Lists.newArrayListWithCapacity(5);
            if (null != image) {
                String imageUrl = updateImage(image);
                imageUrls.add(imageUrl);
            }
            feedBackService.submitFeedBack(title, content, type, userId, imageUrls);
            return Result.success("提交成功");
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
    private String updateImage(MultipartFile image) {
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
