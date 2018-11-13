package com.mit.common.util;

/**
 * StringHelper
 * @author shuyy
 * @date 2018/11/8 9:25
 * @company mitesofor
*/
public class StringHelper {
    public static String getObjectValue(Object obj){
        return obj==null?"":obj.toString();
    }
}
