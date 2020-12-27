package com.lclgl.controller;

import com.lclgl.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cksh
 * @create 2020-12-17 0:02
 */
@RestController
@RequestMapping("/lclgl")
public class AuditController {

    @Autowired
    AuditService auditService;

    @PostMapping("/getAuditInfo")
    public Map<String, Object> getAuditInfo(HttpSession session) {
        Object staffId = session.getAttribute("staffId");
        if (staffId == null) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("msg", "你没有权限！");
            return map;
        }
        return auditService.getAuditInfo(staffId);
    }

    @PostMapping("/getUnfinishedPros")
    public Map<String, Object> getUnfinishedPros(HttpSession session) {
        Object staffId = session.getAttribute("staffId");
        if (staffId == null) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("msg", "你没有权限！");
            return map;
        }
        return auditService.getUnfinishedPros((int) staffId);
    }

    @PostMapping("/processAudit")
    public Map<String, Object> processAudit(HttpSession session,
                                            String auditId,
                                            String proName,
                                            String staffId,
                                            String auditFile,
                                            String operation,
                                            String suggestion) {

        if (session.getAttribute("staffId") == null) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("msg", "你没有权限！");
            return map;
        }
        return auditService.processAudit((int) session.getAttribute("staffId"),
                Integer.parseInt(auditId),
                proName,
                Integer.parseInt(staffId),
                auditFile, operation,
                suggestion);
    }

}
