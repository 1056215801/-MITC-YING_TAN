package com.mit.community.module.dnake.controller;

import com.mit.community.entity.*;
import com.mit.community.service.*;
import com.mit.community.util.HttpPostUtil;
import com.mit.community.util.Result;
import com.mit.community.util.UploadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

/**
 * @Author HuShanLin
 * @Date Created in 10:15 2019/6/20
 * @Company: mitesofor </p>
 * @Description:~人脸模块
 */
@RestController
@RequestMapping("/faceController")
@Slf4j
@Api(tags = "与门禁设备交互")
public class FaceController {

    @Autowired
    private DnakeDeviceInfoService dnakeDeviceInfoService;
    @Autowired
    private HouseHoldService houseHoldService;
    @Autowired
    private HouseholdRoomService householdRoomService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private AccessControlService accessControlService;
    @Autowired
    private AccessCardService accessCardService;
    @Autowired
    private VisitorInviteCodeService visitorInviteCodeService;
    @Autowired
    private DeviceDeviceGroupService deviceDeviceGroupService;
    @Autowired
    private ClusterCommunityService clusterCommunityService;
    @Autowired
    private ZoneService zoneService;
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private UnitService unitService;


    @RequestMapping("/uploadImg")
    @ApiOperation(value = "上传人脸比对开门记录", notes = "传参：")
    public void uploadImg(HttpServletRequest request, HttpServletResponse response, String photo, Integer houseHoldId, Integer uid, Integer data,
                          Integer black, String mac, String ip) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        //Base64字符串处理
        byte[] b = decoder.decodeBuffer(photo);
        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {//调整异常数据
                b[i] += 256;
            }
        }

        AccessControl accessControl = new AccessControl();
        HouseHold houseHold = houseHoldService.getByHouseholdId(houseHoldId);
        DnakeDeviceInfo dnakeDeviceInfo = dnakeDeviceInfoService.getDeviceInfoByMac(mac);
        Device device = deviceService.getByDnakeDeviceInfoId(dnakeDeviceInfo.getId());
        String communityCode = device.getCommunityCode();
        AccessCard accessCard = accessCardService.getByHouseHoidIdAndDeviceNum(houseHold.getHouseholdId(), device.getDeviceNum());
        HouseholdRoom householdRoom = householdRoomService.getByHouseHoldIdAndCommunityCodeAndBuilingIdAndUnitId(houseHold.getHouseholdId(),communityCode,device.getBuildingId(),device.getUnitId());
        ClusterCommunity clusterCommunity = clusterCommunityService.getByCommunityCode(communityCode);
        Zone zone = zoneService.getByZoneId(Integer.parseInt(device.getZoneId()));
        Building building = buildingService.getByBuidingId(Integer.parseInt(device.getBuildingId()));
        Unit unit = unitService.getByUnitId(Integer.parseInt(device.getUnitId()));
        String imageUrl = UploadUtil.uploadWithByte(b);//开门时抓拍的图片
        accessControl.setCommunityCode(communityCode);
        accessControl.setCommunityName(clusterCommunity.getCommunityName());
        accessControl.setAccessTime(LocalDateTime.now());
        accessControl.setInteractiveType(9);
        accessControl.setDeviceName(device.getDeviceName());
        accessControl.setDeviceNum(device.getDeviceNum());
        accessControl.setZoneId(String.valueOf(device.getZoneId()));
        accessControl.setZoneName(zone.getZoneName());
        accessControl.setHouseholdId(houseHold.getHouseholdId());
        accessControl.setHouseholdName(houseHold.getHouseholdName());
        accessControl.setHouseholdMobile(houseHold.getMobile());
        if (accessCard != null) {
            accessControl.setCardNum(accessCard.getCardNum());
        } else {
            accessControl.setCardNum("");
        }
        accessControl.setAccessImgUrl(imageUrl);
        accessControl.setBuildingCode(device.getBuildingCode());
        accessControl.setBuildingName(building.getBuildingName());
        accessControl.setUnitCode(device.getUnitCode());
        accessControl.setUnitName(unit.getUnitName());

        /*long time = System.currentTimeMillis();
        String timeStr = String.valueOf(time);
        accessControl.setAccessControlId(Integer.parseInt(timeStr.substring(timeStr.length()-12, timeStr.length())));//暂时不知*/

        accessControl.setGmtCreate(LocalDateTime.now());
        accessControl.setGmtModified(LocalDateTime.now());
        if (householdRoom != null) {
            accessControl.setRoomNum(householdRoom.getRoomNum());
        }
        accessControlService.insert(accessControl);
        //System.out.println(photo);
    }

    @RequestMapping("/xinTiao")
    @ApiOperation(value = "心跳", notes = "传参：") //没有表
    public void xinTiao(HttpServletRequest request, HttpServletResponse response, String mac, String ip) throws IOException {
        System.out.println("===============心跳="+mac+"="+ip);
        if(StringUtils.isNotBlank(mac) && StringUtils.isNotBlank(ip)){
            DnakeDeviceInfo dnakeDeviceInfo = dnakeDeviceInfoService.getDeviceInfoByMac(mac);
            if(dnakeDeviceInfo != null){
                dnakeDeviceInfoService.update(mac, ip);
            } else {
                dnakeDeviceInfoService.save(mac, ip);
            }
        }
    }

    @PostMapping("/appHttpOpenDoor")
    @ApiOperation(value = "手机开门", notes = "传参：") //需要捕捉连接异常，判断门禁机是否在线
    public Result appHttpOpenDoor(HttpServletRequest request, String cellphone, String communityCode, String deviceNum) throws Exception{
        AccessControl accessControl = new AccessControl();
        HouseHold houseHold = houseHoldService.getByCellphoneAndCommunityCode(cellphone,communityCode);
        if (houseHold.getHouseholdStatus() == 1) {
            if (houseHold.getAuthorizeStatus()== 2 || houseHold.getAuthorizeStatus()== 3 || houseHold.getAuthorizeStatus()== 6 || houseHold.getAuthorizeStatus()==7 ) {
                if (System.currentTimeMillis() <= houseHold.getValidityTime().getTime()) {
                    System.out.println("==============deviceNum="+deviceNum +",communityCode="+communityCode);
                    Device device = deviceService.getByDeviceNumAndCommunityCode(communityCode, deviceNum);
                    //HouseholdRoom householdRoom = householdRoomService.getByHouseHoldIdAndCommunityCodeAndBuilingIdAndUnitId(houseHold.getHouseholdId(),communityCode,device.getBuildingId(),device.getUnitId());
                    DnakeDeviceInfo dnakeDeviceInfo = dnakeDeviceInfoService.getById(device.getDnakeDeviceInfoId());
                    AccessCard accessCard = accessCardService.getByHouseHoidIdAndDeviceNum(houseHold.getHouseholdId(), device.getDeviceNum());
                    ClusterCommunity clusterCommunity = clusterCommunityService.getByCommunityCode(communityCode);
                    Zone zone = zoneService.getByZoneId(communityCode, Integer.parseInt(device.getZoneId()));
                    Building building = buildingService.getByBuidingId(Integer.parseInt(device.getBuildingId()));
                    Unit unit = unitService.getByUnitId(Integer.parseInt(device.getUnitId()));
                    String resultJson = HttpPostUtil.httpOpen(dnakeDeviceInfo.getIp(), cellphone, communityCode, deviceNum);
                    System.out.println("==============resultJson="+resultJson);
                    return Result.success("门已开");
                } else {
                    return Result.error("权限已过期");
                }
            } else {
                return Result.error("没有该权限");
            }
        } else {
            return Result.error("业主非启用状态");
        }
    }

    @PostMapping("/uploadHttpOpenRecord")
    @ApiOperation(value = "接收上传的http开门记录", notes = "传参：") //需要捕捉连接异常，判断门禁机是否在线
    @Transactional
    public Result uploadHttpOpenRecord (String cellphone, String communityCode, String deviceNum, String base64) throws Exception{
        AccessControl accessControl = new AccessControl();
        HouseHold houseHold = houseHoldService.getByCellphoneAndCommunityCode(cellphone,communityCode);
        Device device = deviceService.getByDeviceNumAndCommunityCode(communityCode, deviceNum);
        HouseholdRoom householdRoom = householdRoomService.getByHouseHoldIdAndCommunityCodeAndBuilingIdAndUnitId(houseHold.getHouseholdId(),communityCode,device.getBuildingId(),device.getUnitId());
        //DnakeDeviceInfo dnakeDeviceInfo = dnakeDeviceInfoService.getById(device.getDnakeDeviceInfoId());
        AccessCard accessCard = accessCardService.getByHouseHoidIdAndDeviceNum(houseHold.getHouseholdId(), device.getDeviceNum());
        ClusterCommunity clusterCommunity = clusterCommunityService.getByCommunityCode(communityCode);
        Zone zone = zoneService.getByZoneId(communityCode, Integer.parseInt(device.getZoneId()));
        Building building = buildingService.getByBuidingId(Integer.parseInt(device.getBuildingId()));
        Unit unit = unitService.getByUnitId(Integer.parseInt(device.getUnitId()));
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] b = decoder.decodeBuffer(base64);
        String imageUrl = UploadUtil.uploadWithByte(b);//开门时抓拍的图片
        accessControl.setCommunityCode(communityCode);
        accessControl.setCommunityName(clusterCommunity.getCommunityName());
        accessControl.setAccessTime(LocalDateTime.now());
        accessControl.setInteractiveType(11);
        accessControl.setDeviceName(device.getDeviceName());
        accessControl.setDeviceNum(deviceNum);
        accessControl.setZoneId(String.valueOf(device.getZoneId()));
        accessControl.setZoneName(zone.getZoneName());
        accessControl.setHouseholdId(houseHold.getHouseholdId());
        accessControl.setHouseholdName(houseHold.getHouseholdName());
        accessControl.setHouseholdMobile(houseHold.getMobile());
        if (accessCard != null) {
            accessControl.setCardNum(accessCard.getCardNum());
        } else {
            accessControl.setCardNum("");
        }
        accessControl.setAccessImgUrl(imageUrl);
        accessControl.setBuildingCode(device.getBuildingCode());
        accessControl.setBuildingName(building.getBuildingName());
        accessControl.setUnitCode(device.getUnitCode());
        accessControl.setUnitName(unit.getUnitName());
        if (householdRoom != null) {
            accessControl.setRoomNum(householdRoom.getRoomNum());
        }


        /*long time = System.currentTimeMillis();
          String timeStr = String.valueOf(time);
          accessControl.setAccessControlId(Integer.parseInt(timeStr.substring(timeStr.length()-11, timeStr.length())));*/

        accessControl.setGmtCreate(LocalDateTime.now());
        accessControl.setGmtModified(LocalDateTime.now());
        accessControlService.insert(accessControl);
        return Result.success("上传成功");
    }

    @PostMapping("/uploadCardOpenRecord")
    @ApiOperation(value = "接收上传的门禁卡开门记录", notes = "传参：") //需要捕捉连接异常，判断门禁机是否在线
    @Transactional
    public Result uploadCardOpenRecord(HttpServletRequest request, String mac, String cardNum, String base64) throws Exception{
        AccessControl accessControl = new AccessControl();

        DnakeDeviceInfo dnakeDeviceInfo = dnakeDeviceInfoService.getDeviceInfoByMac(mac);
        Device device = deviceService.getByDnakeDeviceInfoId(dnakeDeviceInfo.getId());
        AccessCard accessCard = accessCardService.getByCardNumAndDeviceNum(cardNum, device.getDeviceNum());
        Integer houseHoldId = accessCard.getHouseHoldId();
        String deviceNum = accessCard.getDeviceNum();
        HouseHold houseHold = houseHoldService.getByHouseholdId(houseHoldId);
        HouseholdRoom householdRoom = householdRoomService.getByHouseHoldIdAndCommunityCodeAndBuilingIdAndUnitId(houseHold.getHouseholdId(),device.getCommunityCode(),device.getBuildingId(),device.getUnitId());
        ClusterCommunity clusterCommunity = clusterCommunityService.getByCommunityCode(device.getCommunityCode());
        Zone zone = zoneService.getByZoneId(device.getCommunityCode(), Integer.parseInt(device.getZoneId()));
        Building building = buildingService.getByBuidingId(Integer.parseInt(device.getBuildingId()));
        Unit unit = unitService.getByUnitId(Integer.parseInt(device.getUnitId()));
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] b = decoder.decodeBuffer(base64);
        String imageUrl = UploadUtil.uploadWithByte(b);//开门时抓拍的图片
        accessControl.setCommunityCode(device.getCommunityCode());
        accessControl.setCommunityName(clusterCommunity.getCommunityName());
        accessControl.setAccessTime(LocalDateTime.now());
        accessControl.setInteractiveType(1);
        accessControl.setDeviceName(device.getDeviceName());
        accessControl.setDeviceNum(deviceNum);
        accessControl.setZoneId(String.valueOf(device.getZoneId()));
        accessControl.setZoneName(zone.getZoneName());
        accessControl.setHouseholdId(houseHold.getHouseholdId());
        accessControl.setHouseholdName(houseHold.getHouseholdName());
        accessControl.setHouseholdMobile(houseHold.getMobile());
        if (accessCard != null) {
            accessControl.setCardNum(accessCard.getCardNum());
        } else {
            accessControl.setCardNum("");
        }
        accessControl.setAccessImgUrl(imageUrl);
        accessControl.setBuildingCode(device.getBuildingCode());
        accessControl.setBuildingName(building.getBuildingName());
        accessControl.setUnitCode(device.getUnitCode());
        accessControl.setUnitName(unit.getUnitName());
        /*long time = System.currentTimeMillis();
        String timeStr = String.valueOf(time);
        accessControl.setAccessControlId(Integer.parseInt(timeStr.substring(timeStr.length()-11, timeStr.length())));*/
        accessControl.setGmtCreate(LocalDateTime.now());
        accessControl.setGmtModified(LocalDateTime.now());
        if (householdRoom != null){
            accessControl.setRoomNum(householdRoom.getRoomNum());
        }

        accessControlService.insert(accessControl);
        return Result.success("上传成功");
    }


    @PostMapping("/getInviteCode")
    @ApiOperation(value = "获取访客邀请码（简单模式）", notes = "传参：cellphone 手机号；dateTag 日期标志：今天:0；明天：1;" +
                      "times 开锁次数：无限次：0；一次：1；deviceGroupId 设备分组id，默认只传公共权限组；communityCode 社区编号") //没有表
    public Result getInviteCode(HttpServletRequest request, String cellphone, String dateTag, String times, String deviceGroupId, String communityCode) throws Exception{
        String message = visitorInviteCodeService.getInviteCode(cellphone, dateTag, times, deviceGroupId, communityCode);
        return Result.success(message);
    }

    @PostMapping("/getHighModelInviteCode")
    @ApiOperation(value = "获取访客邀请码（高级模式）", notes = "传参：cellphone 手机号；" +
            "；deviceGroupId 设备分组id，默认只传公共权限组；communityCode 社区编号；days 日期；timeQuantum 时间段") //没有表
    public Result getHighModelInviteCode(HttpServletRequest request, String cellphone, String deviceGroupId, String communityCode, String days, String timeQuantum) throws Exception{
        String message = visitorInviteCodeService.getHighModelInviteCode(cellphone, deviceGroupId, communityCode, days, timeQuantum);
        return Result.success(message);
    }

    @RequestMapping("/vistitorPassWordVerify")
    @ApiOperation(value = "访客密码验证", notes = "传参：") //没有表
    public Result vistitorPassWordVerify(HttpServletRequest request, String mac, String passWord) throws Exception {
        System.out.println("=======================mac="+mac);
        System.out.println("=======================passWord="+passWord);
        DnakeDeviceInfo dnakeDeviceInfo = dnakeDeviceInfoService.getDeviceInfoByMac(mac);
        if (dnakeDeviceInfo != null) {
            Device device = deviceService.getByDnakeDeviceInfoId(dnakeDeviceInfo.getId());
            if (device != null) {
                DeviceDeviceGroup deviceDeviceGroup = deviceDeviceGroupService.getByDeviceNum(device.getDeviceNum());
                if (deviceDeviceGroup != null) {
                    Integer visitorvInviteCodeId = visitorInviteCodeService.getByDeviceGroupIdAndPassWord(deviceDeviceGroup.getDeviceGroupId(), passWord);
                    if (visitorvInviteCodeId == 0) {
                        return Result.error("密码错误");
                    } else {
                        return Result.success(visitorvInviteCodeId);
                    }
                } else {
                    return Result.error("密码错误");
                }
            } else {
                return Result.error("密码错误");
            }
        } else {
            return Result.error("密码错误");
        }
    }

    @RequestMapping("/uploadVistitorRecord")
    @ApiOperation(value = "接受访客开门照片", notes = "传参：") //没有表
    public Result uploadVistitorRecord(HttpServletRequest request, String id, String photo, String mac) throws IOException {
        System.out.println("=======================id="+id);
        System.out.println("=======================mac="+mac);
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] b = decoder.decodeBuffer(photo);
        String imageUrl = UploadUtil.uploadWithByte(b);//开门时抓拍的图片
        VisitorInviteCode visitorInviteCode = visitorInviteCodeService.getById(Integer.parseInt(id));
        visitorInviteCode.setGmtModified(LocalDateTime.now());
        visitorInviteCode.setUseTimes(visitorInviteCode.getUseTimes() + 1);
        visitorInviteCodeService.update(visitorInviteCode);

        AccessControl accessControl = new AccessControl();
        HouseHold houseHold = houseHoldService.getByCellphoneAndCommunityCode(visitorInviteCode.getCellphone(),visitorInviteCode.getCommunityCode());
        String communityCode = visitorInviteCode.getCommunityCode();
        DnakeDeviceInfo dnakeDeviceInfo = dnakeDeviceInfoService.getDeviceInfoByMac(mac);
        Device device = deviceService.getByDnakeDeviceInfoId(dnakeDeviceInfo.getId());
        AccessCard accessCard = accessCardService.getByHouseHoidIdAndDeviceNum(houseHold.getHouseholdId(), device.getDeviceNum());
        ClusterCommunity clusterCommunity = clusterCommunityService.getByCommunityCode(device.getCommunityCode());
        Zone zone = zoneService.getByZoneId(device.getCommunityCode(), Integer.parseInt(device.getZoneId()));
        Building building = buildingService.getByBuidingId(Integer.parseInt(device.getBuildingId()));
        Unit unit = unitService.getByUnitId(Integer.parseInt(device.getUnitId()));
        HouseholdRoom householdRoom = householdRoomService.getByHouseHoldIdAndCommunityCodeAndBuilingIdAndUnitId(houseHold.getHouseholdId(),communityCode,device.getBuildingId(),device.getUnitId());
        accessControl.setCommunityCode(communityCode);
        accessControl.setCommunityName(clusterCommunity.getCommunityName());
        accessControl.setAccessTime(LocalDateTime.now());
        accessControl.setInteractiveType(2);
        accessControl.setDeviceName(device.getDeviceName());
        accessControl.setDeviceNum(device.getDeviceNum());
        accessControl.setZoneId(String.valueOf(device.getZoneId()));
        accessControl.setZoneName(zone.getZoneName());
        accessControl.setHouseholdId(houseHold.getHouseholdId());
        accessControl.setHouseholdName(houseHold.getHouseholdName());
        accessControl.setHouseholdMobile(houseHold.getMobile());
        if (accessCard != null) {
            accessControl.setCardNum(accessCard.getCardNum());
        } else {
            accessControl.setCardNum("");
        }
        accessControl.setAccessImgUrl(imageUrl);
        accessControl.setBuildingCode(device.getBuildingCode());
        accessControl.setBuildingName(building.getBuildingName());
        accessControl.setUnitCode(device.getUnitCode());
        accessControl.setUnitName(unit.getUnitName());

        /*long time = System.currentTimeMillis();
        String timeStr = String.valueOf(time);
        accessControl.setAccessControlId(Integer.parseInt(timeStr.substring(timeStr.length()-11, timeStr.length())));//暂时不知*/
        accessControl.setGmtCreate(LocalDateTime.now());
        accessControl.setGmtModified(LocalDateTime.now());
        if (householdRoom != null) {
            accessControl.setRoomNum(householdRoom.getRoomNum());
        }

        accessControlService.insert(accessControl);

        return Result.success("ok");
    }

    @RequestMapping("/getTextNotice")
    @ApiOperation(value = "获取文本公告", notes = "传参：") //没有表
    public Result getTextNotice(String mac) throws IOException {
        System.out.println("========================获取文本公告");
        Integer id = 1356;
        return Result.success("这是服务器下发的文字内容 收到请滚动显示");
    }

}
