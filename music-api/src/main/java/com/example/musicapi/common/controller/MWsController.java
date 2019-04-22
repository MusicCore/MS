package com.example.musicapi.common.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;


@Controller
public class MWsController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @Autowired
//    RestTemplate restTemplate;
//
//    @GetMapping(value = "/hi")
//    public String hi() {
//        return restTemplate.getForObject("http://ws/mywebsocket",String.class);
//    }

}
