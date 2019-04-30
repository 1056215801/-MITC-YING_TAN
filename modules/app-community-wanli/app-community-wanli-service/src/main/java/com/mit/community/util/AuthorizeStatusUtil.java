package com.mit.community.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 房屋授权类型判断
 * @author hsl
 * @date 2019-03-30
 * @company mitesofor
 */
public class authorizeStatusUtil {

    public static List<Integer> Contrast(Integer status){
        /**
         * 未授权：0;卡：1;roomNumapp：10;人脸：100）；例:人脸和卡授权：101
         */
        List<Integer> list = new ArrayList<>();
        //转为二进制
        String BinaryString = null;
        Integer BinaryInteger = null;
        if(status != null){
            BinaryString = Integer.toBinaryString(status);
            BinaryInteger = Integer.valueOf(BinaryString);
        }
        if(BinaryInteger != null){
            if(BinaryInteger == 1||BinaryInteger == 10||BinaryInteger == 100){
                list.add(BinaryInteger);
            }else{
                ///只有枚举的办法了，笨了点，辛苦点
                if(BinaryInteger == 11){
                    list.add(BinaryInteger - 10);
                    list.add(BinaryInteger - 1);
                }
                if(BinaryInteger == 101){
                    list.add(BinaryInteger - 100);
                    list.add(BinaryInteger - 1);
                }
                if(BinaryInteger == 110){
                    list.add(BinaryInteger - 100);
                    list.add(BinaryInteger - 10);
                }
                if(BinaryInteger == 111){
                    list.add(BinaryInteger - 100);
                    list.add(BinaryInteger - 10);
                    list.add(BinaryInteger - 1);
                }
            }
        }
        return list;
    }
}
