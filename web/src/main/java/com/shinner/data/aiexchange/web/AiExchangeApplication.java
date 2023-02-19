package com.shinner.data.aiexchange.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {
                "com.shinner.data.aiexchange"
        }
)
@MapperScan(basePackages = "com.shinner.data.aiexchange.core.dao")
public class AiExchangeApplication {
    public static void main(String[] args) {
        SpringApplication.run(AiExchangeApplication.class, args);
    }
}
