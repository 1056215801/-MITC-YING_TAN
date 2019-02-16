package com.mit.community.map;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.HashMap;

/**
 * @author shuyy
 * @date 2019-01-30
 * @company mitesofor
 */
public class HashMapTest {
    @Test
    public void test(){
        HashMap<Object, Object> map = new HashMap<>();
        map.put("key", "value");
        map.put("key1", "value1");
        String s = JSON.toJSONString(map);
        System.out.println(s);

    }
    @Test
    public void test2(){
        int length = 16;

        String a = "hello";

        // 00000000 00000000 00000000 00001010 10
        // 00000000 00000000 01010100 00001010 10
        // 00000000 00000000 00000000 01011110
        //‭ 00000000 00000000 00000000 00001011‬ 15
        // 00000000 00000000 00000000 00001010
        int result = 36000135 & (length - 1);
        System.out.println(result);
        System.out.println((36000135 ^ (36000135 >>> 16)) & (length - 1) );
        System.out.println(36000135 % length);

    }
}
