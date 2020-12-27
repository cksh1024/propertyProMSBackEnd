package com.lclgl.service;

import com.lclgl.dao.StatusLevelMapper;
import com.lclgl.pojo.StatusLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cksh
 * @create 2020-12-25 23:14
 */
@Service
public class FrontDeskService {

    @Autowired
    private StatusLevelMapper statusLevelMapper;

    public Map<String, Object> getStatusLevels() {
        HashMap<String, Object> map = new HashMap<>();

        List<StatusLevel> statusLevels = statusLevelMapper.getStatusLevels();
        map.put("statusLevels", statusLevels);

        return map;
    }

}
