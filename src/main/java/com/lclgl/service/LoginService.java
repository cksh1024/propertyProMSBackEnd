package com.lclgl.service;

import com.lclgl.dao.LoginMapper;
import com.lclgl.dao.StaffInfoMapper;
import com.lclgl.pojo.StaffInfo;
import com.lclgl.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cksh
 * @create 2020-12-15 17:38
 */
@Service
public class LoginService {

    @Autowired
    private LoginMapper loginMapper;

    @Autowired
    private StaffInfoMapper staffInfoMapper;

    public Map<String, Object> login(String username, String password) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("status", -1);

        User user = new User(Integer.parseInt(username), password);
        user = loginMapper.getUser(user);
        if (user == null) {
            map.put("msg", "账号或密码错误！");
        } else {
            StaffInfo staff = staffInfoMapper.getStaff(Integer.parseInt(username));
            if (staff == null) {
                map.put("msg", "登陆失败！");
            } else {
                map.put("msg", "登陆成功！");
                map.put("status", 1);
                String statusType = staffInfoMapper.getStatusType(staff.getStatusId());
                if ("前台".equals(statusType)) map.put("type", "frontdesk");
                else if (statusType.endsWith("主管")) map.put("type", "frontdesk");
                else if ("熟手".equals(statusType) || "学徒".equals(statusType)) {
                    map.put("type", "employee");
                    map.put("statusType", statusType);
                }
                else if ("管理员".equals(statusType)) map.put("type", "manager");

            }
        }



        return map;
    }

}
