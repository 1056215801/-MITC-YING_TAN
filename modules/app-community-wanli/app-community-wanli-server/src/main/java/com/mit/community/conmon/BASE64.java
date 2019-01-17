package com.mit.community.conmon;

/**
 * @author shuyy
 * @date 2019-01-17
 * @company mitesofor
 */

import sun.misc.BASE64Encoder;

public class BASE64 {
    /**
     * 将本地图片进行Base64位编码
     *
     * @return
     */
    public static String encodeImgageToBase64(byte[] data) {
// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
// 其进行Base64编码处理
// 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);// 返回Base64编码过的字节数组字符串
    }
}

