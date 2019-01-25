package com.mit.community.module.system.controller;

import com.alibaba.fastjson.JSON;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.SynchronousQueue;

/**
 * @author shuyy
 * @date 2019-01-25
 * @company mitesofor
 */
@RestController
@RequestMapping(value = "/http")
@Slf4j
@Api(tags = {"http轮询请求"})
public class HttpContorller {

    public static final Map<String, SynchronousQueue<Boolean>> householdQueueMap
            = new ConcurrentHashMap<>();
    public static final Map<String, HttpServletResponse> responseMap = new ConcurrentHashMap<>();


    @GetMapping("/haveUpdateHousehold")
    @ApiOperation(value = "住户信息是否更新", notes = "堵塞方法， 有则返回，没有则堵塞")
    public void haveUpdateHousehold(HttpServletRequest request, HttpServletResponse response, String cellphone, String mac){
        String remoteHost = request.getRemoteHost();
        System.out.println(remoteHost);
        responseMap.put(cellphone, response);
        try {
            SynchronousQueue<Boolean> quene = householdQueueMap.get(cellphone);
            if(quene == null){
                quene = new SynchronousQueue<>();
                householdQueueMap.put(cellphone, quene);
            }
            quene.take();
            System.out.println("take message:" + cellphone);
            response = responseMap.get(cellphone);
            PrintWriter writer = null;
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=utf-8");
            try {
                Result success = Result.success("success");
                writer = response.getWriter();
                String s = JSON.toJSONString(success);
                writer.print(s);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (writer != null)
                    writer.close();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
