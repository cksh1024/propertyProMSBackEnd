package com.lclgl.dao;

import com.lclgl.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author cksh
 * @create 2020-12-15 17:34
 */
@Repository
@Mapper
public interface LoginMapper {

    public User getUser(User user);

}
