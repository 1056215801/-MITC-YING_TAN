package com.mit.community.module.qqwl.controller;

import com.mit.community.util.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

@RequestMapping(value = "/cloud-service")
@RestController
@Slf4j
@Api(tags = "QQ物联服务端")
public class QqwlController {
    private static final Logger logger = LoggerFactory.getLogger(QqwlController.class);
    @ApiOperation(value = "登录步骤A")
    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        log.info("进入请求");
        System.out.print("进入请求");
        String returnMessage = null;
        InputStream in = null;
        try{
            in = request.getInputStream();
            String model = Utils.inputStream2String(in, "");
            JSONObject json = JSONObject.fromObject(model);
        } catch (Exception e){
            e.printStackTrace();
            log.info("登录接口异常："+ e);
        }
        return "ok";



    }

}
