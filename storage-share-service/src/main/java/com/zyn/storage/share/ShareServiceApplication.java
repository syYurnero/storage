package com.zyn.storage.share;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
@MapperScan(value = "com.zyn.storage.share.dao")
public class ShareServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShareServiceApplication.class, args);
    }
}
