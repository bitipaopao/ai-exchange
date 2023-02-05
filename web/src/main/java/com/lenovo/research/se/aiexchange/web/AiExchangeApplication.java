package com.lenovo.research.se.aiexchange.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {
                "com.lenovo.research.se.aiexchange"
        }
)
@MapperScan(basePackages = "com.lenovo.research.se.aiexchange.core.dao")
public class AiExchangeApplication {
    public static void main(String[] args) {
        SpringApplication.run(AiExchangeApplication.class, args);
    }
}
