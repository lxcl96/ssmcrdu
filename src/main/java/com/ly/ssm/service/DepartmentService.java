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

    public List<Department> getAllDepartment() {
        if (logger.isDebugEnabled()) {
            logger.debug("开始查询所有部门信息！");
        }

        return departmentMapper.selectByExample(null);
    }

}
