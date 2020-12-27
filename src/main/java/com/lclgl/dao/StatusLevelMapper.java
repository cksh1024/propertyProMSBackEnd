package com.lclgl.dao;

import com.lclgl.pojo.StatusLevel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author cksh
 * @create 2020-12-25 23:10
 */
@Mapper
@Repository
public interface StatusLevelMapper {

    public List<StatusLevel> getStatusLevels();

}
