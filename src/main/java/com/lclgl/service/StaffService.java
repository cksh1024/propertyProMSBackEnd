package com.lclgl.service;

import com.lclgl.dao.AuditMapper;
import com.lclgl.dao.StaffInfoMapper;
import com.lclgl.pojo.AuditInfo;
import com.lclgl.pojo.StaffInfo;
import org.omg.CosNaming.IstringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.security.timestamp.TSRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cksh
 * @create 2020-12-18 9:31
 */
@Service
public class StaffService {

    @Autowired
    private StaffInfoMapper staffInfoMapper;
    @Autowired
    private AuditMapper auditMapper;

    public Map<String, Object> ModifyStaff(Map<String,Object> form) {
        HashMap<String,Object> map=new HashMap<>();
        map.put("userId",form);
        map.put("staffPhone",form);
        map.put("staffQQ",form);
        map.put("staffEmail",form);
        staffInfoMapper.staffModify(map);
        HashMap<String,Object> map1=new HashMap<>();
        map1.put("msg","修改成功");
        return map1;
    }

    public String getStaffType(int staffId) {
        StaffInfo staff = staffInfoMapper.getStaff(staffId);
        String statusType = staffInfoMapper.getStatusType(staff.getStatusId());
        if ("模型主管".equals(statusType)) return "模型";
        if ("渲染主管".equals(statusType)) return "渲染";
        if ("后期主管".equals(statusType)) return "后期";
        int teamId = staff.getTeamId();
        List<StaffInfo> staffByTeam = staffInfoMapper.getManagerByTeam(teamId);
        if (staffByTeam == null || staffByTeam.size() == 0) return "暂无工作类型";
        StaffInfo staffInfo = staffByTeam.get(0);
        String statusType1 = staffInfoMapper.getStatusType(staffInfo.getStatusId());
        if ("模型主管".equals(statusType1)) return "模型";
        if ("渲染主管".equals(statusType1)) return "渲染";
        if ("后期主管".equals(statusType1)) return "后期";
        return "暂无工作类型";
    }

    public List<StaffInfo> getTeamMembers(int staffId) {
        StaffInfo staff = staffInfoMapper.getStaff(staffId);
        HashMap<String, Object> map = new HashMap<>();
        map.put("staffId", staffId);
        map.put("teamId", staff.getTeamId());
        return staffInfoMapper.getTeamMembers(map);
    }

    public Map<String, Object> getAuditFilesOfManager(int staffId) {
        HashMap<String, Object> map = new HashMap<>();
        StaffInfo staff = staffInfoMapper.getStaff(staffId);
        String statusType = staffInfoMapper.getStatusType(staff.getStatusId());
        if (!statusType.endsWith("主管")) {
            map.put("status", -1);
            map.put("msg", "你没有权限！");
            return map;
        }
        List<StaffInfo> teamMembers = getTeamMembers(staffId);
        ArrayList<AuditInfo> auditInfos = new ArrayList<>();
        for (StaffInfo staffInfo : teamMembers) {
            List<AuditInfo> auditInfo = auditMapper.getUnfinishedAuditInfo(staffInfo.getUserId());
            auditInfos.addAll(auditInfo);
        }
        map.put("auditInfo", auditInfos);
        map.put("msg", "获取成功！");
        map.put("status", 1);
        return map;
    }

    public Map<String, Object> getProNumById(int staffId) {
        HashMap map=new HashMap();
        int teamId=staffInfoMapper.getStaff(staffId).getTeamId();
        map.put("finishedNum",staffInfoMapper.getfinishedProNumById(teamId));
        map.put("currentNum",staffInfoMapper.getcurrentProNumById(teamId));
        return map;
    }
}
