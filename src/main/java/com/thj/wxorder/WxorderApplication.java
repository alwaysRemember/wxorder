package com.thj.wxorder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.thj.wxorder.dataobject.mapper")
public class WxorderApplication {

    public static void main(String[] args) {
        SpringApplication.run(WxorderApplication.class, args);
    }
}
