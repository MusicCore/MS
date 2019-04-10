package com.example.muscimanger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(scanBasePackages = "com")
@PropertySource("${spring.config.location:classpath}:common-application.properties")
@EnableCaching
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class MusciMangerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusciMangerApplication.class, args);
    }

}

