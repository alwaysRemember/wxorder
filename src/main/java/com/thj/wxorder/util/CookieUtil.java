package com.thj.wxorder.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * cookie工具类
 *
 * @author yaer
 * @email 740905172@qq.com
 * @date 2018/8/22 10:44
 **/

public class CookieUtil {

    /**
     * 设置cookie
     * @param response  http响应
     * @param name  key
     * @param value val
     * @param maxAge    过期时间
     */
    public static void set(HttpServletResponse response, String name, String value, Integer maxAge) {

        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     * 获取cookie
     * @param request   http请求
     * @param name  key
     * @return
     */
    public static Cookie get(HttpServletRequest request, String name) {

        Map<String,Cookie> cookieMap = readCookieMap(request);
        if ( cookieMap.containsKey(name) ){
            return cookieMap.get(name);
        }

        return null;
    }

    private static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<>();
        Cookie[] cookies = request.getCookies();

        if ( cookies != null ) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(),cookie);
            }
        }
        return cookieMap;

    }

}
