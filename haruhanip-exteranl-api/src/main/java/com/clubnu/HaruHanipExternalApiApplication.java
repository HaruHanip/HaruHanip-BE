package com.clubnu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HaruHanipExternalApiApplication {
    public static void main(String[] args) {
        System.setProperty("spring.config.location", "classpath:/domain-property/application-rds-local.yml,classpath:/");
//        System.setProperty("spring.config.location", "classpath:/domain-property/application-rds-prod.yml,classpath:/domain-property/application-redis-prod.yml,classpath:/");
        SpringApplication.run(HaruHanipExternalApiApplication.class, args);
    }
}