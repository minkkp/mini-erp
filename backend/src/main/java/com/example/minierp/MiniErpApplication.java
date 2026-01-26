package com.example.minierp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MiniErpApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiniErpApplication.class, args);
    }

}
