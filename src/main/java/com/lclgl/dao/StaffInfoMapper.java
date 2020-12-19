package com.lclgl.dao;

import com.lclgl.pojo.StaffInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author cksh
 * @create 2020-12-16 9:36
 */
@Mapper
@Repository
public interface StaffInfoMapper {

    public StaffInfo getStaff(int userId);

    public String getStatusType(int statusId);

    public List<StaffInfo> getManagerByTeam(int teamId);

    public List<StaffInfo> getTeamMembers(Map<String, Object> map);

}
