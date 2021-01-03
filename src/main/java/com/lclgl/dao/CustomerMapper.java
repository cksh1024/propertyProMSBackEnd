package com.lclgl.dao;

import com.lclgl.pojo.Customer;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CustomerMapper {

    public Customer getCustomerByCustomerId(int customerId);

}
