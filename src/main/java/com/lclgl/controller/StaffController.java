package com.lclgl.controller;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lclgl.dao.StaffInfoMapper;
import com.lclgl.pojo.StaffInfo;
import com.lclgl.service.ProService;
import com.lclgl.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.io.StringReader;
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
    @Autowired
    private ProService proService;

    @PostMapping("/staffShow")
    public List<Map<String,Object>> StaffInfoList(){
        return staffService.StaffInfoList();
    }

    @PostMapping("/teamworkers")
    public List<Map<String,Object>> TeamWorkers(HttpSession session) {
        return staffService.TeamWorkers((Integer) session.getAttribute("staffId"));
    }

    @PostMapping("/managerShow")
    public List<Map<String,Object>> ManagerInfoList(HttpSession session) {
        if (session.getAttribute("staffId")==null){
            System.out.println("获取数据失败");
            return null;
        }
        else {
            return staffService.ManagerInfoList((Integer) session.getAttribute("staffId"));
        }
    }

    @PostMapping("/teamworkerselect")
    public Map<String,Object> deleteTeamWorkers(String selectedteamworkers){
        List<StaffInfo> staffInfos=new ArrayList<>();
        staffInfos=jsonToArrayList(selectedteamworkers,StaffInfo.class);
        return staffService.deleteTeamWorkers(staffInfos);
    }

    @PostMapping("/staffselect")
    public Map<String,Object> Staffselect(String selectedworkers, HttpSession session){
        if (session.getAttribute("staffId")==null){
            HashMap<String, Object> map = new HashMap<>();
            map.put("msg","获取数据失败");
            System.out.println(map.get("msg"));
            return map;
        }
        else {
            List<StaffInfo> staffInfos = new ArrayList<>();
            staffInfos=jsonToArrayList(selectedworkers,StaffInfo.class);
            int teamId=staffService.getManagerTeamId((Integer) session.getAttribute("staffId"));
            HashMap<String, Object> map = new HashMap<>();
            if (teamId==0){
                map.put("msg","该主管未被分配团队，不能选择员工");
                return map;
            }
            else if (staffInfos.size()!=0){
                staffService.Staffselect(staffInfos, teamId);
                map.put("msg","添加员工成功");
                System.out.println(map.get("msg"));
                return map;
            }
            else {
                map.put("msg","请选择员工");
                return map;
            }
        }
    }

    public <T> ArrayList<T> jsonToArrayList(String json, Class<T> clazz) {

        Type type = new TypeToken<ArrayList<JsonObject>>() {
        }.getType();
        ArrayList<JsonObject> jsonObjects = new Gson().fromJson(json, type);
        ArrayList<T> arrayList = new ArrayList<>();
        for (JsonObject jsonObject : jsonObjects) {
            arrayList.add(new Gson().fromJson(jsonObject, clazz));
        }
        return arrayList;

    }

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

    @PostMapping("/showProNum")
    public Map<String,Object> showProNum(HttpSession session){
        return staffService.getProNumById((Integer) session.getAttribute("staffId"));
    }

    @PostMapping("/updatePassword")
    public Map<String, Object> updatePassword(HttpSession session, String newPwd, String oldPwd) {
        if (session.getAttribute("staffId") == null) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("msg", "你没有权限！");
            return map;
        }
        return staffService.updatePassword(newPwd, oldPwd, (int) session.getAttribute("staffId"));
    }

}

