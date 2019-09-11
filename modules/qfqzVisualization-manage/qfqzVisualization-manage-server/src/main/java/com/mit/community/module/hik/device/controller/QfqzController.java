package com.mit.community.module.hik.device.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mit.api.vo.user.UserInfo;
import com.mit.community.entity.DepartmentInfo;
import com.mit.community.entity.PosInfo;
import com.mit.community.entity.QfqzUrl;
import com.mit.community.service.QfqzUrlService;
import com.mit.community.service.SmokeDetectorStatusService;

import com.mit.community.util.Result;
import com.sun.org.apache.bcel.internal.generic.NEW;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import net.sf.json.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.naming.Name;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static java.lang.Thread.sleep;

/**
 * 烟感感知
 *
 * @author shuyy
 * @date 2019-01-04
 * @company mitesofor
 */
@RestController
@RequestMapping(value = "/qfqzVisualization")
@Slf4j
@Api(value = "群防群治可视化系统", tags = {"群防群治可视化系统"})
public class QfqzController {

    @Autowired
    private SmokeDetectorStatusService smokeDetectorStatusService;

    @Autowired
    private QfqzUrlService qfqzUrlService;


 /*   *//**
     * 分页
     *
     * @param deviceNum
     * @param devicePalce
     * @param pageNum
     * @param pageSize
     * @return com.mit.community.rest.util.Result
     * @throws
     * @author shuyy
     * @date 2019-01-04 14:53
     * @company mitesofor
     *//*
    @RequestMapping(value = "/listPage", method = RequestMethod.POST)
    @ApiOperation(value = "分页查询", notes = "status 水压情况（压力不足、缺水、压力过大、水压正常）" +
            "device_status 设备状态（正常，故障，掉线）")
    public Result listPage(String communityCode,
                           String deviceNum, String devicePalce, Short status,
                           Short deviceStatus,
                           @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate gmtUploadStart,
                           @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate gmtUploadEnd,
                           Integer pageNum,
                           Integer pageSize) {
     *//*   Page<SmokeDetectorStatus> page = smokeDetectorStatusService.listPage(communityCode, deviceNum,
                devicePalce, status, deviceStatus, gmtUploadStart, gmtUploadEnd, pageNum, pageSize);*//*
      *//*  return Result.success(page);*//*
      return null;
    }

    @RequestMapping(value = "/insertData", method = RequestMethod.POST)
    @ApiOperation(value = "插入数据", notes = "插入数据")
    public Result insertData() {
//        smokeDetectorStatusService.delete(null);
     *//*   smokeDetectorStatusService.insertDataKXWT();
        smokeDetectorStatusService.insertDataXJB();
        smokeDetectorStatusService.insertDataYWHDHY();*//*
        smokeDetectorStatusService.insertDataNY();
        return Result.success("成功");
    }
*/

   @RequestMapping(value = "/queryUserInfos", method = RequestMethod.POST)
    @ApiOperation(value = "查询设备列表", notes = "查询设备列表")
    public Result queryUserInfos(String departmentId) {
      String url="http://120.76.189.28:1241/queryUserInfos";
      String serviceKey="27bf1c196a1f4b99bab5eff3d7e9bc45";
      String seq=UUID.randomUUID().toString();
      String sign=getSHA256Str(serviceKey+seq);
      //NameValuePair[] data =null;
      NameValuePair[] data = {new NameValuePair("seq", seq),
               new NameValuePair("sign", sign),
               new NameValuePair("departmentId", "6014"),
               new NameValuePair("currPageIndex", "1"),
               new NameValuePair("pageSize", "1000")

      };
       HttpClient httpClient = new HttpClient();
       httpClient.getParams().setBooleanParameter("http.protocol.expect-continue", false);
       httpClient.getParams().setContentCharset("utf-8");
       PostMethod postMethod = new PostMethod(url);
       postMethod.addRequestHeader("Connection", "close");
       postMethod.setRequestBody(data);

       httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
       List<UserInfo> list=new ArrayList<>();
       String result = StringUtils.EMPTY;
       try {
           int status = httpClient.executeMethod(postMethod);
           int healthStatus = 200;
           if (status != healthStatus) {
               return null;
           }
           BufferedReader reader = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream()));
           StringBuilder stringBuffer = new StringBuilder();
           String str;
           while ((str = reader.readLine()) != null) {
               stringBuffer.append(str);
           }
           result = stringBuffer.toString();
           JSONObject jsonObject=JSONObject.fromObject(result);
           if("0".equals(jsonObject.get("code").toString())){
               list= (List<UserInfo>) jsonObject.get("userInfos");
           }
       } catch (Exception e) {
           log.error("发送post请求错误", e);
           return Result.error("发送post请求错误");
       } finally {
           postMethod.releaseConnection();
       }
       return Result.success(list);
    }



    @RequestMapping(value = "/queryDepartmentInfos", method = RequestMethod.POST)
    @ApiOperation(value = "查询部门列表", notes = "查询部门列表")
    public Result queryDepartmentInfos() {
        String url="http://120.76.189.28:1241/queryDepartmentInfos";
        String serviceKey="27bf1c196a1f4b99bab5eff3d7e9bc45";
        String seq=UUID.randomUUID().toString();
        String sign=getSHA256Str(serviceKey+seq);
        //NameValuePair[] data =null;
        NameValuePair[] data = {new NameValuePair("seq", seq),
                new NameValuePair("sign", sign)

        };
        HttpClient httpClient = new HttpClient();
        httpClient.getParams().setBooleanParameter("http.protocol.expect-continue", false);
        httpClient.getParams().setContentCharset("utf-8");
        PostMethod postMethod = new PostMethod(url);
        postMethod.addRequestHeader("Connection", "close");
        postMethod.setRequestBody(data);

        httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
        List<DepartmentInfo> list=new ArrayList<>();
        String result = StringUtils.EMPTY;
        try {
            int status = httpClient.executeMethod(postMethod);
            int healthStatus = 200;
            if (status != healthStatus) {
                return null;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream()));
            StringBuilder stringBuffer = new StringBuilder();
            String str;
            while ((str = reader.readLine()) != null) {
                stringBuffer.append(str);
            }
            result = stringBuffer.toString();
            JSONObject jsonObject=JSONObject.fromObject(result);
            if("0".equals(jsonObject.get("code").toString())){
                list= (List<DepartmentInfo>) jsonObject.get("departmentInfos");
            }
        } catch (Exception e) {
            log.error("发送post请求错误", e);
            return Result.error("发送post请求错误");
        } finally {
            postMethod.releaseConnection();
        }
        return Result.success(list);
    }


    @RequestMapping(value = "/queryOnlinePosInfos", method = RequestMethod.POST)
    @ApiOperation(value = "查询所有在线设备最新位置信息", notes = "查询所有在线设备最新位置信息")
    public Result queryOnlinePosInfos(String departmentId ) {
        String url="http://120.76.189.28:1241/queryOnlinePosInfos";
        String serviceKey="27bf1c196a1f4b99bab5eff3d7e9bc45";
        String seq=UUID.randomUUID().toString();
        String sign=getSHA256Str(serviceKey+seq);
        //NameValuePair[] data =null;
        NameValuePair[] data = {new NameValuePair("seq", seq),
                new NameValuePair("sign", sign),
                new NameValuePair("departmentId", "6014"),
                new NameValuePair("currPageIndex", "1"),
                new NameValuePair("pageSize", "1000")

        };
        HttpClient httpClient = new HttpClient();
        httpClient.getParams().setBooleanParameter("http.protocol.expect-continue", false);
        httpClient.getParams().setContentCharset("utf-8");
        PostMethod postMethod = new PostMethod(url);
        postMethod.addRequestHeader("Connection", "close");
        postMethod.setRequestBody(data);

        httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
        List<PosInfo> list=new ArrayList<>();
        String result = StringUtils.EMPTY;
        try {
            int status = httpClient.executeMethod(postMethod);
            int healthStatus = 200;
            if (status != healthStatus) {
                return null;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream()));
            StringBuilder stringBuffer = new StringBuilder();
            String str;
            while ((str = reader.readLine()) != null) {
                stringBuffer.append(str);
            }
            result = stringBuffer.toString();
            JSONObject jsonObject=JSONObject.fromObject(result);
            if("0".equals(jsonObject.get("code").toString())){
                list= (List<PosInfo>) jsonObject.get("posInfos");
            }
        } catch (Exception e) {
            log.error("发送post请求错误", e);
            return Result.error("发送post请求错误");
        } finally {
            postMethod.releaseConnection();
        }
        return Result.success(list);
    }

    @RequestMapping(value = "/queryPosInfos", method = RequestMethod.POST)
    @ApiOperation(value = "查询设备轨迹信息", notes = "查询设备轨迹信息")
    public Result queryPosInfos(String userId,String date) {
        String url="http://120.76.189.28:1241/queryPosInfos";
        String serviceKey="27bf1c196a1f4b99bab5eff3d7e9bc45";
        String seq=UUID.randomUUID().toString();
        String sign=getSHA256Str(serviceKey+seq);

        //NameValuePair[] data =null;
        NameValuePair[] data = {new NameValuePair("seq", seq),
                new NameValuePair("sign", sign),
                new NameValuePair("userId", userId),
                new NameValuePair("date", date),
                new NameValuePair("currPageIndex", "1"),
                new NameValuePair("pageSize", "1000")

        };
        HttpClient httpClient = new HttpClient();
        httpClient.getParams().setBooleanParameter("http.protocol.expect-continue", false);
        httpClient.getParams().setContentCharset("utf-8");
        PostMethod postMethod = new PostMethod(url);
        postMethod.addRequestHeader("Connection", "close");
        postMethod.setRequestBody(data);

        httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
        List<PosInfo> list=new ArrayList<>();
        String result = StringUtils.EMPTY;
        try {
            int status = httpClient.executeMethod(postMethod);
            int healthStatus = 200;
            if (status != healthStatus) {
                return null;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream()));
            StringBuilder stringBuffer = new StringBuilder();
            String str;
            while ((str = reader.readLine()) != null) {
                stringBuffer.append(str);
            }
            result = stringBuffer.toString();
            JSONObject jsonObject=JSONObject.fromObject(result);
            if("0".equals(jsonObject.get("code").toString())){
                list= (List<PosInfo>) jsonObject.get("posInfos");
            }
        } catch (Exception e) {
            log.error("发送post请求错误", e);
            return Result.error("发送post请求错误");
        } finally {
            postMethod.releaseConnection();
        }
        return Result.success(list);
    }




    @RequestMapping(value = "/startVideo", method = RequestMethod.POST)
    @ApiOperation(value = "开启终端视频", notes = "开启终端视频")
    public Result startVideo(String userId ,String seq) {

      //  userId="21451";
        seq=UUID.randomUUID().toString();
        String serviceKey="27bf1c196a1f4b99bab5eff3d7e9bc45";
        // seq="12345678";
        String sign=getSHA256Str(serviceKey+seq);
        String callBackUrl="http://120.79.67.123:8240/qfqzVisualization/videoCallBack";
        String url="http://120.76.189.28:1241/startVideo";
       // String url="http://120.76.189.28:1241/startVideo?seq="+seq+"&sign="+sign+"&userId="+userId+"&callBackUrl="+callBackUrl;
        //NameValuePair[] data =null;
        NameValuePair[] data = {new NameValuePair("seq", seq),
                new NameValuePair("sign", sign),
                new NameValuePair("userId", userId),
                new NameValuePair("callBackUrl", "http://120.79.67.123:8240/cloud-service/qfqzVisualization/videoCallBack")


        };
        HttpClient httpClient = new HttpClient();
        httpClient.getParams().setBooleanParameter("http.protocol.expect-continue", false);
        httpClient.getParams().setContentCharset("utf-8");
       /* String charSetName = "utf-8";
        // 创建一个方法实例.
        GetMethod getMethod = new GetMethod(url);
        getMethod.addRequestHeader( "Connection", "close");
        // 提供定制的重试处理程序是必要的
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));*/
       /* postMethod.setRequestBody(data);*/

        PostMethod postMethod = new PostMethod(url);
        postMethod.addRequestHeader("Connection", "close");
        postMethod.setRequestBody(data);
        httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
        List<PosInfo> list=new ArrayList<>();
        String result = StringUtils.EMPTY;
        QfqzUrl qfqzUrl=null;
        try {
            int status = httpClient.executeMethod(postMethod);
            int healthStatus = 200;
            if (status != healthStatus) {
                return null;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream()));
            StringBuilder stringBuffer = new StringBuilder();
            String str;
            while ((str = reader.readLine()) != null) {
                stringBuffer.append(str);
            }
            result = stringBuffer.toString();
            JSONObject jsonObject=JSONObject.fromObject(result);
            if("0".equals(jsonObject.get("code").toString())){
                sleep(4000);

                EntityWrapper<QfqzUrl> wrapper=new EntityWrapper<>();
                wrapper.eq("userId",userId);

                qfqzUrl=qfqzUrlService.selectOne(wrapper);
            }
        } catch (Exception e) {
            log.error("发送post请求错误", e);
            return Result.error("发送post请求错误");
        } finally {
            postMethod.releaseConnection();
        }
        return Result.success(qfqzUrl);
    }

    @RequestMapping(value = "/stopVideo", method = RequestMethod.POST)
    @ApiOperation(value = "关闭终端视频", notes = "关闭终端视频")
    public Result stopVideo(String userId) {
        String  seq=UUID.randomUUID().toString();
      //  userId="21451";
        String url="http://120.76.189.28:1241/stopVideo";
        String serviceKey="27bf1c196a1f4b99bab5eff3d7e9bc45";
       // String seq="123456";
        String sign=getSHA256Str(serviceKey+seq);

        //NameValuePair[] data =null;
        NameValuePair[] data = {new NameValuePair("seq", seq),
                new NameValuePair("sign", sign),
                new NameValuePair("userId", userId)
        };
        HttpClient httpClient = new HttpClient();
        httpClient.getParams().setBooleanParameter("http.protocol.expect-continue", false);
        httpClient.getParams().setContentCharset("utf-8");
        PostMethod postMethod = new PostMethod(url);
        postMethod.addRequestHeader("Connection", "close");
        postMethod.setRequestBody(data);

        httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
        String result = StringUtils.EMPTY;
        try {
            int status = httpClient.executeMethod(postMethod);
            int healthStatus = 200;
            if (status != healthStatus) {
                return null;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream()));
            StringBuilder stringBuffer = new StringBuilder();
            String str;
            while ((str = reader.readLine()) != null) {
                stringBuffer.append(str);
            }
            result = stringBuffer.toString();
            JSONObject jsonObject=JSONObject.fromObject(result);
            if("0".equals(jsonObject.get("code").toString())){

            }
        } catch (Exception e) {
            log.error("发送post请求错误", e);
            return Result.error("发送post请求错误");
        } finally {
            postMethod.releaseConnection();
        }
        return Result.success("成功");
    }


    @RequestMapping(value = "/changeMaxTime", method = RequestMethod.POST)
    @ApiOperation(value = "延长视频时间", notes = "延长视频时间")
    public Result changeMaxTime(String userId,String time) {
        String  seq=UUID.randomUUID().toString();
        userId="21451";
        String url="http://120.76.189.28:1241/changeMaxTime";
        String serviceKey="27bf1c196a1f4b99bab5eff3d7e9bc45";
        // String seq="123456";
        String sign=getSHA256Str(serviceKey+seq);

        //NameValuePair[] data =null;
        NameValuePair[] data = {new NameValuePair("seq", seq),
                new NameValuePair("sign", sign),
                new NameValuePair("userId", userId),
                new NameValuePair("time", time)
        };
        HttpClient httpClient = new HttpClient();
        httpClient.getParams().setBooleanParameter("http.protocol.expect-continue", false);
        httpClient.getParams().setContentCharset("utf-8");
        PostMethod postMethod = new PostMethod(url);
        postMethod.addRequestHeader("Connection", "close");
        postMethod.setRequestBody(data);

        httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
        String result = StringUtils.EMPTY;
        try {
            int status = httpClient.executeMethod(postMethod);
            int healthStatus = 200;
            if (status != healthStatus) {
                return null;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream()));
            StringBuilder stringBuffer = new StringBuilder();
            String str;
            while ((str = reader.readLine()) != null) {
                stringBuffer.append(str);
            }
            result = stringBuffer.toString();
            JSONObject jsonObject=JSONObject.fromObject(result);
            if("0".equals(jsonObject.get("code").toString())){

            }
        } catch (Exception e) {
            log.error("发送post请求错误", e);
            return Result.error("发送post请求错误");
        } finally {
            postMethod.releaseConnection();
        }
        return Result.success("成功");
    }

    @RequestMapping(value = "/changeDefinition", method = RequestMethod.POST)
    @ApiOperation(value = "调整视频清晰度", notes = "调整视频清晰度")
    public Result changeDefinition(String userId,String defintion  ) {
        String  seq=UUID.randomUUID().toString();
       // userId="21451";
        String url="http://120.76.189.28:1241/changeDefinition";
        String serviceKey="27bf1c196a1f4b99bab5eff3d7e9bc45";
        // String seq="123456";
        String sign=getSHA256Str(serviceKey+seq);

        //NameValuePair[] data =null;
        NameValuePair[] data = {new NameValuePair("seq", seq),
                new NameValuePair("sign", sign),
                new NameValuePair("userId", userId),
                new NameValuePair("defintion", defintion)

        };
        HttpClient httpClient = new HttpClient();
        httpClient.getParams().setBooleanParameter("http.protocol.expect-continue", false);
        httpClient.getParams().setContentCharset("utf-8");
        PostMethod postMethod = new PostMethod(url);
        postMethod.addRequestHeader("Connection", "close");
        postMethod.setRequestBody(data);

        httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
        List<PosInfo> list=new ArrayList<>();
        String result = StringUtils.EMPTY;

        try {
            int status = httpClient.executeMethod(postMethod);
            int healthStatus = 200;
            if (status != healthStatus) {
                return null;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream()));
            StringBuilder stringBuffer = new StringBuilder();
            String str;
            while ((str = reader.readLine()) != null) {
                stringBuffer.append(str);
            }
            result = stringBuffer.toString();
            JSONObject jsonObject=JSONObject.fromObject(result);
            if("0".equals(jsonObject.get("code").toString())){

            }
        } catch (Exception e) {
            log.error("发送post请求错误", e);
            return Result.error("发送post请求错误");
        } finally {
            postMethod.releaseConnection();
        }
        return Result.success("成功");
    }


    public static String getSHA256Str(String str){
        MessageDigest messageDigest;
        String encdeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(str.getBytes("UTF-8"));
            encdeStr = Hex.encodeHexString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encdeStr;
    }

  /*  public static void main(String[] args) {

       String ss= getSHA256Str("9df75b2c58a4457b91857371a978e916"+"123456");
       System.out.print(ss);
    }
*/

    @RequestMapping(value = "/videoCallBack", method = RequestMethod.GET)
    @ApiOperation(value = "开启终端视频", notes = "开启终端视频")
    public Result videoCallBack(@RequestParam(value="url") String url,@RequestParam(value="userId") String userId) {

        QfqzUrl qfqzUrl=new QfqzUrl();
        qfqzUrl.setUrl(url);
        qfqzUrl.setUserId(userId);

        EntityWrapper<QfqzUrl> wrapper=new EntityWrapper<>();
        wrapper.eq("userId",userId);

        QfqzUrl qfqzUrl1Two=qfqzUrlService.selectOne(wrapper);
       if(qfqzUrl1Two==null){

           qfqzUrlService.insert(qfqzUrl);
       }else{
           qfqzUrlService.update(qfqzUrl,wrapper);
           //qfqzUrlService.updateById(qfqzUrl);
       }

        System.out.print("++++++++++++++++++++"+url+"++++++++++++++"+userId);
        return Result.success(url);

    }
    @ApiOperation(value = "查询设备轨道信息-根据日期范围查询（日期范围最大七天）")
    @ApiImplicitParams({
            @ApiImplicitParam(name="startDate",value = "查询开始日期",dataType = "String",paramType = "query",required = true),
//            @ApiImplicitParam(name = "endDate",value = "查询结束日期",dataType = "String",paramType = "query",required = true),
            @ApiImplicitParam(name = "userId",value = "设备Id",dataType = "String",paramType = "query")

    })
    @PostMapping("queryPosInfosDateRange")
    public Result queryPosInfosDateRange(String startDate,String endDate,String userId){
    PostMethod postMethod=null;
    List<PosInfo> list=new ArrayList<>();
        try {
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = format.parse(startDate);
            Date date2 = format.parse(endDate);
            long daysBetween=(date2.getTime()-date1.getTime()+1000000)/(60*60*24*1000);
            if (daysBetween>7){
                return Result.error("最多只能查询一周之内的信息");
            }
            String seq = UUID.randomUUID().toString();
            String url="http://120.76.189.28:1241/queryPosInfosDateRange";
            String serviceKey="27bf1c196a1f4b99bab5eff3d7e9bc45";
            String sign=getSHA256Str(serviceKey+seq);
            NameValuePair[] data= {new NameValuePair("seq",seq),new NameValuePair("sign",sign),
                    new NameValuePair("userId",userId),
                    new NameValuePair("startDate",startDate),
                    new NameValuePair("endDate",endDate),
                    new NameValuePair("currPageIndex","1"),new NameValuePair("pageSize","1000")};
            HttpClient httpClient=new HttpClient();
            httpClient.getParams().setBooleanParameter("http.protocol.expect-continue", false);
            httpClient.getParams().setContentCharset("utf-8");
            postMethod = new PostMethod(url);
            postMethod.addRequestHeader("Connection", "close");
            postMethod.setRequestBody(data);
            httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);

            String result = StringUtils.EMPTY;
            int status = httpClient.executeMethod(postMethod);
            int healthStatus = 200;
            if (status != healthStatus) {
                return null;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream()));
            StringBuilder stringBuffer = new StringBuilder();
            String str;
            while ((str = reader.readLine()) != null) {
                stringBuffer.append(str);
            }
            result = stringBuffer.toString();
            JSONObject jsonObject=JSONObject.fromObject(result);
            if("0".equals(jsonObject.get("code").toString())){
                list= (List<PosInfo>) jsonObject.get("posInfos");
            }else {
                return Result.error(jsonObject.get("msg").toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (postMethod != null) {
            postMethod.releaseConnection();
            }
        }
        return Result.success(list);

    }
    @ApiOperation(value = "发送文字广播")
    @PostMapping("/facadeSendBroadcast")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "content",value = "发送内容",dataType = "String",paramType = "query",required = true),
    @ApiImplicitParam(name = "userIds",value = "设备Id",dataType = "String",paramType = "query",required = true)
    })
    public Result facadeSendBroadcast(String content,String userIds){
        PostMethod postMethod=null;
        try {
            String seq = UUID.randomUUID().toString();
            String serviceKey="27bf1c196a1f4b99bab5eff3d7e9bc45";
            String sign=getSHA256Str(serviceKey+seq);
            String url="http://120.76.189.28:1241/facadeSendBroadcast";
            String encode = URLEncoder.encode(content, "UTF-8");
            NameValuePair[] data={
                    new NameValuePair("userIds",userIds),
                    new NameValuePair("content",content),
                    new NameValuePair("seq",seq),
                    new NameValuePair("sign",sign)
            };
            HttpClient httpClient= new HttpClient();
            httpClient.getParams().setBooleanParameter("http.protocol.expect-continue", false);
            httpClient.getParams().setContentCharset("utf-8");
            postMethod = new PostMethod(url);
            postMethod.addRequestHeader("Connection", "close");
            postMethod.setRequestBody(data);
            httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
            String result = StringUtils.EMPTY;
            int status = httpClient.executeMethod(postMethod);
            int healthStatus = 200;
            if (status != healthStatus) {
                return null;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream()));
            StringBuilder stringBuffer = new StringBuilder();
            String str;
            while ((str = reader.readLine()) != null) {
                stringBuffer.append(str);
            }
            result = stringBuffer.toString();
            if ("0".equals(result)){
                return Result.success("成功");
            }else{
                return Result.error("发送失败");
            }
        } catch (Exception e) {
            log.error("发送post请求错误", e);
            return Result.error("发送post请求错误");
        }finally {
            postMethod.releaseConnection();
        }
    }
    @ApiOperation(value = "删除群组")
    @ApiImplicitParam(name = "name",value = "群组名称",dataType = "String",required = true,paramType = "query")
    @PostMapping("/deleteGroup")
    public Result deleteGroup(String name){
        PostMethod postMethod=null;
        try {
            String url="https://120.76.189.28:18281/possecu_cs/thirdSystem/delete_group.htm";
            String  seq=UUID.randomUUID().toString();
            String serviceKey="27bf1c196a1f4b99bab5eff3d7e9bc45";
            String sign=getSHA256Str(serviceKey+seq);
            NameValuePair[] data = {new NameValuePair("seq", seq),
                    new NameValuePair("sign", sign),
                    new NameValuePair("name", name),
            };
            HttpClient httpClient = new HttpClient();
            httpClient.getParams().setBooleanParameter("http.protocol.expect-continue", false);
            httpClient.getParams().setContentCharset("utf-8");
            postMethod = new PostMethod(url);
            postMethod.addRequestHeader("Connection", "close");
            postMethod.setRequestBody(data);
            httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
            String result=StringUtils.EMPTY;
            int status = httpClient.executeMethod(postMethod);
            int healthStatus = 200;
            if (status != healthStatus) {
                return null;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream()));
            StringBuilder stringBuffer = new StringBuilder();
            String str;
            while ((str = reader.readLine()) != null) {
                stringBuffer.append(str);
            }
            result = stringBuffer.toString();
            JSONObject jsonObject=JSONObject.fromObject(result);
            if("0".equals(jsonObject.get("code").toString())){
                return Result.success("成功");
            }else {
                return Result.error(jsonObject.get("msg").toString());
            }
        } catch (IOException e) {
            log.error("发送post请求错误", e);
            return Result.error("发送post请求错误");
        } finally {
            postMethod.releaseConnection();
        }

    }
    @ApiOperation(value = "创建群组/添加成员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name",value = "群组名称",dataType = "String",paramType = "query",required = true),
            @ApiImplicitParam(name = "serialNumbers",value = "设备编码",dataType = "String",paramType = "query")
    })
    @PostMapping("/addGroupMember")
    public Result addGroupMember(String name,String serialNumbers){
        PostMethod postMethod=null;
        try {
//            String url="https://120.76.189.28:18281/thirdSystem/add_group_member.htm";
            String url="https://120.76.189.28:18281/possecu_cs/thirdSystem/add_group_member.htm";
            String  seq=UUID.randomUUID().toString();
            String serviceKey="27bf1c196a1f4b99bab5eff3d7e9bc45";
            String sign=getSHA256Str(serviceKey+seq);
            NameValuePair[] data = {new NameValuePair("seq", seq),
                    new NameValuePair("sign", sign),
                    new NameValuePair("name", name),
                    new NameValuePair("serialNumbers", serialNumbers)

            };
            HttpClient httpClient = new HttpClient();
            httpClient.getParams().setBooleanParameter("http.protocol.expect-continue", false);
            httpClient.getParams().setContentCharset("utf-8");
            postMethod = new PostMethod(url);
            postMethod.addRequestHeader("Connection", "close");
            postMethod.setRequestBody(data);
            httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
            String result = StringUtils.EMPTY;
            int status = httpClient.executeMethod(postMethod);
            int healthStatus = 200;
            if (status != healthStatus) {
                return null;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream()));
            StringBuilder stringBuffer = new StringBuilder();
            String str;
            while ((str = reader.readLine()) != null) {
                stringBuffer.append(str);
            }
            result = stringBuffer.toString();
            JSONObject jsonObject=JSONObject.fromObject(result);
            if("0".equals(jsonObject.get("code").toString())){
                   return Result.success("成功");
            }else {
                return Result.error(jsonObject.get("msg").toString());
            }
        } catch (IOException e) {
            log.error("发送post请求错误", e);
            return Result.error("发送post请求错误");
        } finally {
            postMethod.releaseConnection();
        }
    }
    @ApiOperation(value = "删除成员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name",value = "群组名称",dataType = "String",paramType = "query",required = true),
            @ApiImplicitParam(name = "serialNumbers",value = "设备编号",paramType = "query",dataType = "String")
    })
    @PostMapping("/deleteMember")
    public Result deleteMember(String name,String serialNumbers){
        String  seq=UUID.randomUUID().toString();

        String url="https://120.76.189.28:18281/possecu_cs/thirdSystem/delete_member.htm";
        String serviceKey="27bf1c196a1f4b99bab5eff3d7e9bc45";
        String sign=getSHA256Str(serviceKey+seq);
        //NameValuePair[] data =null;
        NameValuePair[] data = {new NameValuePair("seq", seq),
                new NameValuePair("sign", sign),
                new NameValuePair("name", name),
                new NameValuePair("serialNumbers", serialNumbers)

        };
        HttpClient httpClient = new HttpClient();
        httpClient.getParams().setBooleanParameter("http.protocol.expect-continue", false);
        httpClient.getParams().setContentCharset("utf-8");
        PostMethod postMethod = new PostMethod(url);
        postMethod.addRequestHeader("Connection", "close");
        postMethod.setRequestBody(data);

        httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
        List<PosInfo> list=new ArrayList<>();
        String result = StringUtils.EMPTY;

        try {
            int status = httpClient.executeMethod(postMethod);
            int healthStatus = 200;
            if (status != healthStatus) {
                return null;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream()));
            StringBuilder stringBuffer = new StringBuilder();
            String str;
            while ((str = reader.readLine()) != null) {
                stringBuffer.append(str);
            }
            result = stringBuffer.toString();
            JSONObject jsonObject=JSONObject.fromObject(result);
            if("0".equals(jsonObject.get("code").toString())){
                return Result.success("成功");
            }else {
                return Result.error(jsonObject.get("msg").toString());
            }
        } catch (Exception e) {
            log.error("发送post请求错误", e);
            return Result.error("发送post请求错误");
        } finally {
            postMethod.releaseConnection();
        }

    }
}