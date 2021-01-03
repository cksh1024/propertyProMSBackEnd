package com.lclgl.service;

import com.lclgl.dao.AuditMapper;
import com.lclgl.dao.StaffInfoMapper;
import com.lclgl.dao.TeamMapper;
import com.lclgl.pojo.AuditInfo;
import com.lclgl.pojo.StaffInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private TeamMapper teamMapper;

    public List<Map<String,Object>> StaffInfoList(){

        List<StaffInfo> staffInfos=new ArrayList<>();
        staffInfos.addAll(staffInfoMapper.StaffInfoList());
        List<Map<String,Object>> staffInfos1=new ArrayList<>();
        for (int i=0;i<staffInfos.size();i++){
            HashMap<String,Object> map = new HashMap<>();
            map.put("userId",staffInfos.get(i).getUserId());
            map.put("staffName",staffInfos.get(i).getStaffName());
            map.put("staffSex",staffInfos.get(i).getStaffSex());
            map.put("statusName",staffInfoMapper.getStatusType(staffInfos.get(i).getStatusId()));
            staffInfos1.add(map);
        }
        return staffInfos1;
    }

    public Map<String,Object> deleteTeamWorkers(List<StaffInfo> staffInfos){
        HashMap<String,Object> map=new HashMap<>();
        if (staffInfos.size()==0){
            map.put("msg","请先选择员工");
            return map;
        }else{
            for (int i=0;i<staffInfos.size();i++){
                staffInfoMapper.deleteteamworkers(staffInfos.get(i).getUserId());
            }
            map.put("msg","删除成功");
            return map;
        }
    }

    public List<Map<String,Object>> TeamWorkers(int userId){
        List<StaffInfo> staffInfos=new ArrayList<>();
        staffInfos.addAll(staffInfoMapper.showTeamWorkers(staffInfoMapper.getTeamIdbyId(userId)));
        List<Map<String,Object>> staffInfos1=new ArrayList<>();
        for (int i=0;i<staffInfos.size();i++){
            HashMap<String,Object> map = new HashMap<>();
            map.put("userId",staffInfos.get(i).getUserId());
            map.put("staffName",staffInfos.get(i).getStaffName());
            map.put("staffSex",staffInfos.get(i).getStaffSex());
            map.put("statusName",staffInfoMapper.getStatusType(staffInfos.get(i).getStatusId()));
            staffInfos1.add(map);
        }
        return staffInfos1;
    }

    public List<Map<String,Object>> ManagerInfoList(int userId){
        int statusId=staffInfoMapper.getStatusId(userId);
        if (statusId!=105){
            List<StaffInfo> staffInfos =new ArrayList<>();
            List<Map<String,Object>> staffInfos1=new ArrayList<>();
            staffInfos.addAll(staffInfoMapper.ManagerInfoList(statusId+1));
            for (int i=0;i<staffInfos.size();i++){
                HashMap<String,Object> map=new HashMap<>();
                map.put("managerId",staffInfos.get(i).getUserId());
                map.put("managerName",staffInfos.get(i).getStaffName());
                map.put("managerSex",staffInfos.get(i).getStaffSex());
                map.put("teamName",teamMapper.getTeamNamebyId(staffInfos.get(i).getTeamId()));
                map.put("teamType",teamMapper.getTeamTypebyId(staffInfos.get(i).getTeamId()));
                staffInfos1.add(map);
            }
            return staffInfos1;
       }
        else {
            System.out.println("项目已完成！不能进行下一步");
            return null;
        }
    }

    public int Staffselect(List<StaffInfo> selectedworkers,int teamId){
        HashMap<String,Object> map=new HashMap<>();
        for (int i=0;i<selectedworkers.size();i++){
            int userId=selectedworkers.get(i).getUserId();
            map.put("userId",userId);
            map.put("teamId", teamId);
            staffInfoMapper.AddTeamWorker(map);
        }
        return 0;
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

    public int getManagerTeamId(int userId){
        StaffInfo staffInfo=new StaffInfo();
        staffInfo=staffInfoMapper.getStaff(userId);
        if (staffInfo==null){
            return -1;
        }
        else {
            return staffInfo.getTeamId();
        }
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

}
