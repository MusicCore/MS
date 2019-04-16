package com.example.muscichat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MusciChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusciChatApplication.class, args);
    }

}
