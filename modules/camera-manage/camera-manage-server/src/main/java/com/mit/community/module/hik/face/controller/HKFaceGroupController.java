package com.mit.community.module.hik.face.controller;
import com.alibaba.fastjson.JSONObject;
import com.mit.community.entity.ConfigInfo;
import com.mit.community.entity.com.mit.community.entity.hik.FaceGroup;
import com.mit.community.service.ConfigInfoService;
import com.mit.community.util.ArtemisConfig;
import com.mit.community.util.ArtemisHttpUtil;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@Api(tags = "海康人脸分组")
@RequestMapping(value = "/hkFaceGroup")
public class HKFaceGroupController {
    @Autowired
    private  ConfigInfoService configInfoService;
    String  ARTEMIS_PATH = "/artemis";
    @ModelAttribute
    public void  config(){
        ConfigInfo configInfo = configInfoService.getConfig();
        ArtemisConfig.host = configInfo.getIp() + ":" + configInfo.getPort();
        // 秘钥Appkey
        ArtemisConfig.appKey = configInfo.getAppKey();
        // 秘钥AppSecret
        ArtemisConfig.appSecret = configInfo.getAppSecret();
    }

    @RequestMapping(value = "/addSingleFaceGroup", method = RequestMethod.POST)
    @ApiOperation(value = "单个添加人脸分组", notes = "")
    @ResponseBody
    public Result addSingleFaceGroup(FaceGroup faceGroup) {
        String getRootApi = ARTEMIS_PATH + "/api/frs/v1/face/group/single/addition";
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("https://", getRootApi);//根据现场环境部署确认是http还是https
            }
        };

        String contentType = "application/json";
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("name", faceGroup.getName());
        jsonBody.put("description",faceGroup.getDescription());
        String body = jsonBody.toJSONString();
        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, contentType);
        net.sf.json.JSONObject jsonToken = net.sf.json.JSONObject.fromObject(result);
        Map data=null;
        if ("0".equals(jsonToken.getString("code"))) {
            data= (Map) jsonToken.get("data");
        }
        return Result.success(data);
    }


    @RequestMapping(value = "/deleteFaceGroup", method = RequestMethod.POST)
    @ApiOperation(value = "按条件删除人脸分组", notes = "")
    @ResponseBody
    public Result deleteFaceGroup(String[] indexCodes) {
        config();
        String getRootApi = ARTEMIS_PATH + "/api/frs/v1/face/group/batch/deletion";
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("https://", getRootApi);//根据现场环境部署确认是http还是https
            }
        };
        String contentType = "application/json";
        List<String> arr=new ArrayList<>();
        if(indexCodes!=null && indexCodes.length>0) {
            for (int i=0;i<indexCodes.length;i++){
                arr.add(indexCodes[i]);
            }

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("indexCodes", arr);
            String body = jsonBody.toJSONString();
            String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, contentType);
            net.sf.json.JSONObject jsonToken = net.sf.json.JSONObject.fromObject(result);
            Boolean data=null;
            if ("0".equals(jsonToken.getString("code"))) {
                data= (Boolean) jsonToken.get("data");
            }
            return Result.success(data);

        }else{
            return Result.error("参数为空！");
        }
    }


    @RequestMapping(value = "/queryFaceGroup", method = RequestMethod.POST)
    @ApiOperation(value = "按条件查询人脸分组", notes = "")
    @ResponseBody
    public Result queryFaceGroup(String[] indexCodes, String name ) {
        config();
        String getRootApi = ARTEMIS_PATH + "/api/frs/v1/face/group";
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("https://", getRootApi);//根据现场环境部署确认是http还是https
            }
        };
        String contentType = "application/json";
        List<String> arr = new ArrayList<>();
        if (indexCodes != null && indexCodes.length > 0) {
            for (int i = 0; i < indexCodes.length; i++) {
                arr.add(indexCodes[i]);
            }
        }else{

        }
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("indexCodes", arr);
        jsonBody.put("name", name);
        String body = jsonBody.toJSONString();
        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, contentType);
        JSONObject jsonObject=new JSONObject();
        JSONObject jsonToken= (JSONObject) JSONObject.parse(result);
        //net.sf.json.JSONObject jsonToken = net.sf.json.JSONObject.fromObject(result);
        List<Map> list = new ArrayList<>();
        if ("0".equals(jsonToken.getString("code"))) {
            list = (List) jsonToken.get("data");
        }
        return Result.success(list);



    }

    @RequestMapping(value = "/updateSingleFaceGroup", method = RequestMethod.POST)
    @ApiOperation(value = "单个修改人脸分组", notes = "")
    @ResponseBody
    public Result updateSingleFaceGroup(@ApiParam FaceGroup  faceGroup) {
        config();
        String getRootApi = ARTEMIS_PATH + "/api/frs/v1/face/group/single/update";
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("https://", getRootApi);//根据现场环境部署确认是http还是https
            }
        };
        String contentType = "application/json";
        List<String> arr=new ArrayList<>();
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("description", faceGroup.getDescription());
        jsonBody.put("name", faceGroup.getName());
        jsonBody.put("indexCode", faceGroup.getIndexCode());
        String body = jsonBody.toJSONString();
        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, contentType);
        net.sf.json.JSONObject jsonToken = net.sf.json.JSONObject.fromObject(result);
        Boolean data=null;
        if ("0".equals(jsonToken.getString("code"))) {
            data= (Boolean) jsonToken.get("data");
        }
        return Result.success(data);


    }


}




