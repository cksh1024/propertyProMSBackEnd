package com.lclgl.controller;

import com.lclgl.service.FrontDeskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author cksh
 * @create 2020-12-25 23:13
 */
@RestController
@RequestMapping("/lclgl")
public class FrontDeskController {

    @Autowired
    private FrontDeskService frontDeskService;

    @PostMapping("/getStatusLevels")
    public Map<String, Object> getStatusLevels() {
        return frontDeskService.getStatusLevels();
    }

}
