package com.mit.community.module.pass.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.community.constants.RedisConstant;
import com.mit.community.entity.*;
import com.mit.community.feigin.PassThroughFeign;
import com.mit.community.population.service.PersonBaseInfoService;
import com.mit.community.population.service.PersonLabelsService;
import com.mit.community.service.*;
import com.mit.community.util.CookieUtils;
import com.mit.community.util.Result;
import com.mit.community.util.ThreadPoolUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.mit.community.util.Utils.inputStream2String;

/**
 * 住户-通行
 *
 * @author shuyy
 * @date 2018/12/14 15:18
 * @company mitesofor
 */
@RestController
@RequestMapping(value = "/passThrough")
@Slf4j
@Api(tags = "住户-通行模块接口")
public class PassThroughController {


    private final ApplyKeyService applyKeyService;

    private final RedisService redisService;

    private final AppUserService appUserService;

    private final UserService userService;

    private final DnakeAppApiService dnakeAppApiService;

    private final PassThroughFeign passThroughFeign;

    private final HouseHoldService houseHoldService;

    @Autowired
    private PersonLabelsService personLabelsService;
    @Autowired
    private PersonBaseInfoService personBaseInfoService;
    @Autowired
    private DeviceDeviceGroupService deviceDeviceGroupService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private AccessCardService accessCardService;
    @Autowired
    private AccessControlService accessControlService;
    @Autowired
    private HouseHoldPhotoService houseHoldPhotoService;

    @Autowired
    public PassThroughController(ApplyKeyService applyKeyService,
                                 RedisService redisService,
                                 UserService userService,
                                 AppUserService appUserService,
                                 DnakeAppApiService dnakeAppApiService,
                                 PassThroughFeign passThroughFeign,
                                 HouseHoldService houseHoldService) {
        this.applyKeyService = applyKeyService;
        this.redisService = redisService;
        this.appUserService = appUserService;
        this.userService = userService;
        this.dnakeAppApiService = dnakeAppApiService;
        this.passThroughFeign = passThroughFeign;
        this.houseHoldService = houseHoldService;
    }

    /**
     * 查询设备列表
     *
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2019-01-23 13:57
     * @company mitesofor
     */
    @GetMapping("/listDeviceGroup")
    @ApiOperation(value = "设备组列表")
    public Result listDeviceGroup(HttpServletRequest request) {
        String sessionId = CookieUtils.getSessionId(request);
        SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        Result result = null;
        try {
            result = passThroughFeign.getDeviceGroup(sysUser.getCommunityCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 审批钥匙
     *
     * @param request           request
     * @param applyKeyId        申请钥匙记录id
     * @param residenceTime     过期时间
     * @param deviceGroupIdList 设备组id列表
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/19 10:02
     * @company mitesofor
     */
    @PostMapping("/approveKey")
    @ApiOperation(value = "审批钥匙", notes = "传参：applyKeyId 申请钥匙id，residenceTime 居住有效期限," +
            " deviceGroupIdList 设备组id列表")
    public Result approveKey(HttpServletRequest request, Integer applyKeyId,
                             @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate residenceTime,
                             @RequestParam("deviceGroupIdList[]") List<String> deviceGroupIdList) {
        // 更新申请钥匙记录
        String sessionId = CookieUtils.getSessionId(request);
        SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        String checkPerson = sysUser.getAdminName();
        String status = applyKeyService.approval(applyKeyId, checkPerson, residenceTime,
                deviceGroupIdList);
        if (!"success".equals(status)) {
            return Result.error(status);
        }
        ThreadPoolUtil.execute(new Thread(() -> {
            ApplyKey applyKey = applyKeyService.selectById(applyKeyId);
            Integer creatorUserId = applyKey.getCreatorUserId();
            User user = userService.getById(creatorUserId);
            passThroughFeign.hoseholdUpdate(user.getCellphone());
        }));
        return Result.success("审批成功");
    }

    /**
     * 审批钥匙
     *
     * @param applyKeyId 申请钥匙记录id
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/19 10:02
     * @company mitesofor
     */
    @PostMapping("/refuseKey")
    @ApiOperation(value = "拒绝审批钥匙", notes = "传参：applyKeyId 申请钥匙id")
    public Result refuseKey(Integer applyKeyId) {
        // 更新申请钥匙记录
        ApplyKey applyKey = new ApplyKey();
        applyKey.setId(applyKeyId);
        applyKey.setStatus(3);
        applyKeyService.update(applyKey);
        return Result.success("拒绝审批成功");
    }

    /**
     * 分页查询申请钥匙信息
     *
     * @param zoneId           分区id
     * @param buildingId       楼栋id
     * @param unitId           单元id
     * @param roomId           房间id
     * @param contactPerson    联系人
     * @param contactCellphone 联系人电话
     * @param status           状态
     * @param pageNum          当前页
     * @param pageSize         分页大小
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2018/12/14 16:36
     * @company mitesofor
     */
    @GetMapping("/listApplyKeyPage")
    @ApiOperation(value = "分页查询申请钥匙数据", notes = "输入参数：为空则不作为过滤条件。<br/>" +
            "zoneId 分区id，buildingId 楼栋id, unitId 单元id, roomId 房间id, contactPerson 联系人," +
            " contactCellphone 联系人手机号；status 1、申请中，2、审批通过； pageNum 当前页； pageSize 分页大小")
    public Result listApplyKeyPage(HttpServletRequest request, Integer zoneId,
                                   String communityCode, Integer buildingId, Integer unitId,
                                   Integer roomId, String contactPerson, String contactCellphone, Integer status,
                                   @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime gmtCreateStart,
                                   @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime gmtCreateEnd, Integer pageNum, Integer pageSize) {
        if (StringUtils.isBlank(communityCode)) {
            String sessionId = CookieUtils.getSessionId(request);
            SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
            communityCode = sysUser.getCommunityCode();
        }
        Page<ApplyKey> page = applyKeyService.listByPage(null, communityCode, zoneId, buildingId, unitId, roomId, contactPerson, contactCellphone, status, gmtCreateStart, gmtCreateEnd, pageNum, pageSize);
        return Result.success(page);
    }

    /**
     * 分页查询住户信息
     *
     * @param request
     * @param zoneId
     * @param communityCode
     * @param buildingId
     * @param unitId
     * @param roomId
     * @param contactPerson
     * @param contactCellphone
     * @param status
     * @param search_validEndFlag
     * @param select_autyType
     * @return
     */
    @GetMapping("/listHouseholdByCommunityCode")
    @ApiOperation(value = "分页查询住户信息数据", notes = "输入参数：为空则不作为过滤条件。<br/>" +
            "zoneId 分区id，buildingId 楼栋id, unitId 单元id, roomId 房间id, contactPerson 联系人," +
            " contactCellphone 联系人手机号；householdType 与户主关系（1：本人；2：配偶；3：父母；4：子女；5：亲属；6：非亲属；7：租赁；8：其他；9：保姆；10：护理人员); status 0-注销，1-正常；search_validEndFlag 有效期字段(1-即将到期，2-已过期)；select_autyType 授权类型(未授权：0;卡：1;app：10;人脸：100)；" +
            "pageNum 当前页； pageSize 分页大小")
    public Result listHouseholdByCommunityCode(HttpServletRequest request,
                                               Integer zoneId,
                                               String communityCode,
                                               Integer buildingId,
                                               Integer unitId,
                                               Integer roomId,
                                               String contactPerson,
                                               String contactCellphone,
                                               Integer householdType,
                                               Integer status,
                                               Integer search_validEndFlag,//有效期标识：1-即将到期，2-已过期
                                               Integer select_autyType,//授权类型（二进制相加而来，查询时后台需要拆分）
                                               Integer pageNum, Integer pageSize) {
        //逻辑代码
        if (StringUtils.isBlank(communityCode)) {
            String sessionId = CookieUtils.getSessionId(request);
            SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
            communityCode = sysUser.getCommunityCode();
        }
        //分页查询
        Page<HouseHold> page = houseHoldService.listHouseholdByPage(request, zoneId, communityCode, buildingId, unitId, roomId, contactPerson, contactCellphone, householdType, status,
                search_validEndFlag, select_autyType, pageNum, pageSize);
        List<HouseHold> list = page.getRecords();
        if (!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                if (StringUtils.isNotBlank(list.get(i).getMobile())) {
                    //String rkcf = personLabelsService.getRkcfByIdNum(list.get(i).getCredentialNum());
                    String rkcf = personLabelsService.getRkcfByMobile(list.get(i).getMobile(),communityCode);
                    if (org.apache.commons.lang.StringUtils.isNotBlank(rkcf)) {
                        if ("1".equals(rkcf)) {
                            list.get(i).setRkcf("户籍人口");
                        } else if ("2".equals(rkcf)) {
                            list.get(i).setRkcf("流动人口");
                        }
                    }
                   else {
                        list.get(i).setRkcf("未录入");
                    }
                    //String label = personBaseInfoService.getLabelsByCredentialNum(list.get(i).getCredentialNum());
                    String label = personBaseInfoService.getLabelsByMobile(list.get(i).getMobile(), communityCode);
                    if(StringUtils.isNotBlank(label)) {
                        list.get(i).setLabels(label);
                    }
                }
            }
            page.setRecords(list);
        }
        return Result.success(page);
    }

    /**
     * 保存住户房屋信息
     *
     * @param request
     * @param response
     * @param jsonObject
     * @return
     */
    @PostMapping(value = "/saveHouseholdInfoByStepOne")
    @ApiOperation(value = "保存住户房屋信息", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "houseHoldId", value = "住户id", paramType = "query", required = false, dataType = "Integer"),
            @ApiImplicitParam(name = "houseHoldName", value = "住户姓名", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "gender", value = "性别（0男，1女）", paramType = "query", required = true, dataType = "INTEGER"),
            @ApiImplicitParam(name = "residenceTime", value = "居住期限(yyyy-MM-dd)", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "mobile", value = "电话号码", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "idCard", value = "身份证号码", paramType = "query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "householdType", value = "与户主关系", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "mobileBelong", value = "电话号码归属(1本人，2紧急联系人)", paramType = "query", required = true, dataType = "INTEGER"),
    })
    public Result SaveHouseholdInfoByStepOne(HttpServletRequest request,
                                              HttpServletResponse response) {
        InputStream in = null;
        try {
            // 获取请求的流信息
            in = request.getInputStream();
            String params = inputStream2String(in, "");
            System.out.println("================stepOne="+params);
            net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(params);
            PostHouseHoldInfoOne postHouseHoldInfoOne = (PostHouseHoldInfoOne)net.sf.json.JSONObject.toBean(jsonObject, PostHouseHoldInfoOne.class);
            JSONArray array = jsonObject.getJSONArray("houseRoomsVoList");
            List<HouseRoomsVo> list = new ArrayList<>();
            HouseRoomsVo houseRoomsVo = null;
            for (int i=0; i<array.size(); i++) {
                net.sf.json.JSONObject data = (net.sf.json.JSONObject) array.get(i);
                houseRoomsVo = new HouseRoomsVo();
                houseRoomsVo.setHouseholdType(data.getString("householdType"));
                houseRoomsVo.setZoneId(data.getString("zoneId"));
                houseRoomsVo.setZoneName(data.getString("zoneName"));
                houseRoomsVo.setBuildingId(data.getString("buildingId"));
                houseRoomsVo.setBuildingName(data.getString("buildingName"));
                houseRoomsVo.setUnitId(data.getString("unitId"));
                houseRoomsVo.setUnitName(data.getString("unitName"));
                houseRoomsVo.setRoomId(data.getString("roomId"));
                houseRoomsVo.setRoomNum(data.getString("roomNum"));
                list.add(houseRoomsVo);
            }

            String communityCode = null;
            if (StringUtils.isBlank(communityCode)) {
                String sessionId = CookieUtils.getSessionId(request);
                SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
                communityCode = sysUser.getCommunityCode();
            }
            Integer msg = houseHoldService.SaveHouseholdInfoByStepOne(communityCode, postHouseHoldInfoOne, list);
            if (msg == -1){
                return Result.error("保存失败");
            } else {
                return Result.success(msg);
            }
        } catch (Exception e){
            e.printStackTrace();
            return Result.error("保存失败");
        }
    }


    @ApiOperation(value = "保存人脸照片")
    @PostMapping("/saveHouseHoldPhoto")
    public Result savePhoto(MultipartFile image, Integer houseHoldId) {
        String message = null;
        if (image != null) {
            message = houseHoldService.saveHouseHoldPhoto(image, houseHoldId);
            if ("提取人脸特征值失败".equals(message)) {
                return Result.error("提取人脸特征值失败");
            } else {
                return Result.success(message);
            }
        } else {
            Result.error("图片不能为空");
        }
        return Result.success(message);
    }


    /**
     * 保存住户授权信息
     *
     * @param householdId
     * @param appAuthFlag
     * @param directCall
     * @param tellNum
     * @param fileNames
     * @param faceAuthFlag
     * @param deviceGIds
     * @param validityEndDate
     * @param initValidityEndDate
     * @param csReturn
     * @return
     */
    @RequestMapping(value = "/saveHouseholdInfoByStepThree", method = RequestMethod.POST)
    @ApiOperation(value = "保存住户授权信息",notes = "editFlag 是否修改（0新增，1修改）；householdId 住户id；appAuthFlag app授权（0停用，1启用）；faceAuthFlag 人脸（0停用，1启用）；deviceGIds 权限组id（多个用英文逗号拼接）；" +
            "validityEndDate 权限有效期(yyyy-MM-dd)； cardListArr 卡号（多个用英文逗号拼接）；imageUrls 图片链接（多个用英文逗号拼接）；String phone 呼叫转移号码")
    public Result SaveHouseholdInfoByStepThree(Integer editFlag,
                                                Integer householdId,
                                                Integer appAuthFlag,
                                                Integer faceAuthFlag,
                                                String deviceGIds,
                                                String validityEndDate,
                                                String cardListArr, String imageUrls,String phone) {

        if (imageUrls != null) {
            System.out.println("===========================imageUrls="+imageUrls);
        }
        String msg = houseHoldService.SaveHouseholdInfoByStepThree(editFlag, householdId, appAuthFlag,
                faceAuthFlag, deviceGIds, validityEndDate, cardListArr, imageUrls,phone);
        if (!msg.contains("success")) {
            return Result.error("错误");
        }
        return Result.success(msg);
    }

    @ApiOperation(value = "stepThree获取填充信息")
    @PostMapping("/getInfoThree")
    public Result getInfoThree(HttpServletRequest request, Integer houseHoldId) {
        String communityCode = null;
        if (StringUtils.isBlank(communityCode)) {
            String sessionId = CookieUtils.getSessionId(request);
            SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
            communityCode = sysUser.getCommunityCode();
        }
        StepThreeInfo stepThreeInfo = houseHoldService.getInfoThree(houseHoldId, communityCode);
        return Result.success(stepThreeInfo);
    }


    /**
     * 注销住户
     *
     * @param request
     * @param communityCode
     * @param ids
     * @return
     */
    /*@RequestMapping(value = "/logOut", method = RequestMethod.POST)
    @ApiOperation(value = "注销住户", notes = "参数：住户id数组")
    public Result LogOutHousehold(HttpServletRequest request, String communityCode, String ids) {
        if (StringUtils.isBlank(communityCode)) {
            String sessionId = CookieUtils.getSessionId(request);
            SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
            communityCode = sysUser.getCommunityCode();
        }
        String msg = houseHoldService.logOut(communityCode, ids);
        if (!msg.contains("success")) {
            return Result.error("删除失败");
        }
        return Result.success("删除成功");
    }*/

    /**
     * 停用住户
     *
     * @param request
     * @param communityCode
     * @param id
     * @return
     */
    @RequestMapping(value = "/stop", method = RequestMethod.POST)
    @ApiOperation(value = "停用住户", notes = "参数：住户id")
    public Integer Stop(HttpServletRequest request, String communityCode, Integer id) {
        if (StringUtils.isBlank(communityCode)) {
            String sessionId = CookieUtils.getSessionId(request);
            SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
            communityCode = sysUser.getCommunityCode();
        }
        String msg = houseHoldService.Stop(communityCode, id);
        if (!msg.contains("success")) {
            return -1;
        }
        return 1;
    }

    /**
     * @param communityCode 小区code
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2019-01-22 9:31
     * @company mitesofor
     */
    @ApiOperation(value = "查询分区信息，通过小区code")
    @GetMapping("/listZoneByCommunityCode")
    public Result listZoneByCommunityCode(String communityCode, HttpServletRequest request, HttpSession session) {
        Object hello = session.getAttribute("hello");
        System.out.println(hello);
        if (StringUtils.isBlank(communityCode)) {
            String sessionId = CookieUtils.getSessionId(request);
            SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
            communityCode = sysUser.getCommunityCode();
        }
        return passThroughFeign.listZoneByCommunityCode(communityCode);
    }

    /**
     * @param zoneId 分区id
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2019-01-22 9:31
     * @company mitesofor
     */
    @ApiOperation(value = "查询楼栋，通过分区id")
    @GetMapping("/listBuildingByZoneId")
    public Result listBuildingByZoneId(Integer zoneId) {
        return passThroughFeign.listBuildingByZoneId(zoneId);
    }


    /**
     * @param buildingId 楼栋id
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2019-01-22 9:35
     * @company mitesofor
     */
    @ApiOperation(value = "查询单元信息，通过楼栋id")
    @GetMapping("/listUnitByBuildingId")
    public Result listUnitByBuildingId(Integer buildingId) {
        return passThroughFeign.listUnitByBuildingId(buildingId);
    }

    /**
     * @param unitId 单元id
     * @return com.mit.community.util.Result
     * @author shuyy
     * @date 2019-01-22 9:36
     * @company mitesofor
     */
    @ApiOperation(value = "查询房间信息，通过单元id")
    @GetMapping("/listRoomByUnitId")
    public Result listRoomByUnitId(Integer unitId) {
        return passThroughFeign.listRoomByUnitId(unitId);
    }

    @ApiOperation(value = "查询设备组列表，通过社区编号")
    @GetMapping("/getDeviceGroupList")
    public Result getDeviceGroupList(HttpServletRequest request, String communityCode) {
        if (StringUtils.isBlank(communityCode)) {
            String sessionId = CookieUtils.getSessionId(request);
            SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
            communityCode = sysUser.getCommunityCode();
        }
        return houseHoldService.getDeviceGroupList(communityCode);
    }

    /**
     * 修改手机号码
     *
     * @param mobile
     * @param householdId
     * @return
     */
    @RequestMapping("/alterMobile")
    @ApiOperation(value = "修改手机号码")
    public Integer AlterMobile(String mobile, Integer householdId) {
        String msg = houseHoldService.AlterMobile(mobile, householdId);
        if (!msg.contains("success")) {
            return -1;
        }
        return 1;
    }

    @ApiOperation(value = "分页获取通行记录", notes = "interactiveType：开门方式（0：其他开门；1：刷卡开门；2：密码开门；3：APP开门；4：分机开门；5：二维码开门； 6：蓝牙开门；7：按钮开门；8：手机开门;9：人脸识别；10:固定密码；11：http开门；）")
    @PostMapping("/accessControlPage")
    public Result accessControlPage(HttpServletRequest request, String communityCode, String cardNum, String name, String zoneId, String buildingId, String unitId, Integer interactiveType, String deicveNum,
                                   String timeStart,
                                    String timeEnd, Integer pageNum, Integer pageSize) {
        if (StringUtils.isBlank(communityCode)) {
            String sessionId = CookieUtils.getSessionId(request);
            SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
            communityCode = sysUser.getCommunityCode();
        }
        Page<AccessControl> page = accessControlService.getAccessControlPage(communityCode, cardNum, name, zoneId, buildingId, unitId, interactiveType, deicveNum, timeStart, timeEnd, pageNum, pageSize);
        List<AccessControl> list = page.getRecords();
        if (!list.isEmpty()) {
            for (AccessControl accessControl : list) {
                accessControl.setSelfPhotoUrl(accessControl.getAccessImgUrl());
            }
            page.setRecords(list);
        }
        return Result.success(page);
    }

    @ApiOperation(value = "分页获取门禁卡", notes = "")
    @PostMapping("/menJinCardPage")
    public Result menJinCardPage(HttpServletRequest request, String communityCode, String cardNum, String mobile, Integer zoneId, Integer buildingId, Integer unitId, Integer roomeId, Integer cardType,
                                    Integer cardMedia,
                                    Integer authType, Integer pageNum, Integer pageSize) {
        if (StringUtils.isBlank(communityCode)) {
            String sessionId = CookieUtils.getSessionId(request);
            SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
            communityCode = sysUser.getCommunityCode();
        }
        Page<AccessCardPageInfo> page = accessCardService.getMenJinCardPage(communityCode, cardNum, mobile, zoneId, buildingId, unitId, roomeId, cardType,
                 cardMedia, authType, pageNum, pageSize);

        return Result.success(page);
    }

    @ApiOperation(value = "门禁挂失")
    @PostMapping("/reportLossCard")
    public Result reportLossCard (Integer houseHoldId, String cardNum) {
        return Result.success("");
    }

    @ApiOperation(value = "门禁解绑")
    @PostMapping("/unbundlingCard ")
    public Result unbundlingCard (Integer houseHoldId, String cardNum) {
        return Result.success("");
    }

    @ApiOperation(value = "门禁卡下发")
    @PostMapping("/sendCard")
    public Result sendCard(Integer id) {
        AccessCard accessCard = accessCardService.getById(id);
        DeviceIsOnline deviceIsOnline = personLabelsService.getIsOnline(accessCard.getDeviceNum());
        if (Integer.parseInt(deviceIsOnline.getTimeDiffi()) <= 10) {//设备在线
            if (houseHoldService.sendCardToDevice(deviceIsOnline.getIp(),accessCard.getCardNum())) {//下发成功
                accessCardService.updateUploadById(2,id);
            } else {
                return Result.error("下发失败");
            }
        } else {
            return Result.error("设备不在线");
        }
        return Result.success("下发成功");
    }

    @ApiOperation(value = "获取住户人脸信息下发情况信息",notes = "isUpload: 1、下发失败；2、下发成功")
    @PostMapping("/getHouseHoldPhotoInfo")
    public Result getHouseHoldPhotoInfo(HttpServletRequest request, String mobile, String name, Integer isUpload, Integer pageNum, Integer pageSize) {
        String communityCode = null;
        if (StringUtils.isBlank(communityCode)) {
            String sessionId = CookieUtils.getSessionId(request);
            SysUser sysUser = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
            communityCode = sysUser.getCommunityCode();
        }
        Page<HouseHoldPhotoInfo> page = houseHoldService.getHouseHoldPhotoInfo(communityCode, mobile, name, isUpload, pageNum, pageSize);
        return Result.success(page);
    }

    @ApiOperation(value = "人脸下发")
    @PostMapping("/sendFea")
    public Result sendFea(Integer id) {
        HouseHoldPhoto houseHoldPhoto = houseHoldPhotoService.getById(id);
        DeviceIsOnline deviceIsOnline = personLabelsService.getIsOnline(houseHoldPhoto.getDeviceNum());
        if (Integer.parseInt(deviceIsOnline.getTimeDiffi()) <= 10) {//设备在线
            if (houseHoldService.sendFeaToDevice(deviceIsOnline.getIp(),houseHoldPhoto.getFeaUrl(), houseHoldPhoto.getHouseHoldId())) {//下发成功
                houseHoldPhotoService.updateUploadById(2, id);
            } else {
                return Result.error("下发失败");
            }
        } else {
            return Result.error("设备不在线");
        }
        return Result.success("下发成功");
    }


}
