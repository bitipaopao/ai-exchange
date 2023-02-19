package com.shinner.data.aiexchange.test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {
                "com.shinner.data.aiexchange"
        }
)
@MapperScan(basePackages = "com.shinner.data.aiexchange.core.dao")
public class AiExchangeApplicationTest {

    public static void main(String[] args) {
        SpringApplication.run(AiExchangeApplicationTest.class, args);
    }
}
