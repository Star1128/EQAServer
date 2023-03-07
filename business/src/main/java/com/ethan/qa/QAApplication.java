package com.ethan.qa;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Ethan 2023/2/6
 */
@SpringBootApplication
@ComponentScan("com.ethan.qa")
@MapperScan("com.ethan.qa.mapper")
@ComponentScan("com.ethan.common.utils")
@ComponentScan("com.ethan.common.config")
public class QAApplication {
    public static void main(String[] args) {
        SpringApplication.run(QAApplication.class, args);
    }
}
