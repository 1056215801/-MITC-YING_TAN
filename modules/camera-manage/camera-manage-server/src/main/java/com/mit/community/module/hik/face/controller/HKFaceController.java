package com.mit.community.module.hik.face.controller;


import com.alibaba.fastjson.JSONObject;
import com.mit.community.entity.CameraLb;
import com.mit.community.entity.com.mit.community.entity.hik.FaceInfo;
import com.mit.community.entity.com.mit.community.entity.hik.SnapFaceDataHik;
import com.mit.community.service.ConfigInfoService;
import com.mit.community.service.com.mit.community.service.hik.FaceDataHikService;
import com.mit.community.service.com.mit.community.service.hik.SnapFaceDataHikService;
import com.mit.community.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@Slf4j
@Api(tags = "海康人脸")
@RequestMapping(value = "/hkFace")
public class HKFaceController {


    @Autowired
    private SnapFaceDataHikService snapFaceDataHikService;
    @Autowired
    private  ConfigInfoService configInfoService;
    @Autowired
    private FaceDataHikService faceDataHikService;

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

    @RequestMapping("/addFaceInfoToHK")
    @ApiOperation(value = "把人脸信息加入到HK人脸库", notes = "")
    public Result addFaceToHK(FaceInfo faceInfo){

        // 1.根据名称查询出分组
        //String ss= "11dcec2c-93bc-48d7-9548ffb1cf1c2414";
        String groupName=faceInfo.getFaceGroupName();
        String getRootApi = ARTEMIS_PATH + "/api/frs/v1/face/group";
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("https://", getRootApi);//根据现场环境部署确认是http还是https
            }
        };
        String contentType = "application/json";
        List<String> arr = new ArrayList<>();
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("indexCodes", arr);
        jsonBody.put("name", groupName);
        String body = jsonBody.toJSONString();
        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, contentType);
        net.sf.json.JSONObject jsonObjectResult = net.sf.json.JSONObject.fromObject(result);
        List<Map> data =jsonObjectResult.get("data")==null ?null: (List<Map>) jsonObjectResult.get("data");
        if("0".equals(jsonObjectResult.getString("code"))){
            if(data==null || data.size()==0){
                return Result.error("分组没有找到！");
            }

        }else{
            return Result.error("分组接口调用出错！");
        }

        String  faceGroupIndexCode=data.get(0).get("indexCode").toString();
        String  faceUrl=faceInfo.getFaceUrl();
        String  sex=faceInfo.getSex();
        String  certificateType=( "".equals(faceInfo.getCertificateType()) || faceInfo.getCertificateType()==null)? null:faceInfo.getCertificateType() ;
        String  certificateNum=( "".equals(faceInfo.getCertificateNum()) || faceInfo.getCertificateNum()==null)? null:faceInfo.getCertificateNum() ;
        String  name=faceInfo.getName();
        // 2.添加到指定人脸库
        String getRootApi1 = ARTEMIS_PATH + "/api/frs/v1/face/single/addition";
        Map<String, String> path1 = new HashMap<String, String>(2) {
            {
                put("https://", getRootApi1);//根据现场环境部署确认是http还是https
            }
        };

        String contentType1 = "application/json";
        JSONObject jsonBody1 = new JSONObject();
        Map<String, String> faceInfoParam = new HashMap<String, String>(4);
        /*faceInfoParam.put("name", "张三");
        faceInfoParam.put("sex", "1");
        faceInfoParam.put("certificateType", "111");
        faceInfoParam.put("certificateNum", "420204199605121656");*/
        faceInfoParam.put("name", name);
        if("3".equals(sex)){
            sex="UNKNOWN";
        }
        faceInfoParam.put("sex", sex);
        faceInfoParam.put("certificateType", certificateType);
        faceInfoParam.put("certificateNum", certificateNum);
        Map<String, String> facePicParam = new HashMap<String, String>(1);

        byte[]bytes;
        String faceUrl1=null;

        try {
            bytes=  ImgCompass.convertImageToByteArray(faceUrl,200);
            faceUrl1=UploadUtil.uploadWithByte(bytes);

        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("图片上传失败！");
        }
        facePicParam.put("faceUrl", faceUrl1);
        jsonBody.put("faceGroupIndexCode",faceGroupIndexCode);
        jsonBody.put("faceInfo", faceInfoParam);
        jsonBody.put("facePic", facePicParam);
        String body1 = jsonBody.toJSONString();
        String result1 = ArtemisHttpUtil.doPostStringArtemis(path1, body1, null, null, contentType1);
        net.sf.json.JSONObject jsonObjectResult1 = net.sf.json.JSONObject.fromObject(result1);


        Map data1 = null;
        if("0".equals(jsonObjectResult1.getString("code"))){
            data1= (Map) jsonObjectResult1.get("data");
        }else{
            return Result.error("单个添加人脸接口出错！");
        }
        //3.插入本地的人脸库

        faceInfo.setIndexCode(data1.get("indexCode").toString());
        try {
            int i = faceDataHikService.saveSinglePersonFaceLocal(faceInfo);
        }catch (Exception e){

            //要回滚海康添加人脸的信息
            String [] str={};
            str[0]=faceInfo.getIndexCode();

            deleteFace(str,faceGroupIndexCode);

        }
        return Result.success(faceInfo,"成功!");
    }




    @RequestMapping("/snapCallBack")
    @ApiOperation(value = "抓拍的回调方法", notes = "")
    public Result snapCallBack(HttpServletRequest request, HttpServletResponse response, @RequestBody Map map){
        config();
        Map  params= (Map) map.get("params");
        List<Map> events=(List<Map>) params.get("events");
        Map data=(Map)events.get(0).get("data");
        List<Map> captureLibResult=(List<Map>)data.get("captureLibResult");
        List<Map> faces= (List<Map>) captureLibResult.get(0).get("faces");
        String url= (String) faces.get(0).get("URL");
        Map age= (Map) faces.get(0).get("age");
        String ageStr=  age.get("value").toString();
        Map gender= (Map) faces.get(0).get("gender");

        String genderStr= (String) gender.get("value");
        String genderStr2="";
        if("male".equals(genderStr)){
            genderStr2="1";
        }else if ("female".equals(genderStr)){
            genderStr2="2";
        }
        Map glass= (Map) faces.get(0).get("glass");
        String glassStr=  glass.get("value").toString();
        String glassStr2="";
        if("no".equals(glassStr)){
            glassStr2="1";
        }else{
            glassStr2="0"; //0为有眼镜
        }
        Map targetAttrs= (Map) captureLibResult.get(0).get("targetAttrs");
        String deviceId= (String) targetAttrs.get("deviceId");
        String sendTime= (String) params.get("sendTime");
        String[] strs=sendTime.split("T");

        String shootTime=strs[0]+" " +strs[1].substring(0,8);

        SnapFaceDataHik snapFaceDataHik=new SnapFaceDataHik();
        snapFaceDataHik.setDeviceId(deviceId);
        snapFaceDataHik.setAge(Integer.parseInt(ageStr));
        snapFaceDataHik.setGlasses(glassStr2);
        snapFaceDataHik.setSex(genderStr2);
        snapFaceDataHik.setImageUrl(url);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ldt = LocalDateTime.parse(shootTime,df);
        snapFaceDataHik.setShootTime(ldt);
        int i= snapFaceDataHikService.save(snapFaceDataHik);
        //TODO(插入
        if(i>0){
            return Result.success(snapFaceDataHik);
        }else{
            return    Result.error("插入失败！");
        }

    }


    @RequestMapping("/keyPersonRecognitionCallBack")
    @ApiOperation(value = "重点人识别的回调函数", notes = "")
    public Result keyPersonRecognitionCallBack(HttpServletRequest request, HttpServletResponse response, @RequestBody Map map) {
        config();
        Map params = (Map) map.get("params");
        List<Map> events = (List<Map>) params.get("events");
        Map data = (Map) events.get(0).get("data");
        Map  faceRecognitionResult = (Map )data.get("faceRecognitionResult");
        List<Map> faceMatch = (List<Map>) faceRecognitionResult.get("faceMatch");
        String faceGroupName = (String) faceMatch.get(0).get("faceGroupName");    // 人脸分组的名称
        String  faceInfoCode = (String) faceMatch.get(0).get("faceInfoCode");  // 人脸唯一标识
        String  faceInfoName= (String) faceMatch.get(0).get("faceInfoName");  // 人脸对应的名称
        String  faceInfoSex= (String) faceMatch.get(0).get("faceInfoSex");        // 性别
        String  facePicUrl= (String) faceMatch.get(0).get("facePicUrl");          //超脑返回的照片地址
        String  similarity= faceMatch.get(0).get("similarity").toString();         //相似度


        List<Map> resInfo= (List<Map>) data.get("resInfo");
        String deviceCode=resInfo.get(0).get("indexCode").toString();


        String getRootApi = ARTEMIS_PATH + "/api/resource/v1/cameras/indexCode";
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("http://", getRootApi);//根据现场环境部署确认是http还是https
            }
        };
        String contentType = "application/json";
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("cameraIndexCode", "deviceCode");
        String body = jsonBody.toJSONString();
        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, contentType);
        net.sf.json.JSONObject jsonObjectResult = net.sf.json.JSONObject.fromObject(result);





        if("黑名单".equals(faceGroupName)){
            // 报警
        }

        if("陌生人".equals(faceGroupName)){

            //加入到陌生人访问记录里
        }


        return  null;
    }

    @RequestMapping("/addSingleFace")
    @ApiOperation(value = "单个添加人脸", notes = "")
    public Result addSingleFace(FaceInfo faceInfo) {
        config();
        String getRootApi = ARTEMIS_PATH + "/api/frs/v1/face/single/addition";
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
        Map<String, String> faceInfoParam = new HashMap<String, String>(4);
        faceInfoParam.put("name",faceInfo.getName() );
        String sex=faceInfo.getSex();
        if("3".equals( faceInfo.getSex()) ){
            sex="UNKNOWN";
        } //UNKNOWN 	未知    1 	男性     2 	女性


        faceInfoParam.put("sex",sex );
        faceInfoParam.put("certificateType", faceInfo.getCertificateType());
        faceInfoParam.put("certificateNum", faceInfo.getCertificateNum());
        Map<String, String> facePic = new HashMap<String, String>(1);
        facePic.put("faceUrl", faceInfo.getFaceUrl());
        jsonBody.put("faceGroupIndexCode", faceInfo.getFaceGroupIndexCode());
        jsonBody.put("faceInfo", faceInfoParam);
        jsonBody.put("facePic", facePic);
        String body = jsonBody.toJSONString();
        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, contentType);
        net.sf.json.JSONObject jsonToken = net.sf.json.JSONObject.fromObject(result);
        if ("0".equals(jsonToken.getString("code"))) {
            System.out.println("----------" + jsonToken.getString("data"));
        }
        return Result.success(jsonToken);
    }


    @RequestMapping("/deleteFace")
    @ApiOperation(value = "按条件删除人脸", notes = "")
    public Result deleteFace(String[] indexCodes, String faceGroupIndexCode) {
        /**
         * STEP1：设置平台参数，根据实际情况,设置host appkey appsecret 三个参数.
         */

        config();
        String getRootApi = ARTEMIS_PATH + "/api/frs/v1/face/deletion";
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
         *
         */
        JSONObject jsonBody = new JSONObject();

        List<String> list = new ArrayList<String>();
        if(indexCodes!=null && indexCodes.length>0) {
            for (int i = 0; i < indexCodes.length; i++) {
                list.add(indexCodes[i]);
            }
        }else{

        }
        jsonBody.put("indexCodes", list);
        jsonBody.put("faceGroupIndexCode", faceGroupIndexCode);

        String body = jsonBody.toJSONString();

        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, contentType);
        net.sf.json.JSONObject jsonToken = net.sf.json.JSONObject.fromObject(result);
        if ("0".equals(jsonToken.getString("code"))) {

            JSONArray array = JSONArray.fromObject(jsonToken.getString("data"));


        }
        return Result.success("成功！");


    }


    @RequestMapping("/queryFace")
    @ApiOperation(value = "按条件查询人脸", notes = "")
    public Result queryFace(String[] indexCodes, FaceInfo faceInfo, int pageSize, int pageNo) {
        config();
        String getRootApi = ARTEMIS_PATH + "/api/frs/v1/face";
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("https://", getRootApi);//根据现场环境部署确认是http还是https
            }
        };

        String contentType = "application/json";
        JSONObject jsonBody = new JSONObject();
        List<String> arr = new ArrayList<>();
        if (indexCodes != null && indexCodes.length > 0) {
            for (int i = 0; i < indexCodes.length; i++) {
                arr.add(indexCodes[i]);
            }
        }else{

        }

        jsonBody.put("certificateNum", faceInfo.getCertificateNum());
        jsonBody.put("certificateType", faceInfo.getCertificateType());
        jsonBody.put("faceGroupIndexCode", faceInfo.getFaceGroupIndexCode());
        jsonBody.put("indexCodes", arr);
        jsonBody.put("name", faceInfo.getName());
        jsonBody.put("pageNo", pageNo);
        jsonBody.put("pageSize", pageSize);
        String sex=faceInfo.getSex();
        if("3".equals(sex)){
            sex="UNKNOWN";
        }
        jsonBody.put("sex", sex);
        String body = jsonBody.toJSONString();
        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, contentType);
        net.sf.json.JSONObject jsonToken = net.sf.json.JSONObject.fromObject(result);
        List<CameraLb> list = new ArrayList<>();
        if ("0".equals(jsonToken.getString("code"))) {
            System.out.println("----------" + jsonToken.getString("data"));
        }
        return Result.success(list);
    }

    @RequestMapping("/updateSingleFace")
    @ApiOperation(value = "单个修改人脸", notes = "")
    public Result updateSingleFace(FaceInfo faceInfo) {
        config();
        String getRootApi = ARTEMIS_PATH + "/api/frs/v1/face/group/single/update";
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("https://", getRootApi);//根据现场环境部署确认是http还是https
            }
        };

        String contentType = "application/json";
        JSONObject jsonBody = new JSONObject();
        Map<String, String> faceInfo1 = new HashMap<String, String>();
        faceInfo1.put("name", faceInfo.getName());
        faceInfo1.put("sex", faceInfo.getSex());
        faceInfo1.put("certificateType", faceInfo.getCertificateType());
        faceInfo1.put("certificateNum", faceInfo.getCertificateNum());
        Map<String, String> facePic = new HashMap<String, String>();
        facePic.put("faceUrl", faceInfo.getFaceUrl());
        jsonBody.put("indexCode", faceInfo.getIndexCode());
        jsonBody.put("faceInfo", faceInfo1);
        jsonBody.put("facePic", facePic);
        String body = jsonBody.toJSONString();
        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, contentType);
        net.sf.json.JSONObject jsonToken = net.sf.json.JSONObject.fromObject(result);
        if ("0".equals(jsonToken.getString("code"))) {
            System.out.println("----------" + jsonToken.getString("data"));

        }
        return Result.success(faceInfo);


    }


    @RequestMapping("/pictureOneToManySearch")
    @ApiOperation(value = "人脸分组1VN检索", notes = "minSimilarity required 指定检索的最小相似度 ，searchNum 指定所有识别资源搜索张数的总和的最大值 ")
    public Result pictureOneToManySearch(FaceInfo faceInfo, String minSimilarity, String searchNum, int pageSize, int pageNo) {
        config();
        String getRootApi = ARTEMIS_PATH + "/api/frs/v1/application/oneToMany";
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("https://", getRootApi);//根据现场环境部署确认是http还是https
            }
        };
        String contentType = "application/json";
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("pageNo", pageNo);
        jsonBody.put("pageSize", pageSize);
        jsonBody.put("searchNum", searchNum);
        jsonBody.put("minSimilarity", minSimilarity);
        jsonBody.put("facePicUrl", faceInfo.getFaceUrl());
        jsonBody.put("facePicBinaryData", "string");
        jsonBody.put("name", faceInfo.getName());
        jsonBody.put("sex", faceInfo.getSex());
        jsonBody.put("certificateType", faceInfo.getCertificateType());
        jsonBody.put("certificateNum", faceInfo.getCertificateNum());
        List<String> faceGroupIndexCodes = new ArrayList<String>();
        faceGroupIndexCodes.add("5dc82633-a4cb-4107-b55e-f21bbdf952f5");
        jsonBody.put("faceGroupIndexCodes", faceGroupIndexCodes);
        String body = jsonBody.toJSONString();
        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, contentType);
        net.sf.json.JSONObject jsonToken = net.sf.json.JSONObject.fromObject(result);
        if ("0".equals(jsonToken.getString("code"))) {
            System.out.println("----------" + jsonToken.getString("data"));
        }
        return Result.success(faceInfo);


    }


    @RequestMapping("/downloadPicture")
    @ApiOperation(value = "图片下载", notes = "url 图片地址  ")
    public Result downloadPicture(String url) {
        config();
        String getRootApi = ARTEMIS_PATH + "/api/frs/v1/application/picture";
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("https://", getRootApi);//根据现场环境部署确认是http还是https
            }
        };
        String contentType = "application/json";
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("url", "http://192.168.1.231/picture/Streaming/tracks/203/?name=ch0002_02000000019004693964800248873&size=248873");
        String body = jsonBody.toJSONString();
        HttpResponse result = ArtemisHttpUtil.doPostStringImgArtemis(path, body, null, null, contentType, null);
        System.out.println(result);
        try {
            HttpResponse resp = result;
            if (200 == resp.getStatusLine().getStatusCode()) {
                HttpEntity entity = resp.getEntity();
                InputStream in = entity.getContent();
                Tools.savePicToDisk(in, "d:/", "test3.jpg");
                System.out.println("下载成功");
            } else {
                System.out.println("下载出错");
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }



}




