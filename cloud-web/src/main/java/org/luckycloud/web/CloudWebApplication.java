package org.luckycloud.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages="org.luckycloud")
@MapperScan({"org.luckycloud.mapper"})
public class CloudWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudWebApplication.class, args);
    }
}
