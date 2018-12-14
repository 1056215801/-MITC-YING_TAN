package com.mit.community.util;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 生成订单号工具类
 * @author Mr.Deng
 * @date 2018/12/3 19:01
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
public class MakeOrderNumUtil {

    /**
     * 锁对象，可以为任意对象
     */
    private static final Object LOCK_OBJ = "lockerOrder";
    /**
     * 订单号生成计数器
     */
    private static long orderNumCount = 0L;
    /**
     * 每毫秒生成订单号数量最大值
     */
    private static final int MAX_PERMSEC_SIZE = 1000;

    /**
     * 生成非重复订单号，理论上限1毫秒1000个，可扩展
     * @return string
     * @author Mr.Deng
     * @date 19:06 2018/12/3
     */
    public static String makeOrderNum() {
        // 最终生成的订单号
        String finOrderNum = StringUtils.EMPTY;
        try {
            synchronized (LOCK_OBJ) {
                // 取系统当前时间作为订单号变量前半部分，精确到毫秒
                long nowLong = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
                // 计数器到最大值归零，可扩展更大，目前1毫秒处理峰值1000个，1秒100万
                if (orderNumCount >= MAX_PERMSEC_SIZE) {
                    orderNumCount = 0L;
                }
                //组装订单号
                String countStr = MAX_PERMSEC_SIZE + orderNumCount + "";
                finOrderNum = nowLong + countStr.substring(1);
                orderNumCount++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return finOrderNum;
    }
}
