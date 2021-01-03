package com.lclgl.dao;


import com.lclgl.pojo.ProStage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface ProStageMapper {

    public void SwitchproStage(Map<String,Object> map);
    public void AddNewProStage(Map<String,Object> map);
    public List<ProStage> getProstages(Map<String,Object> map);
    public void DeleteStage(Map<String,Object> map);

}
