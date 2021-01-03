package com.lclgl.dao;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TeamMapper {
    public String getTeamNamebyId(int teamId);

    public String getTeamTypebyId(int teamId);
}
