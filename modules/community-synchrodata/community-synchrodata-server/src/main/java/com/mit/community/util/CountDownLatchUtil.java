package com.mit.community.util;

import com.mit.community.common.ThreadPoolUtil;

import java.util.concurrent.CountDownLatch;

/**
 * 多线程同步运行分装类
 *
 * @author shuyy
 * @date 2018/12/8
 * @company mitesofor
 */
public class CountDownLatchUtil {

    CountDownLatch countDownLatch;

    public CountDownLatchUtil(int size){
        countDownLatch = new CountDownLatch(4);
    }

    public void run(ThreadRunnable runnable){
        ThreadPoolUtil.execute(runnable);
    }

    abstract class ThreadRunnable implements Runnable{

        CountDownLatch countDownLatch;

        public ThreadRunnable(CountDownLatch countDownLatch){
            this.countDownLatch = countDownLatch;
        }

        public void after(){
            countDownLatch.countDown();
        }

        public abstract void excute();

        @Override
        public void run() {
               try {
                   excute();
               } catch (Exception e){
                    e.printStackTrace();
               } finally {
                   after();
               }
        }
    }
}
