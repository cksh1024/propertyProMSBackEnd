package com.lclgl.dao;

import com.lclgl.pojo.ProInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author cksh
 * @create 2020-12-17 21:18
 */
@Repository
@Mapper
public interface ProInfoMapper {

    public ProInfo getProById(int proId);

    public List<ProInfo> getProsByTeamID(int teamId);

}
