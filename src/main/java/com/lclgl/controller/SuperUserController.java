package com.lclgl.controller;

import com.lclgl.JsonUtils;
import com.lclgl.pojo.Grade;
import com.lclgl.pojo.Salary;
import com.lclgl.service.SuperUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jws.Oneway;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cksh
 * @create 2021-01-04 10:03
 */
@RestController
@RequestMapping("/lclgl")
public class SuperUserController {

    @Autowired
    private SuperUserService superUserService;

    @PostMapping("/getTeamInfos")
    public Map<String, Object> getTeamInfos(HttpSession session) {
        if (session.getAttribute("staffId") == null) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("msg", "你没有权限！");
            return map;
        }
        return superUserService.getTeamInfos();
    }

    @PostMapping("/getManagersByTeamType")
    public Map<String, Object> getManagersByTeamType(String teamType, HttpSession session) {
        if (session.getAttribute("staffId") == null) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("msg", "你没有权限！");
            return map;
        }
        return superUserService.getManagersByTeamType(teamType);
    }

    @PostMapping("/addTeam")
    public Map<String, Object> addTeam(HttpSession session, String teamName, String teamType, int managerId) {
        if (session.getAttribute("staffId") == null) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("msg", "你没有权限！");
            return map;
        }
        return superUserService.addTeam(teamName, teamType, managerId);
    }

    @PostMapping("/getPros")
    public Map<String, Object> getPros(HttpSession session) {
        if (session.getAttribute("staffId") == null) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("msg", "你没有权限！");
            return map;
        }
        return superUserService.getPros();
    }

    @PostMapping("/getProStages")
    public Map<String, Object> getProStages(HttpSession session) {
        if (session.getAttribute("staffId") == null) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("msg", "你没有权限！");
            return map;
        }
        return superUserService.getProStages();
    }

    @PostMapping("/updateStagePay")
    public Map<String, Object> updateStagePay(HttpSession session, double stagePay, String stageType, int proId) {
        if (session.getAttribute("staffId") == null) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("msg", "你没有权限！");
            return map;
        }
        return superUserService.updateStagePay(stagePay, proId, stageType);
    }

    @PostMapping("/getProJournals")
    public Map<String, Object> getProJournals(HttpSession session) {
        if (session.getAttribute("staffId") == null) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("msg", "你没有权限！");
            return map;
        }
        return superUserService.getProJournals();
    }

    @PostMapping("/setSalaryInfo")
    public Map<String, Object> setSalaryInfo(HttpSession session,
                                             int staffId,
                                             String salaryTime,
                                             int salaryDays,
                                             double salaryPractical,
                                             double salaryBonus,
                                             double salaryAll,
                                             double salaryTax,
                                             double salaryPersonal
                                             ) {
        if (session.getAttribute("staffId") == null) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("msg", "你没有权限！");
            return map;
        }
        Salary salary = new Salary();
        salary.setSalaryAll(salaryAll);
        salary.setSalaryBonus(salaryBonus);
        salary.setSalaryDays(salaryDays);
        salary.setSalaryPersonal(salaryPersonal);
        salary.setSalaryPractical(salaryPractical);
        salary.setSalaryTax(salaryTax);
        salary.setUserId(staffId);
        String[] split = salaryTime.split("-");
        salary.setSalaryTime(new Date(Integer.parseInt(split[0])-1900, Integer.parseInt(split[1])-1, Integer.parseInt(split[2])));
        return superUserService.setSalaryInfo(salary);
    }

    @PostMapping("/getSalaryInfo")
    public Map<String, Object> getSalaryInfo(HttpSession session) {
        if (session.getAttribute("staffId") == null) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("msg", "你没有权限！");
            return map;
        }
        return superUserService.getSalaryInfo();
    }

    @PostMapping("/getSalarys")
    public Map<String, Object> getSalarys(HttpSession session) {
        if (session.getAttribute("staffId") == null) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("msg", "你没有权限！");
            return map;
        }
        return superUserService.getStaffs();
    }

    @PostMapping("/getStaffEvaluate")
    public Map<String, Object> getStaffEvaluate(HttpSession session) {
        if (session.getAttribute("staffId") == null) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("msg", "你没有权限！");
            return map;
        }
        return superUserService.getStaffEvaluate();
    }

    @PostMapping("/getHistoryStaffEvaluate")
    public Map<String, Object> getHistoryStaffEvaluate(HttpSession session) {
        if (session.getAttribute("staffId") == null) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("msg", "你没有权限！");
            return map;
        }
        return superUserService.getHistoryStaffEvaluate();
    }

    @PostMapping("/setStaffEvaluate")
    public Map<String, Object> setStaffEvaluate(HttpSession session, String staffEvaluate) {

        if (session.getAttribute("staffId") == null) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("msg", "你没有权限！");
            return map;
        }

        return superUserService.setStaffEvaluate(JsonUtils.getObjList(staffEvaluate, Grade.class));

    }

}
