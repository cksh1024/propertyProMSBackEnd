package com.lclgl.dao;


import com.lclgl.pojo.StaffInfo;
import com.lclgl.pojo.Team;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TeamMapper {
    public String getTeamNamebyId(int teamId);

    public String getTeamTypebyId(int teamId);

    public List<Team> getTeamInfos();

    public List<String> getTeamManagers(int teamId);

    public int addTeam(Team team);

    public List<StaffInfo> getManagersByTeamType(String type);
}
