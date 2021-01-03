package com.lclgl.service;

import com.lclgl.dao.CustomerMapper;
import com.lclgl.dao.ProInfoMapper;
import com.lclgl.dao.StaffInfoMapper;
import com.lclgl.pojo.Customer;
import com.lclgl.pojo.ProInfo;
import com.lclgl.pojo.StaffInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private CustomerMapper customerMapper;

    public String getProName(int proId) {
        ProInfo pro = proInfoMapper.getProById(proId);
        return pro.getProName();
    }

    public Map<String, Object> getProsByStaffId(int staffId) {
        HashMap<String, Object> res = new HashMap<>();
        StaffInfo staff = staffInfoMapper.getStaff(staffId);
        List<ProInfo> pros = proInfoMapper.getProsByTeamId(staff.getTeamId());
        System.out.println(pros.size());
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (ProInfo pro : pros) {
            HashMap<String, Object> map = new HashMap<>();
            Customer customer = customerMapper.getCustomerByCustomerId(pro.getCusId());
            map.put("project_name", pro.getProName());
            map.put("cusName", customer.getCusName());
            map.put("progress", "未完成");
            map.put("create_date", simpleDateFormat.format(pro.getProCreate()));
            map.put("finished_time", "---");
            map.put("project_id", pro.getProId());
            list.add(map);
        }
        res.put("pros", list);
        return res;
    }

    public Map<String, Object> getFinishedProsByStaffId(int staffId) {
        HashMap<String, Object> map1 = new HashMap<>();
        StaffInfo staff = staffInfoMapper.getStaff(staffId);
        List<ProInfo> pros = proInfoMapper.getFinishedPro(staff.getTeamId());
        System.out.println(pros.size());
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy,MM,dd");
        for (ProInfo pro : pros) {
            HashMap<String, Object> map = new HashMap<>();
            Customer customer = customerMapper.getCustomerByCustomerId(pro.getCusId());
            map.put("project_id", pro.getProId());
            map.put("project_name", pro.getProName());
            map.put("client_name", customer.getCusName());
            map.put("create_date", simpleDateFormat.format(pro.getProCreate()));
            map.put("finish_date", simpleDateFormat.format(pro.getProEndtime()));
            map.put("progress", "已完成");
            list.add(map);
        }
        map1.put("pros", list);
        return map1;
    }
    public Map<String,Object> getCusByCusId(int staffId){
        HashMap<String, Object> map1 = new HashMap<>();
        StaffInfo staff = staffInfoMapper.getStaff(staffId);
        List<ProInfo> pros = proInfoMapper.getProsByTeamId(staff.getTeamId());
        System.out.println(pros.size());
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy,MM,dd");
        for (ProInfo pro : pros) {
            HashMap<String, Object> map = new HashMap<>();
            Customer customer = customerMapper.getCustomerByCustomerId(pro.getCusId());
            map.put("client_id",customer.getCusId());
            map.put("client_name",customer.getCusName());
            map.put("client_level",customer.getCusLevel());
            map.put("company_address",customer.getCusCompany());
            map.put("phone",customer.getCusPhone());
            map.put("email",customer.getCusEmail());
            list.add(map);
        }
        map1.put("cus",list);
        return map1;

    }

}


