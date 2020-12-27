package com.lclgl.dao;

import com.lclgl.pojo.ProInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author cksh
 * @create 2020-12-17 21:18
 */
@Repository
@Mapper
public interface ProInfoMapper {

    public ProInfo getProById(int proId);

    public void CreateProject(Map<String,Object> map);

    public void SwitchProstage(Map<String, Object> map);

    public ProInfo getproIdbymanager(Map<String,Object> map);

    public void CompletePro(Map<String,Object> map);

    public void proBack(Map<String,Object> map);

}
