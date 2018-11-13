package com.mit.common.util;

import org.omg.CORBA.UNKNOWN;

import javax.servlet.http.HttpServletRequest;

/**
 * client util
 *
 * @author shuyy
 * @date 2018/11/8 8:55
 * @company mitesofor
 */
class ClientUtil {
    /**
     * 获取客户端真实ip
     * @param request request
     * @return java.lang.String
     * @author shuyy
     * @date 2018/11/8 8:56
     * @company mitesofor
     */
    static String getClientIp(HttpServletRequest request) {
        final String unknown = "unknown";
        final String xForwardedFor = "x-forwarded-for";
        String ip = request.getHeader(xForwardedFor);
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
