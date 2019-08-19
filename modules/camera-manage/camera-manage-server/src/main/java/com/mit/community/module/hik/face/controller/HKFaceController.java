package com.mit.community.module.hik.face.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mit.community.entity.com.mit.community.entity.hik.FaceInfo;
import com.mit.community.entity.com.mit.community.entity.hik.SnapFaceDataHik;
import com.mit.community.service.ConfigInfoService;
import com.mit.community.service.com.mit.community.service.hik.FaceDataHikService;
import com.mit.community.service.com.mit.community.service.hik.SnapFaceDataHikService;
import com.mit.community.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import util.Base64Util;

import javax.servlet.http.HttpServletResponse;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@Slf4j
@Api(tags = "海康人脸")
@RequestMapping(value = "/hkFace",method = RequestMethod.POST)
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
    @ApiOperation(value = "把人脸信息加入到HK人脸库并插入自己的数据库", notes = "")
    public Result addFaceToHK(FaceInfo faceInfo){
        String faceGroupName=faceInfo.getFaceGroupName();
        String faceGroupIndexCode= queryFaceGroup(null,faceGroupName);
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
        String contentType = "application/json";
        JSONObject jsonBody = new JSONObject();
        Map<String, String> faceInfoParam = new HashMap<String, String>(4);
        faceInfoParam.put("name", name);
        if("3".equals(sex)){
            sex="UNKNOWN";
        }
        faceInfoParam.put("sex", sex);
        faceInfoParam.put("certificateType", certificateType);
        faceInfoParam.put("certificateNum", certificateNum);
        Map<String, String> facePicParam = new HashMap<String, String>(1);
        byte[]bytes;
        URL url=null;
        try {
            url = new URL(faceInfo.getFaceUrl());
            int size=ImgCompass.showUrlLens(faceInfo.getFaceUrl(),200);
            if (size<=200*1024){
                facePicParam.put("faceUrl",faceInfo.getFaceUrl());
            }else{
                 bytes= ImgCompass.convertImageToByteArray(faceInfo.getFaceUrl(),200);
                faceUrl=UploadUtil.uploadWithByte(bytes);
                facePicParam.put("faceUrl",faceUrl);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return Result.error("图片上传失败！");
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("图片上传失败！");
        }
        jsonBody.put("faceGroupIndexCode",faceGroupIndexCode);
        jsonBody.put("faceInfo", faceInfoParam);
        jsonBody.put("facePic", facePicParam);
        String body = jsonBody.toJSONString();
        String result = ArtemisHttpUtil.doPostStringArtemis(path1, body, null, null, contentType);
        net.sf.json.JSONObject jsonObjectResult1 = net.sf.json.JSONObject.fromObject(result);
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


    @RequestMapping(value = "/snapCallBack", method = RequestMethod.POST )
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
        }else{
            genderStr2="3";
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
    @RequestMapping(value="/addSingleFace" , method = RequestMethod.POST)
    @ApiOperation(value = "单个添加人脸到海康人脸库", notes = "")
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
        URL url = null;
        try {
            url = new URL(faceInfo.getFaceUrl());
            int size=ImgCompass.showUrlLens(faceInfo.getFaceUrl(),200);
            if (size<=200*1024){
                facePic.put("faceUrl",faceInfo.getFaceUrl());
            }else{
                byte[] bytes= ImgCompass.convertImageToByteArray(faceInfo.getFaceUrl(),200);
                String faceUrl=UploadUtil.uploadWithByte(bytes);
                facePic.put("faceUrl",faceUrl);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

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

            JSONArray array = JSONArray.parseArray(jsonToken.getString("data"));
        }
        return Result.success("成功！");


    }

    @RequestMapping("/queryFace")
    @ApiOperation(value = "按条件查询人脸", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "indexCodes", value = "indexCodes 通过人脸的唯一标识集合查询指定的人脸集合", paramType = "query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "certificateNum", value = "certificateNum 人脸的证件号码模糊查询", paramType = "query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "certificateType", value = "certificateType 人脸的证件类型搜索", paramType = "query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "faceGroupIndexCode", value = "faceGroupIndexCode 根据人脸所属的分组搜索该分组下符合条件的人脸", paramType = "query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "certificateType", value = "certificateType 人脸的证件类型搜索", paramType = "query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "name", value = "name 人脸名称模糊查询", paramType = "query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "pageNo", value = "pageNo 分页查询条件，页码，为空时，等价于1，页码不能小于1或大于1000", paramType = "query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize 分页查询条件，页尺，为空时，等价于1000，页尺不能小于1或大于1000", paramType = "query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "sex", value = "sex 性别搜索,1代表男性、2代表女性、UNKNOWN代表未知", paramType = "query", required = false, dataType = "String"),

    })
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
        int len=0;
        if(indexCodes!=null) {
             len = indexCodes.length;
        }
        String [] arr =new String[len];
        if (indexCodes != null && indexCodes.length > 0) {
            for (int i = 0; i < indexCodes.length; i++) {
                arr[i]=indexCodes[i];
            }
        }else{

        }
        String faceGroupName=faceInfo.getFaceGroupName();
        String faceGroupIndexCode= queryFaceGroup(null,faceGroupName);
        jsonBody.put("certificateNum", faceInfo.getCertificateNum());
        jsonBody.put("certificateType", faceInfo.getCertificateType());
        jsonBody.put("faceGroupIndexCode", faceGroupIndexCode);
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
        JSONObject jsonToken = (JSONObject) JSONObject.parse(result);
        //JSONArray jsonArray=null;
        JSONObject data=null;
        List<Map> list = new ArrayList<>();
        if ("0".equals(jsonToken.getString("code"))) {
             data=(JSONObject)jsonToken.get("data");
            list= (List)data.get("list");
            System.out.println("----------" + jsonToken.getString("data"));
        }
        return Result.success(data);
    }

    @RequestMapping("/updateSingleFace")
    @ApiOperation(value = "单个修改人脸", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "indexCode", value = "indexCode 人脸的唯一标识", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "name", value = "name 人脸的名称", paramType = "query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "certificateType", value = "certificateType 人脸的证件类型搜索", paramType = "query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "faceGroupIndexCode", value = "faceGroupIndexCode 根据人脸所属的分组搜索该分组下符合条件的人脸", paramType = "query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "certificateType", value = "certificateType 人脸的证件类型搜索", paramType = "query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "name", value = "name 人脸名称模糊查询", paramType = "query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "sex", value = "sex 性别搜索,1代表男性、2代表女性、UNKNOWN代表未知", paramType = "query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "faceUrl", value = "faceUrl 图片地址", paramType = "query", required = false, dataType = "String")

    })
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
        if(faceInfo.getName()==null && ! "".equals(faceInfo.getName())){
        faceInfo1.put("name", faceInfo.getName());
        }
        if(faceInfo.getSex()!=null  && !"".equals(faceInfo.getSex()) ) {
            faceInfo1.put("sex", faceInfo.getSex());
        }
        if(faceInfo.getCertificateType()!=null  && !"".equals(faceInfo.getCertificateType()) ) {
            faceInfo1.put("certificateType", faceInfo.getCertificateType());
        }
        if(faceInfo.getCertificateNum()!=null  && !"".equals(faceInfo.getCertificateNum()) ) {
            faceInfo1.put("certificateNum", faceInfo.getCertificateNum());
        }


        jsonBody.put("indexCode", faceInfo.getIndexCode());
        jsonBody.put("faceInfo", faceInfo1);
        if(faceInfo.getFaceUrl()!=null  && !"".equals(faceInfo.getFaceUrl()) ) {
            Map<String, String> facePic = new HashMap<String, String>();
            facePic.put("faceUrl", faceInfo.getFaceUrl());
            jsonBody.put("facePic", facePic);
        }

        String body = jsonBody.toJSONString();
        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, contentType);
        net.sf.json.JSONObject jsonToken = net.sf.json.JSONObject.fromObject(result);
        if ("0".equals(jsonToken.getString("code"))) {
            System.out.println("----------" + jsonToken.getString("data"));
        }
        return Result.success(faceInfo);


    }
    @RequestMapping("/oneToOne")
    @ApiOperation(value = "人脸图片1V1比对", notes = "srcFacePicBinaryData 原始图（Base64编码） srcFacePicUrl 原始图（url） ，distFacePicBinaryData 目标图（Base64编码）， distFacePicUrl 目标图（url） （url，base64）二选一")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "srcFacePicBinaryData", value = "srcFacePicBinaryData 原始图（Base64编码）", paramType = "query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "srcFacePicUrl", value = "srcFacePicUrl 原始图（url）", paramType = "query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "distFacePicBinaryData", value = "distFacePicBinaryData 目标图（Base64编码）", paramType = "query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "distFacePicUrl", value = "distFacePicUrl 目标图（url）", paramType = "query", required = false, dataType = "String")


    })
    public Result oneToOne(String  srcFacePicBinaryData, String srcFacePicUrl, String distFacePicBinaryData, String distFacePicUrl) {
        config();
      /*  if(minSimilarity==null ||"".equals(minSimilarity)){
            minSimilarity="75";
        }*/
        String getRootApi = ARTEMIS_PATH + "/api/frs/v1/application/oneToOne";
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("https://", getRootApi);//根据现场环境部署确认是http还是https
            }
        };
        String contentType = "application/json";
        JSONObject jsonBody = new JSONObject();

        int srclen=0;
        int distlen=0;
        byte[] srcbytes;
        byte[] distbytes;
        String base64SrcFace="";
        String base64distFace="";
        String faceUrl1=null;
        try {
           srclen=ImgCompass.showUrlLens(srcFacePicUrl,200);
           if(srclen<=4*1024*1024){
               srcbytes=   ImgCompass.showUrlBtyes(srcFacePicUrl);
           }else{
               srcbytes=  ImgCompass.convertImageToByteArray(srcFacePicUrl,4*1024*1024);
           }
            distlen=ImgCompass.showUrlLens(distFacePicUrl,200);
            if(distlen<=4*1024*1024){
                distbytes=   ImgCompass.showUrlBtyes(srcFacePicUrl);
            }else{
                distbytes=  ImgCompass.convertImageToByteArray(srcFacePicUrl,4*1024*1024);
            }
            base64SrcFace=  Base64Util.encode(srcbytes);
            base64distFace=Base64Util.encode(distbytes);

        } catch (IOException e) {
            e.printStackTrace();
        }
        jsonBody.put("srcFacePicBinaryData", base64SrcFace);
        jsonBody.put("srcFacePicUrl", srcFacePicUrl);

        jsonBody.put("distFacePicBinaryData", base64distFace);
        jsonBody.put("distFacePicUrl", distFacePicUrl);
        String body = jsonBody.toJSONString();
        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, contentType);
        net.sf.json.JSONObject jsonToken = net.sf.json.JSONObject.fromObject(result);
        Map data=null;
        if ("0".equals(jsonToken.getString("code"))) {
             data= (Map) jsonToken.get("data");
          //  list= (List<Map>) data.get("list");

            System.out.println("----------" + jsonToken.getString("data"));
        }
        return Result.success(data);
    }

    @RequestMapping("/pictureOneToManySearch")
    @ApiOperation(value = "人脸分组1VN检索", notes = "minSimilarity required 指定检索的最小相似度 ，searchNum 指定所有识别资源搜索张数的总和的最大值 ")
    public Result pictureOneToManySearch(FaceInfo faceInfo, String minSimilarity, String searchNum, int pageSize, int pageNo) {
        config();
        if(minSimilarity==null ||"".equals(minSimilarity)){
            minSimilarity="75";
        }
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
       // jsonBody.put("facePicUrl", faceInfo.getFaceUrl());
        String faceUrl=faceInfo.getFaceUrl();
        if(faceUrl==null || "".equals(faceUrl)){
            return Result.error("faceUrl为空!");
        }
        String faceGroupName=faceInfo.getFaceGroupName();
        String faceGroupIndexCode= queryFaceGroup(null,faceGroupName);

        byte[]bytes;
        String faceUrl1=null;
        try {
            bytes=  ImgCompass.convertImageToByteArray(faceUrl,200);
            faceUrl1=  Base64Util.encode(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        jsonBody.put("facePicBinaryData", faceUrl1);
        jsonBody.put("name", faceInfo.getName());
        jsonBody.put("sex", faceInfo.getSex());
        jsonBody.put("certificateType", faceInfo.getCertificateType());
        jsonBody.put("certificateNum", faceInfo.getCertificateNum());
        List<String> faceGroupIndexCodes = new ArrayList<String>();
       // faceGroupIndexCodes.add("5dc82633-a4cb-4107-b55e-f21bbdf952f5");
        faceGroupIndexCodes.add(faceGroupIndexCode);
        jsonBody.put("faceGroupIndexCodes", faceGroupIndexCodes);
        String body = jsonBody.toJSONString();
        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, contentType);
        net.sf.json.JSONObject jsonToken = net.sf.json.JSONObject.fromObject(result);
        List<Map> list=new ArrayList<>();
        if ("0".equals(jsonToken.getString("code"))) {
            Map data= (Map) jsonToken.get("data");
            list= (List<Map>) data.get("list");

            System.out.println("----------" + jsonToken.getString("data"));
        }
        return Result.success(list);
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

    @RequestMapping("/imgCompass")
    @ApiOperation(value = "图片压缩", notes = "")
    public Result imgCompass(String faceUrl){

        byte[]bytes;
        String faceUrl1=null;

        try {
            bytes=  ImgCompass.convertImageToByteArray(faceUrl,200);

            faceUrl1=  Base64Util.encode(bytes);

           // faceUrl1=UploadUtil.uploadWithByte(bytes);

        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("图片上传失败！");
        }


        return Result.success(faceUrl1,"成功!");
    }


    public String  queryFaceGroup(String[] indexCodes, String name ) {
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
        String faceGroupIndexCode="";
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("indexCodes", arr);
        jsonBody.put("name", name);
        String body = jsonBody.toJSONString();
        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, contentType);
        net.sf.json.JSONObject jsonToken = net.sf.json.JSONObject.fromObject(result);
        List<Map> list = new ArrayList<>();
        if ("0".equals(jsonToken.getString("code"))) {
            list = (List) jsonToken.get("data");
            if(list!=null && list.size()>0){
                faceGroupIndexCode=list.get(0).get("indexCode").toString();
            }
        }
        return faceGroupIndexCode;

    }




    @RequestMapping("/previewURLs")
    @ApiOperation(value = "获取监控点回放取流URL", notes = "获取监控点回放取流URL")
    public Result previewURLs(String  cameraIndexCode, String recordLocation, String protocol, String transmode, String  beginTime, String  endTime) {
        config();
        String getRootApi = ARTEMIS_PATH + "/api/video/v1/cameras/playbackURLs";
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("https://", getRootApi);//根据现场环境部署确认是http还是https
            }
        };
        String contentType = "application/json";
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("cameraIndexCode",cameraIndexCode );
        //存储类型：
        //0：中心存储
        //1：设备存储
        //默认为中心存储
        if(recordLocation!=null && !"".equals(recordLocation)){

        }
   /*
   取流协议（应用层协议)：
“rtsp”:RTSP协议
“rtmp”:RTMP协议
“hls”:HLS协议（HLS协议只支持海康SDK协议、EHOME协议、ONVIF协议接入的设备；只支持H264视频编码和AAC音频编码；
云存储版本要求v2.2.4及以上的2.x版本，或v3.0.5及以上的3.x版本；ISC版本要求v1.2.0版本及以上，
需在运管中心-视频联网共享中切换成启动平台内置VOD）
参数不填，默认为RTSP协议
   * */
        if(protocol!=null && !"".equals(protocol)){
            jsonBody.put("protocol", protocol);
        }

        if(transmode!=null && !"".equals(transmode)){
            jsonBody.put("transmode", transmode);
        }
        jsonBody.put("beginTime", beginTime);
        jsonBody.put("endTime", endTime);
        jsonBody.put("expand","streamform=rtp" );
        String body = jsonBody.toJSONString();
        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, contentType);

        net.sf.json.JSONObject jsonToken = net.sf.json.JSONObject.fromObject(result);
        List<Map> list=new ArrayList<>();
        if ("0".equals(jsonToken.getString("code"))) {

            Map data = (Map) jsonToken.get("data");
            list= (List<Map>) data.get("list");

            System.out.println("----------" + jsonToken.getString("data"));
        }
        return Result.success("成功");
    }

}




