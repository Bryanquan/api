package com.cn.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/home")
@Controller
public class HomeController {

    @RequestMapping(value ="/login" )
    public String login(){
        System.out.println("hello");
        return "home/login";
    }
}
