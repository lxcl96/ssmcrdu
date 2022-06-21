package com.ly.ssm.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ly.ssm.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @FileName:EmployeeController.class
 * @Author:ly
 * @Date:2022/6/16
 * @Description: 处理员工的CRUD请求
 */
@Controller
@RequestMapping({"/emps"})
public class EmployeeController {
    private Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    @Autowired
    private EmployeeService employeeService;
    /**
     * get请求 返回所有员工列表 (分页查询) 【三种请求地址】
     * @param pageNo 当前页码  （sql中为pageNo -1）
     * @param pageSize 每页显示几条数据，默认是5条
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET,value = {"/{pageNo}/{pageSize}","/{pageNo}",""})
    public String getEmpList(@PathVariable(required = false,value = "pageNo") Integer pageNo,
                                   @PathVariable(required = false,value = "pageSize") Integer pageSize) {
        if (pageNo == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("当前请求没有携带当前页码 参数：pageNo，默认为 1");
            }
            pageNo = 1;
        }
        if (pageSize == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("当前请求没有携带分页条数 参数：pageSize，默认为 5");
            }
            pageSize = 5;
        }

        //获取所有员工信息，放到请求中
        ObjectMapper objectMapper = new ObjectMapper();
        String pageInfoString = null;
        try {
            pageInfoString = objectMapper.writeValueAsString(employeeService.getAllEmployees(pageNo, pageSize));
        } catch (JsonProcessingException e) {
            logger.error("分页信息json化异常：" + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return pageInfoString;
    }

}
