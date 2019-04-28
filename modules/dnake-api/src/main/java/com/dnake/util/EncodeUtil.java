package com.dnake.util;

import com.google.common.io.BaseEncoding;

import java.security.MessageDigest;

/**
 * hex/base64 编解码工具集，依赖Guava, 取消了对Commmon Codec的依赖 /MD5加密
 *
 * @author Mr.Deng
 * @date 2018/11/7 17:16
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
public class EncodeUtil {

    /**
     * Hex编码, 将byte[]编码为String，默认为ABCDEF为大写字母.
     */
    public static String encodeHex(byte[] input) {
        return BaseEncoding.base16().encode(input);
    }

    /**
     * Hex解码, 将String解码为byte[].
     * <p>
     * 字符串有异常时抛出IllegalArgumentException.
     */
    public static byte[] decodeHex(CharSequence input) {
        return BaseEncoding.base16().decode(input);
    }

    /**
     * Base64编码.
     */
    public static String encodeBase64(byte[] input) {
        return BaseEncoding.base64().encode(input);
    }

    /**
     * Base64解码.
     * <p>
     * 如果字符不合法，抛出IllegalArgumentException
     */
    public static byte[] decodeBase64(CharSequence input) {
        return BaseEncoding.base64().decode(input);
    }

    /**
     * Base64编码, URL安全.(将Base64中的URL非法字符'+'和'/'转为'-'和'_', 见RFC3548).
     */
    public static String encodeBase64UrlSafe(byte[] input) {
        return BaseEncoding.base64Url().encode(input);
    }

    /**
     * Base64解码, URL安全(将Base64中的URL非法字符'+'和'/'转为'-'和'_', 见RFC3548).
     * <p>
     * 如果字符不合法，抛出IllegalArgumentException
     */
    public static byte[] decodeBase64UrlSafe(CharSequence input) {
        return BaseEncoding.base64Url().decode(input);
    }

    /**
     * MD5加密
     *
     * @param str
     * @return string
     * @throws Exception
     */
    public static String md5Digest(String str) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] b = md.digest(str.getBytes("UTF-8"));
        return byte2HexStr(b);
    }

    /**
     * byte转16进制
     *
     * @param b
     * @return
     */
    private static String byte2HexStr(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            String s = Integer.toHexString(b[i] & 0xFF);
            if (s.length() == 1) {
                sb.append("0");
            }
            sb.append(s.toUpperCase());
        }
        return sb.toString();
    }
}
