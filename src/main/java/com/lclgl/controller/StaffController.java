package com.lclgl.controller;

import com.lclgl.dao.StaffInfoMapper;
import com.lclgl.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
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

    @PostMapping("/getAuditInfoOfManager")
    public Map<String, Object> getAuditFilesOfManager(HttpSession session) {
        if (session.getAttribute("staffId") == null) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("msg", "你没有权限！");
            return map;
        }
        return staffService.getAuditFilesOfManager((int) session.getAttribute("staffId"));
    }

}
