package com.mit.community.module.hik.device.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.mit.DeviceCallBackManageBootstrap;
import com.mit.community.entity.SmokeDetector;
import com.mit.community.entity.SysUser;

import com.mit.community.service.*;
import com.mit.community.util.CookieUtils;
import com.mit.community.util.Result;
import com.mit.community.util.Util;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.web.bind.annotation.*;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 *
 * @company mitesofor
 */
@RestController
@Slf4j
@Api(tags = "(nvr烟感设备回调)")
@RequestMapping(value = "/nvrYanGanDeviceCallBack")
public class NvrDeviceCallBackController {
     @Autowired
    private SmokeDetectorService smokeDetectorService;
     @Autowired
     private RedisService redisService;


    private static String token ="123456";//用户自定义token和OneNet第三方平台配置里的token一致
    private static String aeskey ="cspQqDlSI8cd+KIqZAY3296dnOo8jHw4VpGqkRR6s04=";//aeskey和OneNet第三方平台配置里的token一致

    private static Logger logger = LoggerFactory.getLogger(NvrDeviceCallBackController.class);

    /**
     * 功能描述：第三方平台数据接收。<p>
     *           <ul>注:
     *               <li>1.OneNet平台为了保证数据不丢失，有重发机制，如果重复数据对业务有影响，数据接收端需要对重复数据进行排除重复处理。</li>
     *               <li>2.OneNet每一次post数据请求后，等待客户端的响应都设有时限，在规定时限内没有收到响应会认为发送失败。
     *                    接收程序接收到数据时，尽量先缓存起来，再做业务逻辑处理。</li>
     *           </ul>
     * @param body 数据消息
     * @return 任意字符串。OneNet平台接收到http 200的响应，才会认为数据推送成功，否则会重发。
     */

    @RequestMapping(value = "/receive",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "接受回调函数", notes = "")
    public Result recieve(@RequestBody String body, HttpServletRequest request)throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {

 //      logger.info("data receive:  body String --- " + body);
        /************************************************
         *  解析数据推送请求，非加密模式。
         *  如果是明文模式使用以下代码
         **************************************************/

      /*  net.sf.json.JSONObject jsonToken = net.sf.json.JSONObject.fromObject(body);*/
    /*    HttpSession session=request.getSession();
        String sessionId=session.getId();*/

     /*   String sessionId = CookieUtils.getSessionId(request);
        SysUser user = (SysUser) redisService.get(RedisConstant.SESSION_ID + sessionId);
        if (user != null) {

        }*/
   /*     String  device_num=jsonToken.get("dev_id").toString();
        String  device_name="烟感";
       // String  device_num="00000001";
        String  msg_signature=jsonToken.get("msg_signature").toString();
        Long at =jsonToken.getLong("at");
        Date date=new Date(at);
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        // 报警时间
       LocalDateTime gmt_warn = instant.atZone(zoneId).toLocalDateTime();
     //  LocalDateTime gmt_warn=LocalDateTime.now();
        String community_code="000000001";
        String device_place="利雅轩保安室";
        String  device_type="烟感";
        int warn_type=1;
        String warn_content="利雅轩保安室出现大量烟雾,请及时处理";
        int warn_status=1;

        LocalDateTime gmt_create=LocalDateTime.now();

        SmokeDetector smokeDetector=new SmokeDetector();
        smokeDetector.setDeviceName(device_name);
        smokeDetector.setDeviceNum(device_num);
        smokeDetector.setDevicePlace(device_place);
        smokeDetector.setDeviceType(device_type);
        smokeDetector.setWarnType((short)warn_type);
        smokeDetector.setWarnContent(warn_content);
        smokeDetector.setWarnStatus((short)warn_status);
        smokeDetector.setGmtWarn(gmt_warn);
        smokeDetector.setGmtCreate(gmt_create);
        smokeDetector.setGmtModified(gmt_create);
        smokeDetector.setCommunityCode(community_code);
        Boolean b= smokeDetectorService.insert(smokeDetector);

        if(b==null){
           return Result.error("插入失败");
        }

        if(b!=null){
            return  Result.error("插入失败");
        }

        *//*************明文模式  start****************//*
        Util.BodyObj obj = null;
      *//*  try {
            obj = Util.resolveBody(body, false);
        } catch (JSONException e) {
            e.printStackTrace();
        }*//*
        logger.info("data receive:  body Object --- " + obj);

        if (obj != null) {
            boolean dataRight = Util.checkSignature(obj, token);
            if (dataRight) {
                logger.info("data receive: content" + obj.toString());
            } else {
                logger.info("data receive: signature error");
            }

        } else {
            logger.info("data receive: body empty error");
        }*/
        /*************明文模式  end****************/


        /********************************************************
         *  解析数据推送请求，加密模式
         *
         *  如果是加密模式使用以下代码
         ********************************************************/
        /*************加密模式  start****************/
//        Util.BodyObj obj1 = Util.resolveBody(body, true);
//        logger.info("data receive:  body Object--- " +obj1);
//        if (obj1 != null){
//            boolean dataRight1 = Util.checkSignature(obj1, token);
//            if (dataRight1){
//                String msg = Util.decryptMsg(obj1, aeskey);
//                logger.info("data receive: content" + msg);
//            }else {
//                logger.info("data receive:  signature error " );
//            }
//        }else {
//            logger.info("data receive: body empty error" );
//        }
        /*************加密模式  end****************/
     return  Result.success("成功");
    }

        /**
         * 功能说明： URL&Token验证接口。如果验证成功返回msg的值，否则返回其他值。
         * @param msg 验证消息
         * @param nonce 随机串
         * @param signature 签名
         * @return msg值
         */

        @RequestMapping(value = "/receive", method = RequestMethod.GET)
        @ResponseBody
        public String check(@RequestParam(value = "msg") String msg,
                @RequestParam(value = "nonce") String nonce,
                @RequestParam(value = "signature") String signature) throws UnsupportedEncodingException {

            logger.info("url&token check: msg:{} nonce{} signature:{}",msg,nonce,signature);
            if (Util.checkToken(msg,nonce,signature,token)){
                return msg;
            }else {
                return "error";
            }

        }

  /*  public static void main(String[] args) {
        Date date=new Date(1466133706841l);
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        // 报警时间
        LocalDateTime gmt_warn = instant.atZone(zoneId).toLocalDateTime();

        System.out.print(gmt_warn);
    }*/


    }









