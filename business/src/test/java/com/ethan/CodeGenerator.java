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

    private static final String dbUrl = "jdbc:mysql://localhost:3306/eqa?characterEncoding=utf-8&useSSL=false&useUnicode=true&serverTimezone=GMT%2B8";
    private static final String dbUserName = "root";
    private static final String dbPassword = "Ethan1128?";

    public static void main(String[] args) {
        FastAutoGenerator.create(dbUrl, dbUserName, dbPassword).globalConfig(builder -> {
            builder.author("Ethan").enableSwagger().disableOpenDir().commentDate("yyyy/MM/dd").outputDir("/Users/ethan/代码/Projects/EQA/服务端/EQAServer/business/src/main/java"); // 指定输出目录
        }).packageConfig(builder -> {
            builder.parent("com.ethan").moduleName("qa").entity("pojo").pathInfo(Collections.singletonMap(OutputFile.xml, "/Users/ethan/代码/Projects/EQA/服务端/EQAServer/business/src/main/java/com/ethan/qa/mapper/xml")); // 设置mapperXml生成路径
        }).strategyConfig(builder -> {
            builder.addInclude("answer", "domain", "question", "user_domain", "settings", "user_info", "user_star").addTablePrefix(); // 设置过滤表前缀，比如把 t_user 表 --> User
        }).execute();
    }
}
