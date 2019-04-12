package com.example.musciws.Netty;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MSController {

    @GetMapping(value = "/")
    public String goIndex(){return "c.html";}
}
