package com.lclgl.dao;

import com.lclgl.pojo.Customer;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface CustomerMapper {
    public List<Customer> getCustomers();

    public Customer getCustomerById(int cusId);

    public int modifyCustomer(Customer customer);

    public int delCustomer(int cusId);

    public int addCustomer(Customer customer);

    public Customer getCustomerByCustomerId(int customerId);
}
