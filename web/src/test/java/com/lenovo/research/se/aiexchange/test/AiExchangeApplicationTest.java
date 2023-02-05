package com.lenovo.research.se.aiexchange.test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {
                "com.lenovo.research.se.aiexchange"
        }
)
@MapperScan(basePackages = "com.lenovo.research.se.aiexchange.core.dao")
public class AiExchangeApplicationTest {

    public static void main(String[] args) {
        SpringApplication.run(AiExchangeApplicationTest.class, args);
    }
}
