package com.mit.community.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * cookie帮助类
 *
 * @author shuyy
 * @date 2018/12/18
 * @company mitesofor
 */
public class CookieUtils {

    /**
     * 获取sessionId
     *
     * @param request
     * @return java.lang.String
     * @throws
     * @author shuyy
     * @date 2018/12/18 19:55
     * @company mitesofor
     */
    public static String getSessionId(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("JSESSIONID")) {
                set(response, cookie.getName(), cookie.getValue(), 7 * 24 * 3600);
                return cookie.getValue();
            }
        }
        return null;
    }

    /**
     * @Author HuShanLin
     * @Date 17:43 2019/7/1
     * @Description:~获取请求的session
     */
    public static String getSession(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("JSESSIONID")) {
                return cookie.getValue();
            }
        }
        return null;
    }

    /**
     * @Author HuShanLin
     * @Date 17:38 2019/7/1
     * @Description:~设置Cookie过期时间
     */
    public static void set(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }
}
