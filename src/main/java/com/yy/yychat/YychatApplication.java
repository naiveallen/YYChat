package com.yy.yychat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.yy.yychat.mapper")
@ComponentScan(basePackages = {"com.yy.yychat"})
public class YychatApplication {

    public static void main(String[] args) {
        SpringApplication.run(YychatApplication.class, args);
    }

}
