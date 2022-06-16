package com.ly.ssm.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ly.ssm.bean.Employee;
import com.ly.ssm.dao.DepartmentMapper;
import com.ly.ssm.dao.EmployeeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @FileName:EmployeeService.class
 * @Author:ly
 * @Date:2022/6/16
 * @Description: service处理业务逻辑
 */
@Service
public class EmployeeService {
    private Logger logger = LoggerFactory.getLogger(EmployeeService.class);
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 查询所有员工信息 (分页查询)
     * @param pageNo 当前页码  （sql中为pageNo -1）
     * @param pageSize 每页显示几条数据，默认是5条
     * @return 所有员工信息的列表pageInfo信息
     */
    public PageInfo<Employee> getAllEmployees(Integer pageNo,Integer pageSize){
        if (logger.isDebugEnabled()) {
            logger.debug("分页查询：当前页=" + pageNo + ",分页条数=" + pageSize);
        }
        PageHelper.startPage(pageNo, pageSize);
        List<Employee> employeeList = employeeMapper.selectByExampleWithDepartment(null);
        PageInfo<Employee> employeePageInfo = new PageInfo<>(employeeList, pageSize);
        if (logger.isDebugEnabled()) {
            logger.debug("分页查询：pageInfo信息：" + employeePageInfo);
        }
        return employeePageInfo;
    }
}
