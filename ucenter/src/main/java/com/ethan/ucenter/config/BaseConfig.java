package com.ethan.ucenter.config;

/**
 * 开关及配置
 * 注意此类不要随意格式化代码！！！
 *
 * @author Ethan 2023/2/12
 */
public class BaseConfig {

    /* Time */
    // 单位是秒
    public static final int TIME_SECOND = 1;
    public static final int TIME_MINUTE = 60 * TIME_SECOND;
    public static final int TIME_TEN_MINUTE = 10 * TIME_MINUTE;
    public static final int TIME_HOUR = 60 * TIME_MINUTE;
    public static final int TIME_DAY = 24 * TIME_HOUR;
    public static final int TIME_WEEK = 7 * TIME_DAY;
    // 单位是毫秒
    public static final int TIME_WEEK_IN_MILLI = 7 * TIME_DAY * 1000;

    /* Email */
    // 发送邮件开关
    public static final boolean EMAIL_SWITCH = false; // TODO: 2023/2/15 开发阶段关闭
    // 是否允许注册邮箱重复
    public static final boolean EMAIL_LIMIT_SWITCH = true; // TODO: 2023/2/15 开发阶段开启
    // 验证码类型
    public static final int EMAIL_TYPE_SIGNIN = 1; // 注册验证码
    public static final int EMAIL_TYPE_PWD = 2; // 修改密码验证码
    // 验证码位数
    public static final int EMAIL_VERIFY_CODE_MAX = 999999;
    // 邮件标题
    public static final String EMAIL_TITLE = "[问途]验证码邮件";
    // 邮件发件人
    public static final String EMAIL_FROM = "问途";
    // 邮件内容
    public static final String EMAIL_BODY =
            "<img src=\"https://images-pool-1310202894.cos.ap-beijing.myqcloud.com/typora/%%E9%%97%%AE%%E9%%80%%94%%20Logo.png\" alt=\"问途 Logo\" width=\"50%%\">" +
                    "<h1>感谢选择问途。</h1>" +
                    "<h2>您的问途账号为 %s </h2>" +
                    "<h2>本次操作的验证码为 %d </h2>" +
                    "<h2>十分钟内有效，请尽快验证</h2>";

    /* Redis */
    // IP 防御开关
    public static final boolean IP_DEFENSE_SWITCH = false; // TODO: 2023/2/16 开发阶段关闭
    // 单个 IP 地址每 EXPIRE 时间最多请求 TIMES 次
    public static final int REDIS_EMAIL_CODE_IP_EXPIRE = TIME_HOUR;
    public static final int REDIS_EMAIL_CODE_IP_TIMES = 5;
    // Redis 中邮箱 ---> 请求验证码的IP 的 KEY 前缀
    public static final String REDIS_EMAIL_CODE_IP_PREFIX = "email_code_ip_";
    // 验证码有效期
    public static final int REDIS_EMAIL_CODE_EXPIRE = TIME_TEN_MINUTE;
    // Redis 中注册时邮箱 ---> 验证码的 KEY 前缀
    public static final String REDIS_SIGNIN_EMAIL_CODE_PREFIX = "signin_email_code_";
    // Redis 中修改密码时邮箱 ---> 验证码的 KEY 前缀
    public static final String REDIS_PWD_EMAIL_CODE_PREFIX = "pwd_email_code_";
    // Redis 中 Token ---> UID 的 KEY 前缀
    public static final String REDIS_UID_PREFIX = "uid_";
    // Token 和盐的有效期
    public static final int REDIS_TOKEN_EXPIRE = TIME_WEEK;
    // Redis 中 Token ---> 盐的 KEY 前缀
    public static final String REDIS_SALT_PREFIX = "salt_";

    /* Token */
    // Payload 中放置的键值对的键名
    public static final String TOKEN_PAYLOAD_APPKEY = "appKey";
    // 应用鉴权开关（开启后未在统一用户中心注册的应用会被拦截）
    public static final boolean SWITCH_APPS_AUTHENTICATION = false; // TODO: 2023/2/15 开发阶段关闭

    /* 数据库 */
    public static final int DB_USER_STATUS_NORMAL = 0;
    public static final int DB_USER_STATUS_DISABLED = 1;
    public static final int DB_APP_STATUS_OTHER = 1;
    public static final int DB_APP_STATUS_ETHAN = 0;
    public static final int DB_ROLE_APP_ID_EUC = 0;
    public static final int DB_ROLE_ROLE_ADMIN = 1;
    public static final int DB_ROLE_ROLE_SUPER_ADMIN = 2;
    public static final int DB_PAGE_SIZE = 30;
}
