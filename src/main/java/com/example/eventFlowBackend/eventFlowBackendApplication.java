package com.example.eventFlowBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.example.eventFlowBackend.entity")
public class eventFlowBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(eventFlowBackendApplication.class, args);
    }

}
