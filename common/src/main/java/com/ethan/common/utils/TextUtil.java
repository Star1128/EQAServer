package com.ethan.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ethan 2023/2/12
 */
public class TextUtil {

    public static boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }

    /**
     * 判断是否是邮箱格式
     */
    public static boolean isEmail(String s) {
        String regEx = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(s);
        return m.matches();
    }
}
