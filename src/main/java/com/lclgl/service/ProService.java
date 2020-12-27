package com.lclgl.service;

import com.lclgl.dao.ProInfoMapper;
import com.lclgl.dao.StaffInfoMapper;
import com.lclgl.pojo.ProInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cksh
 * @create 2020-12-18 10:30
 */
@Service
public class ProService {

    @Autowired
    private ProInfoMapper proInfoMapper;
    @Autowired
    private StaffInfoMapper staffInfoMapper;

    public String getProName(int proId) {
        ProInfo pro = proInfoMapper.getProById(proId);
        return pro.getProName();
    }

    public List<ProInfo> getProsOfStaff(int staffId) {
        int teamId = staffInfoMapper.getStaff(staffId).getTeamId();
        return proInfoMapper.getProsByTeamID((teamId));
    }

    public List<String> getPronamesByStaff(int staffId) {
        List<ProInfo> pros = getProsOfStaff(staffId);
        ArrayList<String> proNames = new ArrayList<>();
        for (ProInfo pro : pros) {
            proNames.add(pro.getProName());
        }
        return proNames;
    }

}
