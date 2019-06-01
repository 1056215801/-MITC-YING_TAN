package com.mit.community.module.device.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mit.community.config.InitProjectParameter;
import com.mit.community.entity.UploadFaceComparisonData;
import com.mit.community.service.DeviceService;
import com.mit.community.service.FaceComparisonService;
import com.mit.community.service.RealTimePhotoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import util.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
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

    //@ApiOperation(value = "QQ物联摄像头上传人脸比对数据", notes = "输入参数：token 设备登录时下发的token")
    @RequestMapping(value = "download",produces = {"application/json;charset=utf-8"})
    public void upload(HttpServletRequest request, HttpServletResponse response,String lingPai) throws Exception{
        InputStream in = null;
        try{
            System.out.println("===========进入download上传比对数据=" + lingPai);
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
            realTimePhotoService.saveSnapPhoto(basePath, deviceNum, photoBase64, time);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
