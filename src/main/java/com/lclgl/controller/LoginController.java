package com.lclgl.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cksh
 * @create 2020-12-15 17:31
 */
@RequestMapping("/lclgl")
public class LoginController {

    @PostMapping("/login")
    public Map<String, Object> login(String username, String password) {
        return new HashMap<>();
    }

}
