package com.mit.community.module.device.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mit.community.entity.AccessRecord;
import com.mit.community.entity.CarIn;
import com.mit.community.entity.UploadFaceComparisonData;
import com.mit.community.service.AccessRecordService;
import com.mit.community.service.DeviceService;
import com.mit.community.service.FaceComparisonService;
import com.mit.community.service.RealTimePhotoService;
import com.mit.community.util.HttpUtil;
import com.mit.community.util.UploadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Decoder;
import util.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 和QQ物联摄像头交互的接口
 * @company mitesofor
 */
@RestController
@Slf4j
@Api(tags = "和QQ物联摄像头交互的接口(非对接接口)")
public class DeviceController {
    @Autowired
    private FaceComparisonService faceComparisonService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private RealTimePhotoService realTimePhotoService;

    @Autowired
    private AccessRecordService accessRecordService;
    //本机ip：192.168.1.149

    @RequestMapping(value = "login",produces = {"application/json;charset=utf-8"})
    //@ApiOperation(value = "设备登录接口")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        String returnMessage = null;
        InputStream in = null;
        Long time = System.currentTimeMillis();
        try{
            in = request.getInputStream();
            String model = Utils.inputStream2String(in, "");
            if(model == null || "".equals(model)){
                returnMessage = "{\"command\":\"ack_signature\",\"timestamp\":" + time + "}";
            } else {
                String token = UUID.randomUUID().toString().replaceAll("-", "");
                JSONObject json = JSONObject.fromObject(model);
                String deviceId = json.getString("deviceid");
                System.out.println("=============deviceId="+deviceId);
                deviceService.saveToken(deviceId, token);
                //暂时忽略合法认证，直接返回登录成功
                returnMessage = "{\"command\":\"reply_login\",\"token\":\"" + token + "\",\"errcode\":0,\"errmsg\":\"ok\"}";
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return returnMessage;
    }

    @ApiOperation(value = "QQ物联摄像头上传人脸比对数据", notes = "输入参数：token 设备登录时下发的token")
    @RequestMapping(value = "download",produces = {"application/json;charset=utf-8"})
    public void upload(HttpServletRequest request, HttpServletResponse response,String lingPai) throws Exception{
        InputStream in = null;
        try{
            in = request.getInputStream();
            String model = Utils.inputStream2String(in, "");
            if (model != null) {
                ObjectMapper mapper = new ObjectMapper();
                UploadFaceComparisonData data = mapper.readValue(model, UploadFaceComparisonData.class);
                String basePath = request.getServletContext().getRealPath("imgs/");
                File file = new File(basePath);
                if (!file.exists()) {
                    file.mkdir();
                }
                faceComparisonService.saveUploadFaceComparisonData(basePath, data, lingPai);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    @Description("QQ物联摄像头上传所有抓拍的人脸数据")
    @PostMapping(value = "allInterface",produces = {"application/json;charset=utf-8"})
    public void allInterface(HttpServletRequest request, HttpServletResponse response) {
        InputStream in = null;
        try{
            in = request.getInputStream();
            String model = Utils.inputStream2String(in, "");
            String basePath = request.getServletContext().getRealPath("imgs/");
            System.out.println("===========进入allInterface上传数据请求=");
            JSONObject json = JSONObject.fromObject(model);
            String deviceNum = json.getString("deviceId");
            String photoBase64 = json.getString("image");
            long time = json.getLong("time");
            File file = new File(basePath);
            if (!file.exists()) {
                file.mkdir();
            }
            deviceService.updateOutCount();
            realTimePhotoService.saveSnapPhoto(basePath, deviceNum, photoBase64, time);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @PostMapping("/Interface2")
    public void Interface2(HttpServletRequest request,String photoBase64, String model) {
        AccessRecord accessRecord = new AccessRecord();
        try{
            System.out.println("============数据=="+model);
            if (model != null) {
                JSONObject json = JSONObject.fromObject(model);
                String cmd = json.getString("cmd");
                if ("CarIn".equals(cmd)) {
                    JSONObject carIn = json.getJSONObject("params");
                    accessRecord.setCarnum(carIn.getString("CPH"));
                    accessRecord.setAccessType("进");
                    accessRecord.setPasstime(parseStringToLocal(carIn.getString("InTime")));
                    accessRecord.setInGateName(carIn.getString("InGateName"));
                    accessRecord.setSfGate(carIn.getString("SFGate"));
                    accessRecord.setCareParkNo(carIn.getString("CarparkNO"));
                    if ("DNX114".equals(accessRecord.getCareParkNo())) {
                        accessRecord.setCommunity_code("a5f53a2248794c678766edad485392ff");
                    } else if ("DNX113".equals(accessRecord.getCareParkNo())){
                        accessRecord.setCommunity_code("b181746d9bd1444c80522f9923c59b80");
                    }
                } else if ("CarOut".equals(cmd)) {
                    JSONObject carOut = json.getJSONObject("params");
                    accessRecord.setCarnum(carOut.getString("CPH"));
                    accessRecord.setAccessType("出");
                    accessRecord.setPasstime(parseStringToLocal(carOut.getString("OutTime")));
                    accessRecord.setInGateName(carOut.getString("InGateName"));
                    accessRecord.setSfGate(carOut.getString("SFGate"));
                    accessRecord.setCareParkNo(carOut.getString("CarparkNO"));
                    if ("DNX114".equals(accessRecord.getCareParkNo())) {
                        accessRecord.setCommunity_code("a5f53a2248794c678766edad485392ff");
                    } else if ("DNX113".equals(accessRecord.getCareParkNo())){
                        accessRecord.setCommunity_code("b181746d9bd1444c80522f9923c59b80");
                    }
                }

                if (StringUtils.isNotBlank(photoBase64)) {
                    if ("图片不存在".equals(photoBase64)) {
                        accessRecord.setImage("图片不存在");
                    } else {
                        BASE64Decoder decoder = new BASE64Decoder();
                        photoBase64 = photoBase64.replaceAll(" ","+" );
                        System.out.println("=====图片=" + photoBase64);
                        log.info("图片base64="+photoBase64);
                        byte[] b = decoder.decodeBuffer(photoBase64);
                        String imageUrl = UploadUtil.uploadWithByte(b);
                        accessRecord.setImage(imageUrl);
                    }
                } else {
                    accessRecord.setImage("图片不存在");
                }
                accessRecord.setGmtCreate(LocalDateTime.now());
                accessRecord.setGmtModified(LocalDateTime.now());
                accessRecordService.save(accessRecord);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public LocalDateTime parseStringToLocal(String strTime){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ldt = LocalDateTime.parse(strTime,df);
        return ldt;
    }

}
