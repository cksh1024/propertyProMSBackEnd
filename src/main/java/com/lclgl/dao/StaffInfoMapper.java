package com.lclgl.dao;

import com.lclgl.pojo.Grade;
import com.lclgl.pojo.StaffInfo;
import com.lclgl.pojo.StatusLevel;
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

    public int getStatusId(int userId);

    public List<StaffInfo> getManagerByTeam(int teamId);

    public List<StaffInfo> getTeamMembers(Map<String, Object> map);

    public List<StaffInfo> getStaffs();

    public int addStaff(StaffInfo staffInfo);

    public int modifyStaff(StaffInfo staffInfo);

    public List<StaffInfo> StaffInfoList();

    public List<StaffInfo> showTeamWorkers(int teamId);

    public List<StaffInfo> ManagerInfoList(int statusId);

    public void AddTeamWorker(Map<String, Object> map);

    public int getTeamIdbyId(int userId);

    public void deleteteamworkers(int userId);

    public void staffModify(Map<String,Object> map);

    public int getfinishedProNumById(int teamId);

    public int getcurrentProNumById(int teamId);

    public int addTeamToManager(int managerId);

    public List<Grade> getRateDataById(int userId);

}
