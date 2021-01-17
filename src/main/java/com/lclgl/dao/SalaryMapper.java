package com.lclgl.dao;

import com.lclgl.pojo.Salary;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author cksh
 * @create 2021-01-04 23:17
 */
@Mapper
@Repository
public interface SalaryMapper {

    public int addSalary(Salary salary);

    public int updateSalary(Salary salary);

    public List<Salary> getSalarys();

    public Salary getSalaryByIdAndTime(Map<String, Object> map);

    public List<Salary> getSalaryById(int userId);

}
