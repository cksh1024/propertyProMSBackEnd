package com.lclgl.dao;

import com.lclgl.pojo.StaffInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author cksh
 * @create 2020-12-16 9:36
 */
@Mapper
@Repository
public interface StaffInfoMapper {

    public StaffInfo getStaff(int userId);

    public String getStatusType(int statusId);

}
