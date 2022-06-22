package com.ly.ssm.service;

import com.ly.ssm.bean.Department;
import com.ly.ssm.dao.DepartmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @FileName:DepartmentService.class
 * @Author:ly
 * @Date:2022/6/21
 * @Description:
 */
@Service
public class DepartmentService {
    private Logger logger = LoggerFactory.getLogger(DepartmentService.class);

    @Autowired
    private DepartmentMapper departmentMapper;

    /**
     * 查询所有部门信息
     * @return 查询结果集合
     */
    public List<Department> getAllDepartment() {
        if (logger.isDebugEnabled()) {
            logger.debug("开始查询所有部门信息！");
        }

        return departmentMapper.selectByExample(null);
    }

    /**
     * 根据部门id查询 该部门是否存在
     * @param deptId 部门id
     * @return true:存在，false:不存在
     */
    public Boolean isExistsDepartment(Integer deptId) {
        Department department = departmentMapper.selectByPrimaryKey(deptId);
        if (logger.isDebugEnabled()) {
            logger.debug("查询到的部门信息为：" + department);
        }
        return department != null;
    }
}
