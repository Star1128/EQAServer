package com.ethan.common.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Ethan 2023/3/1
 */
public class CookieUtil {

    // 1年
    public static final int default_age = 60 * 60 * 24 * 365;

    public static final String domain = "ethandiary.top";

    /**
     * 设置cookie值
     */
    public static void setUpCookie(HttpServletResponse response, String key, String value) {
        setUpCookie(response, key, value, default_age);
    }


    public static void setUpCookie(HttpServletResponse response, String key, String value, int age) {
        Cookie cookie = new Cookie(key, value);
        cookie.setPath("/");
        /**
         * 域名：如果是单点登录，就设置顶级域名
         * sunofbeach.net
         * https://www.sunofbeach.net/
         * https://mp.sunofbeach.net/
         */
        cookie.setDomain(domain);
        cookie.setMaxAge(age);
        response.addCookie(cookie);
    }

    /**
     * 删除cookie
     */
    public static void deleteCookie(HttpServletResponse response, String key) {
        setUpCookie(response, key, null, 0);
    }

    /**
     * 获取cookie
     */
    public static String getCookie(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (key.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}

