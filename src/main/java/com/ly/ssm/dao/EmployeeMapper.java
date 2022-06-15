package com.ly.ssm.dao;

import com.ly.ssm.bean.Employee;
import com.ly.ssm.bean.EmployeeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EmployeeMapper {
    long countByExample(EmployeeExample example);

    int deleteByExample(EmployeeExample example);

    int deleteByPrimaryKey(Integer empId);

    int insert(Employee record);

    int insertSelective(Employee record);

    List<Employee> selectByExample(EmployeeExample example);

    Employee selectByPrimaryKey(Integer empId);

    int updateByExampleSelective(@Param("record") Employee record, @Param("example") EmployeeExample example);

    int updateByExample(@Param("record") Employee record, @Param("example") EmployeeExample example);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);

    /**
     * 查询所有员工信息和部门信息
     * @param example 条件
     * @return  List<Employee>
     */
    List<Employee> selectByExampleWithDepartment(EmployeeExample example);

    /**
     * 根据主键，查询其信息和对应部门信息
     * @param empId 员工id
     * @return Employee
     */
    Employee selectByPrimaryKeyWithDepartment(Integer empId);
}