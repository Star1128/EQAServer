package com.ethan.qa.config;

/**
 * @author Ethan 2023/2/26
 */
public class GlobalConfig {

    /* UC 相关 */
    public static String UC_BASE_URL = "";
    public static final String UC_BASE_URL_RELEASE = "http://8.130.55.76:30000/";
    public static final String UC_BASE_URL_DEBUG = "http://localhost:30000/";
    public static final int UC_T2U = 0;
    public static final int UC_INFO = 1;
    public static final int UC_IS_ADMIN = 2;

    static {
        if (GlobalSwitch.DEBUG_MODE) {
            UC_BASE_URL = UC_BASE_URL_DEBUG;
        } else {
            UC_BASE_URL = UC_BASE_URL_RELEASE;
        }
    }

    public static final String UC_TOKEN2UID_URL = UC_BASE_URL + "app/uid";
    public static final String UC_INFO_URL = UC_BASE_URL + "user/info";
    public static final String UC_IS_ADMIN_URL = UC_BASE_URL + "app/is-admin";
    public static final String URL_SEPARATOR = "?";

    /* 数据库相关 */
    // 一页多少条数据
    public static final long PAGE_SIZE = 10;

    /* 与前端对齐的代号字段 */
    public static final long DOMAIN_ALL = 0;
    public static final int COLLECT_QUESTION = 0;
    public static final int COLLECT_ANSWER = 1;
    public static final int COLLECT = 0;
    public static final int UN_COLLECT = 1;

    /* Token */
    // 存到 Cookie 中的 Token 前缀
    public static final String TOKEN = "token";

}
