package com.ethan;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;

import java.util.Collections;

/**
 * 代码生成器
 *
 * @author Ethan 2023/2/11
 */
public class CodeGenerator {

    private static final String dbUrl = "jdbc:mysql://localhost:3306/ucenter?characterEncoding=utf-8&useSSL=false&useUnicode=true&serverTimezone=GMT%2B8";
    private static final String dbUserName = "root";
    private static final String dbPassword = "Ethan1128?";

    public static void main(String[] args) {
        FastAutoGenerator.create(dbUrl, dbUserName, dbPassword).globalConfig(builder -> {
            builder.author("Ethan").enableSwagger().disableOpenDir().commentDate("yyyy/MM/dd").outputDir("/Users/ethan/代码/Projects/EQA/服务端/EQAServer/ucenter/src/main/java"); // 指定输出目录
        }).packageConfig(builder -> {
            builder.parent("com.ethan").moduleName("ucenter").entity("pojo").pathInfo(Collections.singletonMap(OutputFile.xml, "/Users/ethan/代码/Projects/EQA/服务端/EQAServer/ucenter/src/main/java/com/ethan/ucenter/mapper/xml")); // 设置mapperXml生成路径
        }).strategyConfig(builder -> {
            builder.addInclude("app_info", "images", "login_record", "role", "settings", "sign_in_info", "user", "user_info").addTablePrefix(); // 设置过滤表前缀，比如把 t_user 表 --> User
        }).execute();
    }
}
