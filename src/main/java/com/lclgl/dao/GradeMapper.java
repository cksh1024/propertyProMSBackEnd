package com.lclgl.dao;

import com.lclgl.pojo.Grade;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author cksh
 * @create 2021-01-04 23:35
 */
@Mapper
@Repository
public interface GradeMapper {

    public Grade getCurrentGradeById(Map<String, Object> map);

    public int addGrade(Grade grade);

    public int updateGrade(Map<String, Object> map);

    public List<Grade> getHistoryGrade();

}
