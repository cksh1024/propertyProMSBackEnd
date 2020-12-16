package com.lclgl.controller;

import com.lclgl.dao.LoginMapper;
import com.lclgl.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author cksh
 * @create 2020-12-15 17:31
 */

@RestController
@RequestMapping("/lclgl")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public Map<String, Object> login(String username, String password, HttpSession session) {
        return loginService.login(username, password, session);
    }

}
