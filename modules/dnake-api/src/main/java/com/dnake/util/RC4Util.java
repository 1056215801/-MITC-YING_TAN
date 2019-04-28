package com.dnake.util;

/**
 * RC4加密解密工具类
 *
 * @author Mr.Deng
 * @date 2018/11/7 14:10
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: mitesofor </p>
 */
public class RC4Util {
    /**
     * 解密
     *
     * @param data 数据 byte[]
     * @param key  密钥
     * @return String
     * @author Mr.Deng
     * @date 16:43 2018/11/7
     */
    public static String decrypt(byte[] data, String key) {
        if (data == null || key == null) {
            return null;
        }
        return asString(rc4Base(data, key));
    }

    /**
     * 解密
     *
     * @param data 数据 String
     * @param key  密钥
     * @return string
     * @author Mr.Deng
     * @date 16:44 2018/11/7
     */
    public static String decrypt(String data, String key) {
        if (data == null || key == null) {
            return null;
        }
        return new String(rc4Base(hexStringToBytes(data), key));
    }

    /**
     * 加密
     *
     * @param data 数据 string
     * @param key  密钥
     * @return byte[]
     * @author Mr.Deng
     * @date 16:44 2018/11/7
     */
    public static byte[] encryptToByte(String data, String key) {
        if (data == null || key == null) {
            return null;
        }
        byte[] b_data = data.getBytes();
        return rc4Base(b_data, key);
    }

    /**
     * 加密
     *
     * @param data 数据 String
     * @param key  密钥
     * @return String
     * @author Mr.Deng
     * @date 16:44 2018/11/7
     */
    public static String encryptToString(String data, String key) {
        if (data == null || key == null) {
            return null;
        }
        return toHexString(asString(encryptToByte(data, key)));
    }

    /**
     * byte转换为String
     *
     * @param buf byte[]
     * @return string
     * @author Mr.Deng
     * @date 16:45 2018/11/7
     */
    private static String asString(byte[] buf) {
        StringBuffer strBuf = new StringBuffer(buf.length);
        for (int i = 0; i < buf.length; i++) {
            strBuf.append((char) buf[i]);
        }
        return strBuf.toString();
    }

    /**
     * 初始化Key
     *
     * @param aKey 密钥
     * @return byte[]
     * @author Mr.Deng
     * @date 16:45 2018/11/7
     */
    private static byte[] initKey(String aKey) {
        byte[] bKey = aKey.getBytes();
        byte[] state = new byte[256];
        int s = 256;
        for (int i = 0; i < s; i++) {

            state[i] = (byte) i;
        }
        int index1 = 0;
        int index2 = 0;
        if (bKey == null || bKey.length == 0) {
            return null;
        }
        for (int i = 0; i < s; i++) {
            index2 = ((bKey[index1] & 0xff) + (state[i] & 0xff) + index2) & 0xff;
            byte tmp = state[i];
            state[i] = state[index2];
            state[index2] = tmp;
            index1 = (index1 + 1) % bKey.length;
        }
        return state;
    }

    /**
     * String转16进制
     *
     * @param s 字符串
     * @return 16进制字符串
     * @author Mr.Deng
     * @date 16:45 2018/11/7
     */
    private static String toHexString(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch & 0xFF);
            if (s4.length() == 1) {
                s4 = '0' + s4;
            }
            str = str + s4;
        }
        // 0x表示十六进制
        return str;
    }

    /**
     * 16进制转byte
     *
     * @param src 16进制字符串
     * @return byte[]
     * @author Mr.Deng
     * @date 16:46 2018/11/7
     */
    private static byte[] hexStringToBytes(String src) {
        int size = src.length();
        byte[] ret = new byte[size / 2];
        byte[] tmp = src.getBytes();
        for (int i = 0; i < size / 2; i++) {
            ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
        }
        return ret;
    }

    /**
     * 组合byte
     *
     * @param src0 byte
     * @param src1 byte
     * @return 组合后的byte
     * @author Mr.Deng
     * @date 16:46 2018/11/7
     */
    private static byte uniteBytes(byte src0, byte src1) {
        char b0 = (char) Byte.decode("0x" + new String(new byte[]{src0})).byteValue();
        b0 = (char) (b0 << 4);
        char b1 = (char) Byte.decode("0x" + new String(new byte[]{src1})).byteValue();
        byte rest = (byte) (b0 ^ b1);
        return rest;
    }

    /**
     * Cr4加密
     *
     * @param input byte[]
     * @param mkKey 密钥
     * @return byte[]
     * @author Mr.Deng
     * @date 16:47 2018/11/7
     */
    private static byte[] rc4Base(byte[] input, String mkKey) {
        int x = 0;
        int y = 0;
        byte[] key = initKey(mkKey);
        int xorIndex;
        byte[] result = new byte[input.length];

        for (int i = 0; i < input.length; i++) {
            x = (x + 1) & 0xff;
            y = ((key[x] & 0xff) + y) & 0xff;
            byte tmp = key[x];
            key[x] = key[y];
            key[y] = tmp;
            xorIndex = ((key[x] & 0xff) + (key[y] & 0xff)) & 0xff;
            result[i] = (byte) (input[i] ^ key[xorIndex]);
        }
        return result;
    }
}
