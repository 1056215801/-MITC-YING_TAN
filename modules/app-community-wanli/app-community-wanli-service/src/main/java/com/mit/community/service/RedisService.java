package com.mit.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis service操作类
 * @author shuyy
 * @date 2018/11/29
 * @company mitesofor
 */
@Service
public class RedisService {

    private final RedisTemplate redisTemplate;

    @Autowired
    public RedisService(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /***
     * 写入缓存
     * @param key key
     * @param value value
     * @return boolean
     * @author shuyy
     * @date 2018/11/29 10:16
     * @company mitesofor
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /***
     * 写入缓存设置时效时间
     * @param key key
     * @param value value
     * @param expireTime 时间戳
     * @return boolean
     * @author shuyy
     * @date 2018/11/29 10:18
     * @company mitesofor
     */
    public boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 批量删除对应的value
     * @param keys keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     * @param pattern pattern
     */
    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0) {
            redisTemplate.delete(keys);
        }
    }

    /**
     * 删除对应的value
     * @param key key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 获取过期时间
     * @param key key
     * @return java.lang.Long
     * @author shuyy
     * @date 2018/12/12 14:22
     * @company mitesofor
    */
    public Long getExpire(String key){
        Long expire = redisTemplate.getExpire(key);
        return expire;
    }

    /**
     * 判断缓存中是否有对应的value
     * @param key key
     * @return boolean
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     * @param key key
     * @return Object
     */
    public Object get(final String key) {
        Object result;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * 哈希 添加
     * @param key     key
     * @param hashKey 哈希key
     * @param value   value
     */
    public void hmSet(String key, Object hashKey, Object value) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.put(key, hashKey, value);

    }

    /**
     * 哈希获取数据
     * @param key     ksy
     * @param hashKey 哈希key
     * @return object
     */
    public Object hmGet(String key, Object hashKey) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.get(key, hashKey);
    }

    /**
     * 列表添加
     * @param k key
     * @param v value
     */
    public void lPush(String k, Object v) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.rightPush(k, v);
    }

    /**
     * 列表获取
     * @param k  key
     * @param l  l
     * @param l1 l
     * @return Object列表
     */
    public List<Object> lRange(String k, long l, long l1) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.range(k, l, l1);
    }

    /**
     * 集合添加
     * @param key   key
     * @param value value
     */
    public void add(String key, Object value) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add(key, value);
    }

    /**
     * 集合获取
     * @param key key
     * @return Set<Object>
     */
    public Set<Object> setMembers(String key) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.members(key);
    }

    /**
     * 有序集合添加
     * @param key    key
     * @param value  value
     * @param scoure scoure
     */
    public void zAdd(String key, Object value, double scoure) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        zset.add(key, value, scoure);
    }

    /**
     * 有序集合获取
     * @param key     key
     * @param scoure  scoure
     * @param scoure1 scoure
     * @return Set<Object>
     */
    public Set<Object> rangeByScore(String key, double scoure, double scoure1) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.rangeByScore(key, scoure, scoure1);
    }

    /**
     * 自增
     * @param key
     * @param delta
     * @return void
     * @throws
     * @author shuyy
     * @date 2019-01-29 10:03
     * @company mitesofor
    */
    public void increment(String key, Integer delta){
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        operations.increment(key, delta);
    }

}
