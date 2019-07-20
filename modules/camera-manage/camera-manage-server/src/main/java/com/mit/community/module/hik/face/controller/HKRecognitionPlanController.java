package com.mit.community.module.hik.face.controller;


import com.alibaba.fastjson.JSONObject;
import com.mit.community.util.ArtemisConfig;
import com.mit.community.util.ArtemisHttpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@Api(tags = "海康识别人计划")
@RequestMapping(value = "/hkRecognitionPlan")
public class HKRecognitionPlanController {


    @ApiOperation(value = "单个添加重点人员识别计划", notes = "")
    public static String addBlackRecognitionPlan() {

        /**
         * STEP1：设置平台参数，根据实际情况,设置host appkey appsecret 三个参数.
         */
        ArtemisConfig.host = "192.168.1.230:443"; // artemis网关服务器ip端口
        ArtemisConfig.appKey = "25892539"; // 秘钥appkey
        ArtemisConfig.appSecret = "fYFeO1sqj2Z4Ul4Sd8dT";// 秘钥appSecret

        /**
         * STEP2：设置OpenAPI接口的上下文
         */
        final String ARTEMIS_PATH = "/artemis";

        /**
         * STEP3：设置接口的URI地址
         */
        String getApi = ARTEMIS_PATH + "/api/frs/v1/plan/recognition/black/addition";
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("https://", getApi);//根据现场环境部署确认是http还是https
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
        jsonBody.put("name", "这是一条重点人员识别计划");

        List<String> faceGroupIndexCodes = new ArrayList<String>();
        faceGroupIndexCodes.add("11dcec2c-93bc-48d7-9548-ffb1cf1c2414");
        jsonBody.put("faceGroupIndexCodes", faceGroupIndexCodes);

        List<String> cameraIndexCodes = new ArrayList<String>();
        cameraIndexCodes.add("66b5a02624f242a7989528786e741ef1");
        jsonBody.put("cameraIndexCodes", cameraIndexCodes);

       /* List<String> recognitionResourceIndexCodes = new ArrayList<String>();
        recognitionResourceIndexCodes.add("867c73b5-56d8-43b7-8491-6481ab23c8be");
        jsonBody.put("recognitionResourceIndexCodes", recognitionResourceIndexCodes);*/

        jsonBody.put("recognitionResourceType", "SUPER_BRAIN");
        jsonBody.put("description", "这是一个描述");
        jsonBody.put("threshold", 32);

        //时间数组，由时间对象组成
       /* List<Map<String, Object>> timeBlockList = new ArrayList<Map<String, Object>>();
        //时间对象，参数包扩时间范围和天数
        Map<String, Object> timeBlock = new HashMap<String, Object>();
        //时间范围参数，包括开始时间和结束时间
        List<Map<String, String>> timeRanges = new ArrayList<Map<String, String>>();
        Map<String, String> timeRange = new HashMap<String, String>();
        timeRange.put("startTime", "00:00");
        timeRange.put("endTime", "12:00");
        timeRanges.add(timeRange);
        Map<String, String> timeRange2 = new HashMap<String, String>();
        timeRange2.put("startTime", "12:00");
        timeRange2.put("endTime", "24:00");
        timeRanges.add(timeRange2);

        timeBlock.put("timeRange", timeRanges);
        timeBlock.put("dayOfWeek", "1");
        timeBlockList.add(timeBlock);

        //时间对象，参数包扩时间范围和天数
        Map<String, Object> timeBlock2 = new HashMap<String, Object>();
        //时间范围参数，包括开始时间和结束时间
        List<Map<String, String>> timeRanges2 = new ArrayList<Map<String, String>>();
        Map<String, String> timeRange3 = new HashMap<String, String>();
        //时间范围参数，包括开始时间和结束时间
        timeRange3.put("startTime", "00:00");
        timeRange3.put("endTime", "12:00");
        timeRanges2.add(timeRange3);

        timeBlock2.put("timeRange", timeRanges2);
        timeBlock2.put("dayOfWeek", "2");
        timeBlockList.add(timeBlock2);

        jsonBody.put("timeBlockList", timeBlockList);*/

        String body = jsonBody.toJSONString();
        System.out.println(body);
        /**
         * STEP6：调用接口
         */
        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, contentType);
        System.out.println(result);
        return result;
    }
    /**

     按条件删除重点人员识别计划

     */
    public static String deleteBlackRecognitionPlan() {

        /**
         * STEP1：设置平台参数，根据实际情况,设置host appkey appsecret 三个参数.
         */
        ArtemisConfig.host = "10.19.132.3:443"; // artemis网关服务器ip端口
        ArtemisConfig.appKey = "29180881"; // 秘钥appkey
        ArtemisConfig.appSecret = "XO0wCAYGi4KV70ybjznx";// 秘钥appSecret

        /**
         * STEP2：设置OpenAPI接口的上下文
         */
        final String ARTEMIS_PATH = "/artemis";

        /**
         * STEP3：设置接口的URI地址
         */
        String getApi = ARTEMIS_PATH + "/api/frs/v1/plan/recognition/black/deletion";
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("https://", getApi);//根据现场环境部署确认是http还是https
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

        List<String> indexCodes = new ArrayList<String>();
        indexCodes.add("7cc0adb2-a3c3-48fd-b432-718103e85c28");
        jsonBody.put("indexCodes", indexCodes);


        String body = jsonBody.toJSONString();

        /**
         * STEP6：调用接口
         */
        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, contentType);
        System.out.println(result);
        return result;
    }

/*
  单个查询重点人员识别计划详情
        */

    public static String queryBlackRecognitionPlan() {

        /**
         * STEP1：设置平台参数，根据实际情况,设置host appkey appsecret 三个参数.
         */
        ArtemisConfig.host = "10.19.132.3:443"; // artemis网关服务器ip端口
        ArtemisConfig.appKey = "29180881"; // 秘钥appkey
        ArtemisConfig.appSecret = "XO0wCAYGi4KV70ybjznx";// 秘钥appSecret

        /**
         * STEP2：设置OpenAPI接口的上下文
         */
        final String ARTEMIS_PATH = "/artemis";

        /**
         * STEP3：设置接口的URI地址
         */
        String getApi = ARTEMIS_PATH + "/api/frs/v1/plan/recognition/black/detail";
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("https://", getApi);//根据现场环境部署确认是http还是https
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

        jsonBody.put("indexCode", "7cc0adb2-a3c3-48fd-b432-718103e85c28");
        jsonBody.put("pageNo", 1);
        jsonBody.put("pageSize", 20);

        String body = jsonBody.toJSONString();
        /**
         * STEP6：调用接口
         */
        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, contentType);
        System.out.println(result);
        return result;
    }

    /**
     *
     *按条件批量查询重点人员识别计划
     */

    public static String batchBlackRecognitionPlan() {

        /**
         * STEP1：设置平台参数，根据实际情况,设置host appkey appsecret 三个参数.
         */
        ArtemisConfig.host = "10.19.132.3:443"; // artemis网关服务器ip端口
        ArtemisConfig.appKey = "29180881"; // 秘钥appkey
        ArtemisConfig.appSecret = "XO0wCAYGi4KV70ybjznx";// 秘钥appSecret

        /**
         * STEP2：设置OpenAPI接口的上下文
         */
        final String ARTEMIS_PATH = "/artemis";

        /**
         * STEP3：设置接口的URI地址
         */
        String getApi = ARTEMIS_PATH + "/api/frs/v1/plan/recognition/black";
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("https://", getApi);//根据现场环境部署确认是http还是https
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

        jsonBody.put("description", "这是一个描述");
        List<String> faceGroupIndexCodes = new ArrayList<String>();
        faceGroupIndexCodes.add("8ce19be05b5c4914b1e3c9eee3997eb2");
        jsonBody.put("faceGroupIndexCodes", faceGroupIndexCodes);
        jsonBody.put("name", "这是一条重点人员识别计划");
        jsonBody.put("status", "RUNNING");


        String body = jsonBody.toJSONString();
        /**
         * STEP6：调用接口
         */
        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, contentType);
        System.out.println(result);
        return result;
    }

    /**
     单个修改重点人员识别计划
     */

    public static String updateBlackRecognitionPlan() {

        /**
         * STEP1：设置平台参数，根据实际情况,设置host appkey appsecret 三个参数.
         */
        ArtemisConfig.host = "10.19.132.3:443"; // artemis网关服务器ip端口
        ArtemisConfig.appKey = "29180881"; // 秘钥appkey
        ArtemisConfig.appSecret = "XO0wCAYGi4KV70ybjznx";// 秘钥appSecret

        /**
         * STEP2：设置OpenAPI接口的上下文
         */
        final String ARTEMIS_PATH = "/artemis";

        /**
         * STEP3：设置接口的URI地址
         */
        String getApi = ARTEMIS_PATH + "/api/frs/v1/plan/recognition/black/update";
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("https://", getApi);//根据现场环境部署确认是http还是https
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
        jsonBody.put("name", "这是一条重点人员识别计划");
        jsonBody.put("indexCode", "7cc0adb2-a3c3-48fd-b432-718103e85c28");


        List<String> faceGroupIndexCodes = new ArrayList<String>();
        faceGroupIndexCodes.add("8ce19be05b5c4914b1e3c9eee3997eb2");
        jsonBody.put("faceGroupIndexCodes", faceGroupIndexCodes);

        List<String> cameraIndexCodes = new ArrayList<String>();
        cameraIndexCodes.add("c4023a78c83948f9b6b0fb76b0f3db71");
        jsonBody.put("cameraIndexCodes", cameraIndexCodes);

        List<String> recognitionResourceIndexCodes = new ArrayList<String>();
        recognitionResourceIndexCodes.add("867c73b5-56d8-43b7-8491-6481ab23c8be");
        jsonBody.put("recognitionResourceIndexCodes", recognitionResourceIndexCodes);


        jsonBody.put("description", "这是一个描述");
        jsonBody.put("threshold", 32);

        //时间数组，由时间对象组成
        List<Map<String, Object>> timeBlockList = new ArrayList<Map<String, Object>>();
        //时间对象，参数包扩时间范围和天数
        Map<String, Object> timeBlock = new HashMap<String, Object>();
        //时间范围参数，包括开始时间和结束时间
        List<Map<String, String>> timeRanges = new ArrayList<Map<String, String>>();
        Map<String, String> timeRange = new HashMap<String, String>();
        timeRange.put("startTime", "00:00");
        timeRange.put("endTime", "12:00");
        timeRanges.add(timeRange);
        Map<String, String> timeRange2 = new HashMap<String, String>();
        timeRange2.put("startTime", "12:00");
        timeRange2.put("endTime", "24:00");
        timeRanges.add(timeRange2);

        timeBlock.put("timeRange", timeRanges);
        timeBlock.put("dayOfWeek", "1");
        timeBlockList.add(timeBlock);

        //时间对象，参数包扩时间范围和天数
        Map<String, Object> timeBlock2 = new HashMap<String, Object>();
        //时间范围参数，包括开始时间和结束时间
        List<Map<String, String>> timeRanges2 = new ArrayList<Map<String, String>>();
        Map<String, String> timeRange3 = new HashMap<String, String>();
        //时间范围参数，包括开始时间和结束时间
        timeRange3.put("startTime", "00:00");
        timeRange3.put("endTime", "12:00");
        timeRanges2.add(timeRange3);

        timeBlock2.put("timeRange", timeRanges2);
        timeBlock2.put("dayOfWeek", "2");
        timeBlockList.add(timeBlock2);

        jsonBody.put("timeBlockList", timeBlockList);

        String body = jsonBody.toJSONString();
        System.out.println(body);
        /**
         * STEP6：调用接口
         */
        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, contentType);
        System.out.println(result);
        return result;
    }

    /**
     * 重新下发重点人员识别计划
     */
    public static String restartBlackRecognitionPlan() {

        /**
         * STEP1：设置平台参数，根据实际情况,设置host appkey appsecret 三个参数.
         */
        ArtemisConfig.host = "10.19.132.3:443"; // artemis网关服务器ip端口
        ArtemisConfig.appKey = "29180881"; // 秘钥appkey
        ArtemisConfig.appSecret = "XO0wCAYGi4KV70ybjznx";// 秘钥appSecret

        /**
         * STEP2：设置OpenAPI接口的上下文
         */
        final String ARTEMIS_PATH = "/artemis";

        /**
         * STEP3：设置接口的URI地址
         */
        String getApi = ARTEMIS_PATH + "/api/frs/v1/plan/recognition/black/restart";
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("https://", getApi);//根据现场环境部署确认是http还是https
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


        jsonBody.put("indexCode", "7cc0adb2-a3c3-48fd-b432-718103e85c28");

        String body = jsonBody.toJSONString();

        /**
         * STEP6：调用接口
         */
        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, contentType);
        System.out.println(result);
        return result;
    }

}
