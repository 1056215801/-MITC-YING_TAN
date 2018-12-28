package com.mit.community.common;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * 线程池工具类
 *
 * @author shuyy
 * @date 2018/11/14
 * @company mitesofor
 */
@Component
public class ThreadPoolUtil implements CommandLineRunner {

    private static ExecutorService threadPoolExecutor;

    private static void init(){
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("custom-pool-%d").build();
        threadPoolExecutor = new ThreadPoolExecutor(3, 3,
                60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
    }

    /***
     * 提交线程执行
     * @param thread 线程
     * @author shuyy
     * @date 2018/11/14 9:33
     * @company mitesofor
    */
    public static void execute(Runnable thread){
        if(threadPoolExecutor  == null){
            synchronized (threadPoolExecutor){
                if(threadPoolExecutor == null){
                    init();
                }
            }
        }
        threadPoolExecutor.execute(thread);
    }

    public static Future<?> submit(Callable thread){
        if(threadPoolExecutor  == null){
            synchronized (threadPoolExecutor){
                if(threadPoolExecutor == null){
                    init();
                }
            }
        }
        return threadPoolExecutor.submit(thread);
    }

    @Override
    public void run(String... args) throws Exception {
        ThreadPoolUtil.init();
    }
}
