package com.lclgl.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lclgl")
public class FrontDesk {

    @PostMapping("/hello")
    public String hello() {
        return "hello";
    }
}
