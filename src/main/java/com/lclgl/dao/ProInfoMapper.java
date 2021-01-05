package com.lclgl.dao;

import com.lclgl.pojo.ProInfo;
import com.lclgl.pojo.ProStage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface ProInfoMapper {

    public ProInfo getProById(int proId);

    public List<ProInfo> getProsByTeamID(int teamId);

    public void CreateProject(Map<String,Object> map);

    public void SwitchProstage(Map<String, Object> map);

    public ProInfo getproIdbymanager(Map<String,Object> map);

    public void CompletePro(Map<String,Object> map);

    public void proBack(Map<String,Object> map);

    public List<ProInfo> getFinishedPro(int teamId);

    public List<ProInfo> getPros();

    public int getProNameByProId(String proName);

    public List<ProStage> getStagePayOfStaff(Map<String, Object> map);

}
