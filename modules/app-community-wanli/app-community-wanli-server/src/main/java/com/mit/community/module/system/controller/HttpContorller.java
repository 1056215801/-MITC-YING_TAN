package com.mit.community.module.system.controller;

import com.alibaba.fastjson.JSON;
import com.mit.community.constants.RedisConstant;
import com.mit.community.service.RedisService;
import com.mit.community.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.LockSupport;

/**
 * @author shuyy
 * @date 2019-01-25
 * @company mitesofor
 */
@RestController
@Component
@RequestMapping(value = "/http")
@Slf4j
@Api(tags = {"http轮询请求"})
public class HttpContorller {
    @Autowired
    private RedisService redisService;

    public static final Map<String, Thread> threadMap = new ConcurrentHashMap<>();

    public static final Map<String, Boolean> haveUpdateMap = new ConcurrentHashMap<>();

    @GetMapping("/haveUpdateHousehold")
    @ApiOperation(value = "住户信息是否更新", notes = "堵塞方法， 有则返回，没有则堵塞")
    public void haveUpdateHousehold(HttpServletRequest request, HttpServletResponse response, String cellphone, String mac) {
        System.out.println(threadMap.keySet() + "-----------------start");
        if (threadMap.get(cellphone) == null) {
            // 第一次调用
            threadMap.put(cellphone, Thread.currentThread());
            LockSupport.park();
        } else {
            Thread thread = threadMap.get(cellphone);
            LockSupport.unpark(thread);
            threadMap.put(cellphone, Thread.currentThread());
            LockSupport.park();
        }
        Boolean haveUpdate = haveUpdateMap.get(cellphone);
        if (haveUpdate != null) {
            // 已更新
            haveUpdateMap.remove(cellphone);
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
                if (writer != null) {
                    writer.close();
                }
            }
        } else {
            System.out.println("=========offer=================");
            System.out.println(threadMap.keySet() + "-----------------end");
            return;
        }
    }

    @Scheduled(cron = "0 0/2 * ? * *")
    public void rmThreadMapToRedis() {
        System.out.println("start-----------------------------");
        Set<String> Keys = threadMap.keySet();
        System.out.println(Keys);
        if (!Keys.isEmpty()) {
            Iterator iter = Keys.iterator();
            while (iter.hasNext()) {
                String key = (String) iter.next();
                Object o = redisService.get(RedisConstant.USER + key);
                if (o == null) {
                    Thread thread = threadMap.get(key);
                    LockSupport.unpark(thread);
                    threadMap.remove(key);
                }
            }
            System.out.println("end------------------------------");
        }
    }
}
