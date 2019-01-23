package com.mit.community.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * cookie帮助类
 * @author shuyy
 * @date 2018/12/18
 * @company mitesofor
 */
public class CookieUtils {

    /**
     * 获取sessionId
     * @param request
     * @return java.lang.String
     * @throws
     * @author shuyy
     * @date 2018/12/18 19:55
     * @company mitesofor
     */
    public static String getSessionId(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("JSESSIONID")) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
