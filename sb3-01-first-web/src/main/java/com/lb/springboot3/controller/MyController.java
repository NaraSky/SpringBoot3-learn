package com.lb.springboot3.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResponseBody
public class MyController {

    @RequestMapping("/hello")
    public String hello() {
        return "Hello World";
    }
}
