package com.mit.community.module.hk.controller;

import com.mit.community.entity.CameraInfo;
import com.mit.community.entity.CameraLb;
import com.mit.community.entity.ConfigInfo;
import com.mit.community.entity.SnapFaceData;
import com.mit.community.service.ConfigInfoService;
import com.mit.community.util.ArtemisConfig;
import com.mit.community.util.ArtemisHttpUtil;
import com.mit.community.util.GetBaiDuAccessToken;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

@RestController
@Slf4j
@Api(tags = "获取视屏设备信息")
@RequestMapping(value = "/hkVideo")
public class HkController {
    @Autowired
    private ConfigInfoService configInfoService;

    @PostMapping("/devicesInfo")
    @ApiOperation(value = "获取设备列表", notes = "")
    public Result devicesInfo() {
        /**
         * STEP1：设置平台参数，根据实际情况,设置host appkey appsecret 三个参数.
         */
        ConfigInfo configInfo = configInfoService.getConfig();
        ArtemisConfig.host = configInfo.getIp() + ":" + configInfo.getPort();
        // 秘钥Appkey
        ArtemisConfig.appKey = configInfo.getAppKey();
        // 秘钥AppSecret
        ArtemisConfig.appSecret = configInfo.getAppSecret();
        String ARTEMIS_PATH = "/artemis";
        String getRootApi = ARTEMIS_PATH + "/api/resource/v1/regions";
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("https://", getRootApi);//根据现场环境部署确认是http还是https
            }
        };
        /**
         * STEP4：设置参数提交方式
         */
        String contentType = "application/json";
        /**
         * STEP5：组装请求参数
         */
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("pageNo", 1);
        jsonBody.put("pageSize", 999);
        String body = jsonBody.toJSONString();
        /**
         * STEP6：调用接口
         */
        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, contentType);
        //String result = "{\"code\":\"0\",\"msg\":\"SUCCESS\",\"data\":{\"total\":2,\"pageNo\":1,\"pageSize\":20,\"list\":[{\"indexCode\":\"root000000\",\"name\":\"根节点\",\"parentIndexCode\":null,\"treeCode\":\"0\"},{\"indexCode\":\"0129900000000001\",\"name\":\"test\",\"parentIndexCode\":\"root000000\",\"treeCode\":\"0\"}]}}";
        System.out.println("请求路径："+ getRootApi + ",请求参数："+ body + ",返回结果：" + result);
        net.sf.json.JSONObject jsonToken = net.sf.json.JSONObject.fromObject(result);
        List<CameraLb> list = new ArrayList<>();
        if("SUCCESS".equals(jsonToken.getString("msg"))) {
            net.sf.json.JSONObject data = net.sf.json.JSONObject.fromObject(jsonToken.getString("data"));
            JSONArray array = data.getJSONArray("list");
            list = (List)JSONArray.toCollection(array,CameraLb.class);
        }
        return Result.success(list);
    }

    @PostMapping("/cameraInfo")
    @ApiOperation(value = "获取相机信息列表", notes = "")
    public Result cameraInfo(String indexCode) {
        /**
         * STEP1：设置平台参数，根据实际情况,设置host appkey appsecret 三个参数.
         */
        ConfigInfo configInfo = configInfoService.getConfig();
        ArtemisConfig.host = configInfo.getIp() + ":" + configInfo.getPort();
        System.out.println(ArtemisConfig.host);
        // 秘钥Appkey
        ArtemisConfig.appKey = configInfo.getAppKey();
        System.out.println(ArtemisConfig.appKey);
        // 秘钥AppSecret
        ArtemisConfig.appSecret = configInfo.getAppSecret();
        System.out.println(ArtemisConfig.appSecret);
        String ARTEMIS_PATH = "/artemis";
        String getRootApi = ARTEMIS_PATH + "/api/resource/v1/regions/regionIndexCode/cameras";
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("https://", getRootApi);//根据现场环境部署确认是http还是https
            }
        };
        /**
         * STEP4：设置参数提交方式
         */
        String contentType = "application/json";
        /**
         * STEP5：组装请求参数
         */
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("pageNo", 1);
        jsonBody.put("pageSize", 999);
        jsonBody.put("regionIndexCode", indexCode);
        String body = jsonBody.toJSONString();
        /**
         * STEP6：调用接口
         */
        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, contentType);
        //String result = "{\"code\":\"0\",\"msg\":\"SUCCESS\",\"data\":{\"total\":2,\"pageNo\":1,\"pageSize\":1,\"list\":[{\"altitude\":null,\"cameraIndexCode\":\"66b5a02624f242a7989528786e741ef1\",\"cameraName\":\"Camera 01\",\"cameraType\":0,\"cameraTypeName\":\"枪机\",\"capabilitySet\":\"event_body,io,MixMixedRoot,event_face,event_rule,event_veh,event_ias,event_heat,drawFrameInPlayBack,event_face_match,record,vss,ptz,event_io,net,maintenance,event_device,PlayConvert,status\",\"capabilitySetName\":null,\"intelligentSet\":null,\"intelligentSetName\":null,\"channelNo\":\"1\",\"channelType\":\"digital\",\"channelTypeName\":\"数字通道\",\"createTime\":\"2019-06-25T13:07:51.929+08:00\",\"encodeDevIndexCode\":\"e9b48fac159e40648367b244f4d60f22\",\"encodeDevResourceType\":null,\"encodeDevResourceTypeName\":null,\"gbIndexCode\":null,\"installLocation\":null,\"keyBoardCode\":null,\"latitude\":null,\"longitude\":null,\"pixel\":null,\"ptz\":null,\"ptzName\":null,\"ptzController\":null,\"ptzControllerName\":null,\"recordLocation\":null,\"recordLocationName\":null,\"regionIndexCode\":\"0129900000000001\",\"status\":null,\"statusName\":null,\"transType\":1,\"transTypeName\":\"TCP\",\"treatyType\":null,\"treatyTypeName\":null,\"viewshed\":null,\"updateTime\":\"2019-06-25T13:58:22.856+08:00\"},{\"altitude\":null,\"cameraIndexCode\":\"3d882cca4c7d44fd94a46cf1e6896878\",\"cameraName\":\"Camera 02\",\"cameraType\":0,\"cameraTypeName\":\"枪机\",\"capabilitySet\":\"event_body,io,MixMixedRoot,event_face,event_rule,event_veh,event_ias,event_heat,drawFrameInPlayBack,event_face_match,record,vss,ptz,event_io,net,maintenance,event_device,PlayConvert,status\",\"capabilitySetName\":null,\"intelligentSet\":null,\"intelligentSetName\":null,\"channelNo\":\"2\",\"channelType\":\"digital\",\"channelTypeName\":\"数字通道\",\"createTime\":\"2019-06-25T13:07:51.928+08:00\",\"encodeDevIndexCode\":\"e9b48fac159e40648367b244f4d60f22\",\"encodeDevResourceType\":null,\"encodeDevResourceTypeName\":null,\"gbIndexCode\":null,\"installLocation\":null,\"keyBoardCode\":null,\"latitude\":null,\"longitude\":null,\"pixel\":null,\"ptz\":null,\"ptzName\":null,\"ptzController\":null,\"ptzControllerName\":null,\"recordLocation\":null,\"recordLocationName\":null,\"regionIndexCode\":\"0129900000000001\",\"status\":null,\"statusName\":null,\"transType\":1,\"transTypeName\":\"TCP\",\"treatyType\":null,\"treatyTypeName\":null,\"viewshed\":null,\"updateTime\":\"2019-06-26T10:46:16.230+08:00\"}]}}";
        System.out.println("请求路径："+ getRootApi + ",请求参数："+ body + ",返回结果：" + result);
        net.sf.json.JSONObject jsonToken = net.sf.json.JSONObject.fromObject(result);
        List<CameraInfo> list = new ArrayList<>();
        if("SUCCESS".equals(jsonToken.getString("msg"))) {
            net.sf.json.JSONObject data = net.sf.json.JSONObject.fromObject(jsonToken.getString("data"));
            JSONArray array = data.getJSONArray("list");
            list = (List)JSONArray.toCollection(array,CameraInfo.class);
        }
        return Result.success(list);
    }

    @PostMapping("/configInfo")
    @ApiOperation(value = "获取配置信息", notes = "")
    public Result configInfo(){
        ConfigInfo configInfo = configInfoService.getConfig();
        return Result.success(configInfo);
    }


}
