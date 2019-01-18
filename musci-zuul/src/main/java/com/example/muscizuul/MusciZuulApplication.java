package com.example.muscizuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;

@EnableZuulProxy
@SpringBootApplication
public class MusciZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusciZuulApplication.class, args);
    }

}

