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
        String communityCode = houseHold.getCommunityCode();
        DnakeDeviceInfo dnakeDeviceInfo = dnakeDeviceInfoService.getDeviceInfoByMac(mac);
        Device device = deviceService.getByDnakeDeviceInfoId(dnakeDeviceInfo.getId());
        HouseholdRoom householdRoom = householdRoomService.getByHouseHoldIdAndCommunityCodeAndBuilingIdAndUnitId(houseHold.getHouseholdId(),communityCode,device.getBuildingId(),device.getUnitId());
        String imageUrl = UploadUtil.uploadWithByte(b);//开门时抓拍的图片
        accessControl.setCommunityCode(communityCode);
        accessControl.setCommunityName(householdRoom.getCommunityName());
        accessControl.setAccessTime(LocalDateTime.now());
        accessControl.setInteractiveType(9);
        accessControl.setDeviceName(device.getDeviceName());
        accessControl.setDeviceNum(device.getDeviceNum());
        accessControl.setZoneId(String.valueOf(householdRoom.getZoneId()));
        accessControl.setZoneName(householdRoom.getZoneName());
        accessControl.setHouseholdId(houseHold.getHouseholdId());
        accessControl.setHouseholdName(houseHold.getHouseholdName());
        accessControl.setHouseholdMobile(houseHold.getMobile());
        accessControl.setCardNum("");//暂时不知
        accessControl.setAccessImgUrl(imageUrl);
        accessControl.setBuildingCode(device.getBuildingCode());
        accessControl.setBuildingName(householdRoom.getBuildingName());
        accessControl.setUnitCode(device.getUnitCode());
        accessControl.setUnitName(householdRoom.getUnitName());
        accessControl.setAccessControlId(111);//暂时不知
        accessControl.setGmtCreate(LocalDateTime.now());
        accessControl.setGmtModified(LocalDateTime.now());
        accessControl.setRoomNum(householdRoom.getRoomNum());
        //accessControlService.insert(accessControl);
        System.out.println(photo);
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
        Device device = deviceService.getByDeviceNumAndCommunityCode(deviceNum, communityCode);
        HouseholdRoom householdRoom = householdRoomService.getByHouseHoldIdAndCommunityCodeAndBuilingIdAndUnitId(houseHold.getHouseholdId(),communityCode,device.getBuildingId(),device.getUnitId());
        DnakeDeviceInfo dnakeDeviceInfo = dnakeDeviceInfoService.getById(device.getDeviceId());
        String resultJson = HttpPostUtil.httpOpen(dnakeDeviceInfo.getIp());
        JSONObject json = JSONObject.fromObject(resultJson);
        String base64 = json.getString("base64");
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] b = decoder.decodeBuffer(base64);
        String imageUrl = UploadUtil.uploadWithByte(b);//开门时抓拍的图片
        accessControl.setCommunityCode(communityCode);
        accessControl.setCommunityName(householdRoom.getCommunityName());
        accessControl.setAccessTime(LocalDateTime.now());
        accessControl.setInteractiveType(11);
        accessControl.setDeviceName(device.getDeviceName());
        accessControl.setDeviceNum(deviceNum);
        accessControl.setZoneId(String.valueOf(householdRoom.getZoneId()));
        accessControl.setZoneName(householdRoom.getZoneName());
        accessControl.setHouseholdId(houseHold.getHouseholdId());
        accessControl.setHouseholdName(houseHold.getHouseholdName());
        accessControl.setHouseholdMobile(houseHold.getMobile());
        accessControl.setCardNum("");//暂时不知
        accessControl.setAccessImgUrl(imageUrl);
        accessControl.setBuildingCode(device.getBuildingCode());
        accessControl.setBuildingName(householdRoom.getBuildingName());
        accessControl.setUnitCode(device.getUnitCode());
        accessControl.setUnitName(householdRoom.getUnitName());
        accessControl.setAccessControlId(111);//暂时不知
        accessControl.setGmtCreate(LocalDateTime.now());
        accessControl.setGmtModified(LocalDateTime.now());
        accessControl.setRoomNum(householdRoom.getRoomNum());
        return Result.success("门已开");
    }

    @PostMapping("/uploadCardOpenRecord")
    @ApiOperation(value = "接收上传的门禁卡开门记录", notes = "传参：") //需要捕捉连接异常，判断门禁机是否在线
    public Result uploadCardOpenRecord(HttpServletRequest request, String mac, String cardNum, String base64) throws Exception{
        AccessControl accessControl = new AccessControl();
        System.out.println("====================="+base64);
        //cardNum,houseHoldId
        /*AccessCard accessCard = accessCardService.getByCardNumAndMac(cardNum, mac);
        String communityCode = accessCard.getCommunityCode();
        Integer houseHoldId = accessCard.getHouseHoldId();
        String deviceNum = accessCard.getDeviceNum();
        HouseHold houseHold = houseHoldService.getByHouseholdId(houseHoldId);
        Device device = deviceService.getByDeviceNumAndCommunityCode(deviceNum, communityCode);
        HouseholdRoom householdRoom = householdRoomService.getByHouseHoldIdAndCommunityCodeAndBuilingIdAndUnitId(houseHold.getHouseholdId(),communityCode,device.getBuildingId(),device.getUnitId());
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] b = decoder.decodeBuffer(base64);
        String imageUrl = UploadUtil.uploadWithByte(b);//开门时抓拍的图片
        accessControl.setCommunityCode(communityCode);
        accessControl.setCommunityName(householdRoom.getCommunityName());
        accessControl.setAccessTime(LocalDateTime.now());
        accessControl.setInteractiveType(1);
        accessControl.setDeviceName(device.getDeviceName());
        accessControl.setDeviceNum(deviceNum);
        accessControl.setZoneId(String.valueOf(householdRoom.getZoneId()));
        accessControl.setZoneName(householdRoom.getZoneName());
        accessControl.setHouseholdId(houseHold.getHouseholdId());
        accessControl.setHouseholdName(houseHold.getHouseholdName());
        accessControl.setHouseholdMobile(houseHold.getMobile());
        accessControl.setCardNum(cardNum);//暂时不知
        accessControl.setAccessImgUrl(imageUrl);
        accessControl.setBuildingCode(device.getBuildingCode());
        accessControl.setBuildingName(householdRoom.getBuildingName());
        accessControl.setUnitCode(device.getUnitCode());
        accessControl.setUnitName(householdRoom.getUnitName());
        accessControl.setAccessControlId(111);//暂时不知
        accessControl.setGmtCreate(LocalDateTime.now());
        accessControl.setGmtModified(LocalDateTime.now());
        accessControl.setRoomNum(householdRoom.getRoomNum());*/
        return Result.success("上传成功");
    }

    @RequestMapping("/vistitorPassWordVerify")
    @ApiOperation(value = "访客密码验证", notes = "传参：") //没有表
    public Result vistitorPassWordVerify(HttpServletRequest request, String mac, String passWord) throws IOException {
        System.out.println("=======================mac="+mac);
        System.out.println("=======================passWord="+passWord);
        Integer id = 1356;
        return Result.success(id);
    }

    @RequestMapping("/uploadVistitorRecord")
    @ApiOperation(value = "接受访客开门照片", notes = "传参：") //没有表
    public Result uploadVistitorRecord(HttpServletRequest request, String id, String photo) throws IOException {
        System.out.println("=======================id="+id);
        System.out.println("=======================photo="+photo);
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] b = decoder.decodeBuffer(photo);
        String imageUrl = UploadUtil.uploadWithByte(b);//开门时抓拍的图片
        return Result.success("ok");
    }
}
