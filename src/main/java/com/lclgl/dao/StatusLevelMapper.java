package com.lclgl.dao;

import com.lclgl.pojo.StatusLevel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StatusLevelMapper {

    public List<StatusLevel> getStatusLevels();

}
