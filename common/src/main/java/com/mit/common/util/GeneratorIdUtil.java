package com.mit.common.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 分布式id
 *
 * @author shuyy
 * @date 2018/11/8 9:10
 * @company mitesofor
 */
public class GeneratorIdUtil {
    /***
     * 机器编码
     */
    private static final int SERVER_ID = 1;

    /**
     * key：表名， value：内存系列号, 为什么用concurrentHashMap，因为，锁的时候，只是分段锁，不同的表获取id还是会有并发操作
     */
    private static final ConcurrentHashMap<String, Integer> SEQUENCE = new ConcurrentHashMap<>();

    /**
     * 生成id。格式为：42bit毫秒 7bit机器数 8bit系列号。总共57位：id需要预留18位
     *
     * @author shuyy
     * @date 2018/11/8 9:15
     * @company mitesofor
     */
    public static long generatorId(String table) {
        Integer i;
		/*
		   低7位的序列号。 借鉴了concurrentHashmap分段锁的思想，只锁局部并不锁整个集合。
		   然后并发的时候让它自旋。本来可以用sun.misc.Unsafe类来进行cas操作解决加锁的问题，但是由于jdk这个类不给用户用，所以只能加锁
		 */
        for (; ; ) {
            i = SEQUENCE.get(table);
            if (i == null) {
                synchronized (SEQUENCE) {
                    if (SEQUENCE.get(table) == null) {
                        i = 0;
                        SEQUENCE.put(table, i);
                        break;
                    }
                }
            } else {
                synchronized (SEQUENCE.get(table)) {
                    if (i.equals(SEQUENCE.get(table))) {
                        i++;
                        if (i > 255) {
                            i = 0;
                        }
                        SEQUENCE.put(table, i);
                        break;
                    }
                }
            }
        }
        // 高42位
        // 中7位
        long tmp = getMil();
        tmp = tmp << 7;
        tmp = tmp ^ SERVER_ID;
        tmp = tmp << 8;
        tmp = tmp ^ i;
        System.out.println(table + ":" + tmp);
        return tmp;
    }

    /**
     * 获取现在到2018/08/21的毫秒数 @param @return @return long @throws
     */
    private static long getMil() {
        LocalDateTime ldt = LocalDateTime.of(2018, 8, 21, 0, 0, 0);
        ZonedDateTime zdt = ldt.atZone(ZoneId.of("Asia/Shanghai"));
        long millis = zdt.toInstant().toEpochMilli();
        long now = System.currentTimeMillis();
        return now - millis;
    }
}
