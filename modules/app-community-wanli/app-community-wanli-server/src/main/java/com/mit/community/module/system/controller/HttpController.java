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
 * http轮询请求，用来刷新app首页
 * @author shuyy
 * @date 2019-01-25
 * @company mitesofor
 */
@RestController
@Component
@RequestMapping(value = "/http")
@Slf4j
@Api(tags = {"http轮询请求"})
public class HttpController {
    private final RedisService redisService;

    public static final Map<String, Thread> THREAD_MAP = new ConcurrentHashMap<>();

    public static final Map<String, Boolean> HAVE_UPDATE_MAP = new ConcurrentHashMap<>();

    @Autowired
    public HttpController(RedisService redisService) {
        this.redisService = redisService;
    }

    @GetMapping("/haveUpdateHousehold")
    @ApiOperation(value = "住户信息是否更新", notes = "堵塞方法， 有则返回，没有则堵塞")
    public void haveUpdateHousehold(HttpServletRequest request, HttpServletResponse response, String cellphone, String mac) {
        System.out.println(THREAD_MAP.keySet() + "-----------------start");
        if (THREAD_MAP.get(cellphone) == null) {
            // 第一次调用
            THREAD_MAP.put(cellphone, Thread.currentThread());
            LockSupport.park();
        } else {
            Thread thread = THREAD_MAP.get(cellphone);
            LockSupport.unpark(thread);
            THREAD_MAP.put(cellphone, Thread.currentThread());
            LockSupport.park();
        }
        Boolean haveUpdate = HAVE_UPDATE_MAP.get(cellphone);
        if (haveUpdate != null) {
            // 已更新
            HAVE_UPDATE_MAP.remove(cellphone);
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
            System.out.println(THREAD_MAP.keySet() + "-----------------end");
            return;
        }
    }

    /**
     * 定时清理退出登录但未及时释放的线程
     * @author Mr.Deng
     * @date 10:22 2019/3/7
     */
    @Scheduled(cron = "0 0/2 * ? * *")
    public void rmThreadMapToRedis() {
        System.out.println("start-----------------------------");
        Set<String> Keys = THREAD_MAP.keySet();
        System.out.println(Keys);
        if (!Keys.isEmpty()) {
            Iterator iter = Keys.iterator();
            while (iter.hasNext()) {
                String key = (String) iter.next();
                Object o = redisService.get(RedisConstant.USER + key);
                if (o == null) {
                    Thread thread = THREAD_MAP.get(key);
                    LockSupport.unpark(thread);
                    THREAD_MAP.remove(key);
                }
            }
            System.out.println("end------------------------------");
        }
    }
}
