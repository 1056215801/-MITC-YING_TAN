package com.mit.community.module.hik.visibleConversation.controller;

import com.alibaba.fastjson.JSONObject;
import com.mit.community.entity.com.mit.community.entity.hik.AccessEvents;
import com.mit.community.util.ArtemisConfig;
import com.mit.community.util.ArtemisHttpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @company mitesofor
 */
@RestController
@Slf4j
@Api(tags = "海康可视对讲机")
@RequestMapping(value = "/hkvisibleConversation")
public class VisibleConversationController {

    @Value("${hik-manager.ip}")
    private  String ip ;
    @Value("${hik-manager.port}")
    private  String port ;
    @Value("${hik-manager.appkey}")
    private  String appkey ;
    @Value("${hik-manager.appsecret}")
    private  String appsecret ;
    String  ARTEMIS_PATH = "/artemis";
    public void config(){
        ArtemisConfig.host =ip+":"+port;
        // 秘钥Appkey
        ArtemisConfig.appKey = appkey;
        // 秘钥AppSecret
        ArtemisConfig.appSecret = appsecret;
    }
    @RequestMapping("/getAccessEvents")
    @ApiOperation(value = "查询出入事件", notes = "")
    public  String getAccessEvents(AccessEvents accessEvents) {

        String getCamsApi = ARTEMIS_PATH + "/api/vis/v1/accessEvent/events";
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("http://", getCamsApi);// 根据现场环境部署确认是http还是https
            }
        };
        String contentType = "application/json";

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("startTime", "2018-08-10T12:00:00.000+08:00");
        jsonBody.put("endTime", "2018-08-11T12:00:00.000+08:00");
        jsonBody.put("deviceIds", "1f276203e5234bdca08f7d99e1097bba");
        jsonBody.put("sort", "personName");
        jsonBody.put("order", "asc");
        jsonBody.put("pageNo", 1);
        jsonBody.put("pageSize", 100);
        String body = jsonBody.toJSONString();
        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, contentType);
        System.out.println(result);
        return result;
    }

}
