package com.mit.gate.utils;

import com.mit.api.vo.log.LogInfo;
import com.mit.gate.feign.ILogService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 日志线程
 * @author shuyy
 * @date 2018/11/7 16:23
 * @company mitesofor
*/
@Slf4j
public class DBLog extends Thread {
    private static DBLog dblog = null;
    private static BlockingQueue<LogInfo> logInfoQueue = new LinkedBlockingQueue<>(1024);
    private ILogService logService;

    public DBLog setLogService(ILogService logService) {
        if(this.logService==null) {
            this.logService = logService;
        }
        return this;
    }
    public static synchronized DBLog getInstance() {
        if (dblog == null) {
            dblog = new DBLog();
        }
        return dblog;
    }

    private DBLog() {
        super("CLogOracleWriterThread");
    }

    public void offerQueue(LogInfo logInfo) {
        try {
            logInfoQueue.offer(logInfo);
        } catch (Exception e) {
            log.error("日志写入失败", e);
        }
    }

    @Override
    public void run() {
        // 缓冲队列
        List<LogInfo> bufferedLogList = new ArrayList<>();
        while (true) {
            try {
                bufferedLogList.add(logInfoQueue.take());
                logInfoQueue.drainTo(bufferedLogList);
                if (bufferedLogList.size() > 0) {
                    // 写入日志
                    for(LogInfo log:bufferedLogList){
                        logService.saveLog(log);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                // 防止缓冲队列填充数据出现异常时不断刷屏
                try {
                    Thread.sleep(1000);
                } catch (Exception ignored) {
                }
            } finally {
                if (bufferedLogList.size() > 0) {
                    try {
                        bufferedLogList.clear();
                    } catch (Exception ignored) {
                    }
                }
            }
        }
    }
}