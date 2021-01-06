package com.lclgl.dao;

import com.lclgl.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @author cksh
 * @create 2020-12-15 17:34
 */
@Repository
@Mapper
public interface LoginMapper {

    public User getUser(User user);

    public User getUserById(int staffId);

    public int updatePassword(Map<String, Object> map);

}
