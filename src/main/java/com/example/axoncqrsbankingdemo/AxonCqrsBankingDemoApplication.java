package com.example.axoncqrsbankingdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class AxonCqrsBankingDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AxonCqrsBankingDemoApplication.class, args);
    }

}
