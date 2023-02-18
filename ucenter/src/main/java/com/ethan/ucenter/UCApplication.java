package com.ethan.ucenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Ethan 2023/2/6
 */

@SpringBootApplication
@MapperScan("com.ethan.ucenter.mapper")
@ComponentScan("com.ethan.common.utils")
@ComponentScan("com.ethan.ucenter")
public class UCApplication {

    public static void main(String[] args) {
        SpringApplication.run(UCApplication.class, args);
    }
}
