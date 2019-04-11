package com.example.musciws;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MusciWsApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MusciWsApplication.class, args);
    }


    //实现CommandLineRunner 重写run方法 这里放了netty的启动
    @Override
    public void run(String... args) {
        new NettyService();
    }
}
