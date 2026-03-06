package com.health.fitness;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.health.fitness.mapper")
public class FitnessApplication {

    public static void main(String[] args) {
        SpringApplication.run(FitnessApplication.class, args);
        System.out.println("=================================");
        System.out.println("Health Fitness Backend Started!");
        System.out.println("Access: http://localhost:8080");
        System.out.println("=================================");
    }
}
