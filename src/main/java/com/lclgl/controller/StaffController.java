package com.lclgl.controller;

import com.lclgl.dao.StaffInfoMapper;
import com.lclgl.service.ProService;
import com.lclgl.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cksh
 * @create 2020-12-18 21:36
 */
@RestController
@RequestMapping("/lclgl")
public class StaffController {

    @Autowired
    private StaffService staffService;
    @Autowired
    private ProService proService;

    @PostMapping("/getAuditInfoOfManager")
    public Map<String, Object> getAuditFilesOfManager(HttpSession session) {
        if (session.getAttribute("staffId") == null) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("msg", "你没有权限！");
            return map;
        }
        return staffService.getAuditFilesOfManager((int) session.getAttribute("staffId"));
    }

    @PostMapping("/getProsByStaffId")
    public Map<String, Object> getProsByStaffId(HttpSession session) {
        Object staffId = session.getAttribute("staffId");
        if (staffId == null) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("msg", "你没有权限！");
            return map;
        }
        return proService.getProsByStaffId((int) staffId);
    }


    @PostMapping("/getFinishedProsByStaffId")
    public Map<String, Object> getFinishedProsByStaffId(HttpSession session) {
        Object staffId = session.getAttribute("staffId");
        if (staffId == null) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("msg", "你没有权限！");
            return map;
        }
        return proService.getFinishedProsByStaffId((int) staffId);
    }

    @PostMapping("/getCusByCusId")
    public Map<String, Object> getCusByCusId(HttpSession session) {
        Object staffId = session.getAttribute("staffId");
        if (staffId == null) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("msg", "你没有权限！");
            return map;
        }
        return proService.getCusByCusId((int) staffId);
    }

    @PostMapping("/ModifyStaff")
    public Map<String,Object> ModifyStaff(String form)
    {
        HashMap map=new HashMap();
        map.put("dsad",form);
        return staffService.ModifyStaff(map);
    }

    @PostMapping("/showProNum")
    public Map<String,Object> showProNum(HttpSession session){
        return staffService.getProNumById((Integer) session.getAttribute("staffId"));
    }
}

