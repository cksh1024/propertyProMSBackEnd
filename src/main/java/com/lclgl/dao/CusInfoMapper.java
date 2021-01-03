package com.lclgl.dao;


import com.lclgl.pojo.Customer;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CusInfoMapper {
    public Customer getCustomerByName(String Cus_name);
}
