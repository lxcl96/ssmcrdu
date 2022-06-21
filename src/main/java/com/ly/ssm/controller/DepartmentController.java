package com.ly.ssm.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ly.ssm.bean.Department;
import com.ly.ssm.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @FileName:DepartmentController.class
 * @Author:ly
 * @Date:2022/6/21
 * @Description:
 */
@Controller
@RequestMapping("depts")
public class DepartmentController {
    private Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    private DepartmentService departmentService;

    /**
     * 查询后台所有部门数据
     */
    @RequestMapping(method = RequestMethod.GET,value = {""})
    @ResponseBody
    public String getAllDepartment() {
        ObjectMapper objectMapper = new ObjectMapper();
        String deptsJson = null;
        try {
            deptsJson = objectMapper.writeValueAsString(departmentService.getAllDepartment());
        } catch (JsonProcessingException e) {
            logger.error("");
            throw new RuntimeException(e.getMessage());
        }

        return deptsJson;
    }
}
