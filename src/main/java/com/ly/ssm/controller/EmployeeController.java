package com.ly.ssm.controller;

import com.ly.ssm.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @FileName:EmployeeController.class
 * @Author:ly
 * @Date:2022/6/16
 * @Description: 处理员工的CRUD请求
 */
@Controller
@RequestMapping("/emps")
public class EmployeeController {
    private Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    @Autowired
    private EmployeeService employeeService;
    /**
     * get请求 返回所有员工列表 (分页查询)
     * @param pageNo 当前页码  （sql中为pageNo -1）
     * @param pageSize 每页显示几条数据，默认是5跳
     * @return 跳转到员工列表展示页面
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getEmpList(@RequestParam(required = false,value = "pageNo") Integer pageNo,
                             @RequestParam(required = false,value = "pageSize") Integer pageSize,ModelAndView modelAndView) {
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
            pageNo = 5;
        }

        //获取所有员工信息，放到请求中
        modelAndView.addObject("pageInfo",employeeService.getAllEmployees(pageNo,pageSize));
        modelAndView.setViewName("empList");

        return modelAndView;
    }
}
