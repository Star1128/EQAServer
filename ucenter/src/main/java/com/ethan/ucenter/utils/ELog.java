package com.ethan.ucenter.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatterBuilder;

/**
 * Java 软件（Android、JavaEE）通用的彩色日志打印机
 * V1 版本暂不开放自定义接口，当然小学生都会改 :)
 *
 * @author Ethan 2023/2/13
 */
public class ELog {

    private static final int COLOR_TRANSPARENT = 30;
    private static final int COLOR_RED = 31;
    private static final int COLOR_GREEN = 32;
    private static final int COLOR_YELLOW = 33;
    private static final int COLOR_BLUE = 34;
    private static final int COLOR_PURPLE = 35;
    private static final int COLOR_CYAN = 36;
    private static final int COLOR_GRAY = 37;
    private static final int COLOR_WHITE = 38;

    private static final int SCHEMA_NONE = 0;
    private static final int SCHEMA_BOLD = 1;
    private static final int SCHEMA_UNDERLINE = 4;

    // 日志级别控制
    public static final int LEVEL_INFO = 1;
    public static final int LEVEL_DEBUG = 2;
    public static final int LEVEL_WARNING = 3;
    public static final int LEVEL_ERROR = 4;
    public static final int SHUTDOWN = 9; // 将 LEVEL 设为 SHUTDOWN 就会关闭所有日志
    private static int LEVEL = LEVEL_INFO;

    public static void setLevel(int level){
        LEVEL = level;
    }

    public static void INFO(String content) {
        if (LEVEL > LEVEL_INFO) {
            return;
        }
        String classFullName = new Exception().getStackTrace()[1].getClassName(); //获取调用者的类名
        String methodName = new Exception().getStackTrace()[1].getMethodName(); //获取调用者的方法名
        String prefix = assemblePrefix(classFullName, methodName, "INFO");
        printLog(prefix, COLOR_BLUE, SCHEMA_BOLD, content);
    }

    public static void DEBUG(String content) {
        if (LEVEL > LEVEL_DEBUG) {
            return;
        }
        String classFullName = new Exception().getStackTrace()[1].getClassName(); //获取调用者的类名
        String methodName = new Exception().getStackTrace()[1].getMethodName(); //获取调用者的方法名
        String prefix = assemblePrefix(classFullName, methodName, "DEBUG");
        printLog(prefix, COLOR_PURPLE, SCHEMA_BOLD, content);
    }

    public static void WARNING(String content) {
        if (LEVEL > LEVEL_WARNING) {
            return;
        }
        String classFullName = new Exception().getStackTrace()[1].getClassName(); //获取调用者的类名
        String methodName = new Exception().getStackTrace()[1].getMethodName(); //获取调用者的方法名
        String prefix = assemblePrefix(classFullName, methodName, "WARNING");
        printLog(prefix, COLOR_YELLOW, SCHEMA_BOLD, content);
    }

    public static void ERROR(String content) {
        if (LEVEL > LEVEL_ERROR) {
            return;
        }
        String classFullName = new Exception().getStackTrace()[1].getClassName(); //获取调用者的类名
        String methodName = new Exception().getStackTrace()[1].getMethodName(); //获取调用者的方法名
        String prefix = assemblePrefix(classFullName, methodName, "ERROR");
        printLog(prefix, COLOR_RED, SCHEMA_BOLD, content);
    }

    private static String assemblePrefix(String classFullName, String methodName, String type) {
        String className = classFullName.substring(classFullName.lastIndexOf(".") + 1);
        return String.format("%s[%s][%s]<%s#%s> ",
                LocalDateTime.now().format(new DateTimeFormatterBuilder()
                        .appendPattern("MM-dd HH:mm:ss.SSS").toFormatter()) + " ",
                Thread.currentThread().getName(), type, className, methodName);
    }

    private static void printLog(String prefix, int color, int schema, String content) {
        System.out.format("%s\033[%d;%dm%s\033[0m %n", prefix, color, schema, content);
    }
}
