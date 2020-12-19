package com.lclgl.service;

import com.lclgl.dao.ProInfoMapper;
import com.lclgl.pojo.ProInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author cksh
 * @create 2020-12-18 10:30
 */
@Service
public class ProService {

    @Autowired
    private ProInfoMapper proInfoMapper;

    public String getProName(int proId) {
        ProInfo pro = proInfoMapper.getProById(proId);
        return pro.getProName();
    }

}
