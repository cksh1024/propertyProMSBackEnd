package com.lclgl.service;

import com.lclgl.dao.ProInfoMapper;
import com.lclgl.dao.StaffInfoMapper;
import com.lclgl.dao.*;
import com.lclgl.pojo.ProInfo;
import com.lclgl.pojo.ProStage;
import com.lclgl.pojo.StaffInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.xml.transform.Source;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ProService {

    @Autowired
    private ProInfoMapper proInfoMapper;

    @Autowired
    private StaffInfoMapper staffInfoMapper;

    @Autowired
    private CusInfoMapper cusInfoMapper;

    @Autowired
    private ProStageMapper proStageMapper;

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

    public Map<String,Object> createProject(String pro_name, String cus_name, int userId){
        HashMap<String, Object> map=new HashMap<>();
        if (cusInfoMapper.getCustomerByName(cus_name)!=null){
            HashMap<String,Object> map1=new HashMap<>();
            map1.put("proName",pro_name);
            map1.put("cusId",cusInfoMapper.getCustomerByName(cus_name).getCusId());
            map1.put("proCreate",new Date());
            map1.put("proCondition","建模中");
            map1.put("proEndtime",null);
            map1.put("proModelmanagerId",userId);
            map1.put("proXRmanagerId",null);
            map1.put("proAEmanagerId",null);
            proInfoMapper.CreateProject(map1);
            AddNewStage(userId);
            map.put("msg","创建成功");
            return map;
        }else {
            map.put("msg","没有该客户");
            return map;
        }
    }
    public Map<String,Object> SwitchproStage(Map<String,Object> selectedManager,int proId, int userId) {
        HashMap<String, Object> map = new HashMap<>();
        if (selectedManager != null) {
            if ("模型主管".equals(staffInfoMapper.getStatusType(staffInfoMapper.getStatusId(userId)))){
                HashMap<String,Object> map1=new HashMap<>();
                map1.put("proCondition","渲染中");
                map1.put("proId",proId);
                map1.put("xrmanagerId",selectedManager.get("managerId"));
                proInfoMapper.SwitchProstage(map1);
                AddNewStage((Integer) selectedManager.get("managerId"));
                HashMap<String,Object> map2=new HashMap<>();
                map2.put("proId",proId);
                map2.put("stageType","建模阶段");
                map2.put("stageEndtime",new Date());
                map2.put("stageCondition","已完成");
                proStageMapper.SwitchproStage(map2);
                map.put("msg", "项目阶段切换成功");
                return map;
            }
            else if ("渲染主管".equals(staffInfoMapper.getStatusType(staffInfoMapper.getStatusId(userId)))){
                HashMap<String,Object> map1=new HashMap<>();
                map1.put("proCondition","后期中");
                map1.put("proId",proId);
                map1.put("aemanagerId",selectedManager.get("managerId"));
                proInfoMapper.SwitchProstage(map1);
                AddNewStage((Integer) selectedManager.get("managerId"));
                HashMap<String,Object> map2=new HashMap<>();
                map2.put("proId",proId);
                map2.put("stageType","渲染阶段");
                map2.put("stageEndtime",new Date());
                map2.put("stageCondition","已完成");
                proStageMapper.SwitchproStage(map2);
                map.put("msg", "项目阶段切换成功");
                return map;
            }
            else {
                map.put("msg","主管选择错误！");
                return map;
            }
            }
        else {
            map.put("msg", "请选择下一阶段主管");
            return map;
        }
    }

    public Map<String,Object> proBack(String stageType,Map<String,Object> selectedPro){
        HashMap<String,Object> map=new HashMap<>();
        if (selectedPro.size()!=0){
            if ("渲染中".equals(proInfoMapper.getProById((Integer) selectedPro.get("proId")).getProCondition())&&"建模阶段".equals(stageType)){
                String stagetype="渲染阶段";
                HashMap<String,Object> map1=new HashMap<>();
                map1.put("proCondition","建模中");
                map1.put("proEndtime",null);
                map1.put("proId",selectedPro.get("proId"));
                map1.put("proXRmanagerId",null);
                map1.put("proAEmanagerId",null);
                proInfoMapper.proBack(map1);
                HashMap<String,Object> map2=new HashMap<>();
                map2.put("proId",selectedPro.get("proId"));
                map2.put("stageType",stagetype);
                proStageMapper.DeleteStage(map2);
                HashMap<String,Object> map3=new HashMap<>();
                map3.put("proId",selectedPro.get("proId"));
                map3.put("stageType",stageType);
                map3.put("stageEndtime",null);
                map3.put("stageCondition","未完成");
                proStageMapper.SwitchproStage(map3);
                map.put("msg","回退成功");
            }
            else if ("后期中".equals(proInfoMapper.getProById((Integer) selectedPro.get("proId")).getProCondition())&&"建模阶段".equals(stageType)){
                String stagetype="渲染阶段";
                String stagetype1="后期阶段";
                HashMap<String,Object> map1=new HashMap<>();
                map1.put("proCondition","建模中");
                map1.put("proEndtime",null);
                map1.put("proId",selectedPro.get("proId"));
                map1.put("proXRmanagerId",null);
                map1.put("proAEmanagerId",null);
                proInfoMapper.proBack(map1);
                HashMap<String,Object> map2=new HashMap<>();
                HashMap<String,Object> map4=new HashMap<>();
                map2.put("proId",selectedPro.get("proId"));
                map2.put("stageType",stagetype);
                proStageMapper.DeleteStage(map2);
                map4.put("proId",selectedPro.get("proId"));
                map4.put("stageType",stagetype1);
                proStageMapper.DeleteStage(map4);
                HashMap<String,Object> map3=new HashMap<>();
                map3.put("proId",selectedPro.get("proId"));
                map3.put("stageType",stageType);
                map3.put("stageEndtime",null);
                map3.put("stageCondition","未完成");
                proStageMapper.SwitchproStage(map3);
                map.put("msg","回退成功");
            }
            else if ("后期中".equals(proInfoMapper.getProById((Integer) selectedPro.get("proId")).getProCondition())&&"渲染阶段".equals(stageType)){
                String stagetype="后期阶段";
                HashMap<String,Object> map1=new HashMap<>();
                map1.put("proCondition","渲染中");
                map1.put("proEndtime",null);
                map1.put("proId",selectedPro.get("proId"));
                map1.put("proXRmanagerId",null);
                map1.put("proAEmanagerId",null);
                proInfoMapper.proBack(map1);
                HashMap<String,Object> map2=new HashMap<>();
                HashMap<String,Object> map4=new HashMap<>();
                map2.put("proId",selectedPro.get("proId"));
                map2.put("stageType",stagetype);
                proStageMapper.DeleteStage(map2);
                HashMap<String,Object> map3=new HashMap<>();
                map3.put("proId",selectedPro.get("proId"));
                map3.put("stageType",stageType);
                map3.put("stageEndtime",null);
                map3.put("stageCondition","未完成");
                proStageMapper.SwitchproStage(map3);
                map.put("msg","回退成功");
            }
        }
        else {
            map.put("msg","请选择项目");
        }
        return map;
    }

    public Map<String,Object> CompletePro(Map<String,Object> selectedPro){
        HashMap<String,Object> map=new HashMap<>();
        if (selectedPro.size()!=0){
            HashMap<String,Object> map1=new HashMap<>();
            map1.put("proId",selectedPro.get("proId"));
            map1.put("proCondition","已完成");
            map1.put("proEndtime",new Date());
            proInfoMapper.CompletePro(map1);
            HashMap<String,Object> map2=new HashMap<>();
            map2.put("proId",selectedPro.get("proId"));
            map2.put("stageType","后期阶段");
            map2.put("stageEndtime",new Date());
            map2.put("stageCondition","已完成");
            proStageMapper.SwitchproStage(map2);
            map.put("msg","项目完成成功");
        }
        else {
            map.put("msg","请选择项目");
        }
        return map;
    }

    public List<Map<String,Object>> showmanagePro(int userId){
        List<ProStage> stageList=new ArrayList<>();
        HashMap<String,Object> map1=new HashMap<>();
        map1.put("teamId",staffInfoMapper.getTeamIdbyId(userId));
        map1.put("stageCondition","未完成");
        stageList.addAll(proStageMapper.getProstages(map1));
        List<Map<String,Object>> proinfolists=new ArrayList<>();
        for (int i=0;i<stageList.size();i++){
            HashMap<String,Object> map = new HashMap<>();
            map.put("proId",stageList.get(i).getProId());
            map.put("proName",proInfoMapper.getProById(stageList.get(i).getProId()).getProName());
            map.put("proCondition",proInfoMapper.getProById(stageList.get(i).getProId()).getProCondition());
            map.put("stageCondition",stageList.get(i).getStageCondition());
            proinfolists.add(map);
        }
        return proinfolists;
    }
    public void AddNewStage(int userId){
        HashMap<String,Object> map=new HashMap<>();
        if ("模型主管".equals(staffInfoMapper.getStatusType(staffInfoMapper.getStatusId(userId)))){
            HashMap<String,Object> map1=new HashMap<>();
            map1.put("modelId",userId);
            map.put("stageType","建模阶段");
            map.put("teamId",staffInfoMapper.getTeamIdbyId(userId));
            map.put("proId",proInfoMapper.getproIdbymanager(map1).getProId());
            map.put("stageStatime",new Date());
            map.put("stageEndtime",null);
            map.put("stageCondition","未完成");
            map.put("stagePay",0);
            proStageMapper.AddNewProStage(map);
        }
        else if("渲染主管".equals(staffInfoMapper.getStatusType(staffInfoMapper.getStatusId(userId)))){
            HashMap<String,Object> map1=new HashMap<>();
            map1.put("xrId",userId);
            map.put("stageType","渲染阶段");
            map.put("teamId",staffInfoMapper.getTeamIdbyId(userId));
            map.put("proId",proInfoMapper.getproIdbymanager(map1).getProId());
            map.put("stageStatime",new Date());
            map.put("stageEndtime",null);
            map.put("stageCondition","未完成");
            map.put("stagePay",0);
            proStageMapper.AddNewProStage(map);
        }
        else if("后期主管".equals(staffInfoMapper.getStatusType(staffInfoMapper.getStatusId(userId)))){
            HashMap<String,Object> map1=new HashMap<>();
            map1.put("aeId",userId);
            map.put("stageType","后期阶段");
            map.put("teamId",staffInfoMapper.getTeamIdbyId(userId));
            map.put("proId",proInfoMapper.getproIdbymanager(map1).getProId());
            map.put("stageStatime",new Date());
            map.put("stageEndtime",null);
            map.put("stageCondition","未完成");
            map.put("stagePay",0);
            proStageMapper.AddNewProStage(map);
        }
    }
}
