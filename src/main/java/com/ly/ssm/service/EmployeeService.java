package com.ly.ssm.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ly.ssm.bean.Employee;
import com.ly.ssm.bean.EmployeeExample;
import com.ly.ssm.dao.DepartmentMapper;
import com.ly.ssm.dao.EmployeeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        EmployeeExample example = new EmployeeExample();
        example.setOrderByClause("emp_id");
        List<Employee> employeeList = employeeMapper.selectByExampleWithDepartment(example);
        PageInfo<Employee> employeePageInfo = new PageInfo<>(employeeList, pageSize);
        if (logger.isDebugEnabled()) {
            logger.debug("分页查询：pageInfo信息：" + employeePageInfo);
        }
        return employeePageInfo;
    }

    /**
     * 根据用户名和性别判断该用户是否已存在！
     * @param empName 用户名
     * @param gender 性别
     * @return true：表示该用户名和性别存在，false：表示该用户名和性别不存在
     */
    public Boolean isExistsNameAndGender(String empName,String gender) {
        if (logger.isDebugEnabled()) {
            logger.debug("根据用户名和性别判断该用户是否已存在！");
        }
        EmployeeExample employeeExample = new EmployeeExample();
        employeeExample.createCriteria().andEmpNameEqualTo(empName).andGenderEqualTo(gender);
        List<Employee> employees = employeeMapper.selectByExample(employeeExample);
        if (logger.isDebugEnabled()) {
            logger.debug("数据库查询结果列表：" + employees);
        }
        return employees.size() > 0;
    }

    /**
     * 根据用户名和id判断该用户是否已存在！
     * @param empName 用户名
     * @param empId 用户id
     * @return true：表示该用户存在，false：表示该用户不存在
     */
    public Boolean isExistsNameAndId(String empName,Integer empId) {
        if (logger.isDebugEnabled()) {
            logger.debug("根据用户名和id判断该用户是否已存在！");
        }
        EmployeeExample employeeExample = new EmployeeExample();
        employeeExample.createCriteria().andEmpNameEqualTo(empName).andEmpIdEqualTo(empId);
        List<Employee> employees = employeeMapper.selectByExample(employeeExample);
        if (logger.isDebugEnabled()) {
            logger.debug("数据库查询结果列表：" + employees);
        }
        return employees.size() > 0;
    }

    /**
     * 新增用户，spring配置文件已经配置了事务管理即切入点表达式，所以不需要再写@注解了
     * @param employee 新用户
     * @return 影响的行数
     */

    public int addEmployee(Employee employee) {
        if (logger.isDebugEnabled()) {
            logger.debug("开始新增用户，已经通过xml配置文件方式开启事务！");
        }
        int ret = employeeMapper.insertSelective(employee);
        logger.debug("数据库影响行数为：" + ret);
        return ret;
    }

    public int updateEmployee(Employee employee){
        if (logger.isDebugEnabled()) {
            logger.debug("开始更新用户信息，已经通过xml配置文件方式开启事务！");
        }
        int ret = employeeMapper.updateByPrimaryKeySelective(employee);
        logger.debug("数据库影响行数为：" + ret);
        return ret;
    }
}
