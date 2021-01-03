package com.lclgl.controller;

import com.lclgl.service.ProService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Type;
import java.util.*;

@RestController
@RequestMapping("/lclgl")
public class ProjectController {

    @Autowired
    private ProService proService;

    @PostMapping("/createproject")
    public Map<String,Object> CreateProject(String pro_name,String cus_name,HttpSession session){
        return proService.createProject(pro_name,cus_name, (Integer) session.getAttribute("staffId"));
    }
    @PostMapping("/switchprostage")
    public Map<String,Object> SwitchproStage(String selectedManager,int proId, HttpSession session){
        JSONObject jsonObject=new JSONObject(selectedManager);
        Map<String,Object> map =new HashMap<>();
        Iterator iterator=jsonObject.keys();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            Object value = jsonObject.get(key);
            map.put(key, value);
        }
        return proService.SwitchproStage(map,proId, (Integer) session.getAttribute("staffId"));
    }

    @PostMapping("/showmanagePro")
    public List<Map<String,Object>> showmanagePro(HttpSession session) {
        return proService.showmanagePro((Integer) session.getAttribute("staffId"));
    }

//    @PostMapping("/selectedPro")
//    public Map<String,Object> selectedProInfo(String selectedPro){
//        HashMap<String,Object> map=new HashMap<>();
//        map.put("msg","进入项目成功");
//        return map;
//    }

    @PostMapping("/CompletePro")
    public Map<String,Object> CompletePro(String completepro){
        JSONObject jsonObject=new JSONObject(completepro);
        Map<String,Object> map =new HashMap<>();
        Iterator iterator=jsonObject.keys();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            Object value = jsonObject.get(key);
            map.put(key, value);
        }
        return proService.CompletePro(map);
    }

    @PostMapping("/proBack")
    public Map<String,Object> proBack(String stageType,String selectedPro){
        JSONObject jsonObject=new JSONObject(selectedPro);
        Map<String,Object> map=new HashMap<>();
        Iterator iterator=jsonObject.keys();
        while (iterator.hasNext()){
            String key= (String) iterator.next();
            Object value=jsonObject.get(key);
            map.put(key,value);
        }
        return proService.proBack(stageType,map);
    }

}
