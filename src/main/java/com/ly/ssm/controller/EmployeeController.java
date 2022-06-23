package com.ly.ssm.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ly.ssm.bean.Employee;
import com.ly.ssm.service.DepartmentService;
import com.ly.ssm.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Locale;

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
    @Autowired
    private DepartmentService departmentService;
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


    /**
     * post请求，专门用于新增员工信息
     * @return 返回结果json
     *  json {
     *      success:true/false,
     *      msg:
     *  }
     */
    @ResponseBody
    @RequestMapping(value = "",method = RequestMethod.POST)
    public String addEmployee(@RequestParam("empName") String empName,@RequestParam("email") String email,
                              @RequestParam("gender") String gender,@RequestParam("deptId") Integer deptId) {
        HashMap<String, Object> reply = new HashMap<>();
        if (logger.isDebugEnabled()) {
            logger.debug("开始前端传回数据校验！");
        }
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            if (empName==""||empName==null) {
                reply.put("success",false);
                reply.put("msg","用户名为空!");
                return objectMapper.writeValueAsString(reply);
            }
            if (email==""||email==null) {
                reply.put("success",false);
                reply.put("msg","邮箱为空!");
                return objectMapper.writeValueAsString(reply);
            }
            if (gender==""||gender==null) {
                reply.put("success",false);
                reply.put("msg","性别为空!");
                return objectMapper.writeValueAsString(reply);
            }
            if (deptId < 0||deptId==null) {
                reply.put("success",false);
                reply.put("msg","部门不存在！");
                return objectMapper.writeValueAsString(reply);
            }

            if (logger.isDebugEnabled()) {
                logger.debug("参数数据格式校验完成，开始校验用户名和性别是否重复？部门是否存在？");
            }

            if (!departmentService.isExistsDepartment(deptId)) {
                logger.info("该部门信息不存在！");
                reply.put("success",false);
                reply.put("msg","该部门信息不存在！");
                return objectMapper.writeValueAsString(reply);
            }
            if (employeeService.isExistsNameAndGender(empName.toLowerCase(),gender.toLowerCase())) {
                logger.info("用户名 " + empName + " 已存在！");
                reply.put("success",false);
                reply.put("msg","用户名已存在");
                return objectMapper.writeValueAsString(reply);
            }
            Employee newEmployee = new Employee(null, empName.toLowerCase(), gender.toLowerCase(), email.toLowerCase(), deptId);
            if (employeeService.addEmployee(newEmployee) < 0) {
                logger.error("数据插入失败！");
                reply.put("success",false);
                reply.put("msg","后台操作失败，请联系管理员");
                return objectMapper.writeValueAsString(reply);
            }

            reply.put("success",true);
            reply.put("msg","成功");
            return objectMapper.writeValueAsString(reply);
        } catch (JsonProcessingException e) {
            logger.error("json数据转换失败，" + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    }


    @ResponseBody
    @RequestMapping(value = {"/{empId}"},method = RequestMethod.PUT)
    public String updateEmployee(@PathVariable("empId") Integer empId,@RequestParam("empName") String empName,
                                 @RequestParam("email") String email,@RequestParam("deptId") Integer deptId)  {
        if (logger.isDebugEnabled()) {
            logger.debug("前端接收到信息：empId=" + empId + ",enmName=" + empName + ",email=" + email + ",deptId=" + deptId);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> reply = new HashMap<>();
        try {
            //查询该用户名和新部门是否存在。
            if (!employeeService.isExistsNameAndId(empName.toLowerCase(),empId)) {
                logger.info("该用户信息不存在！");
                reply.put("success",false);
                reply.put("msg","该用户信息不存在！ empName=" + empName);
                return objectMapper.writeValueAsString(reply);
            }

            if (!departmentService.isExistsDepartment(deptId)) {
                logger.info("该部门信息不存在！");
                reply.put("success",false);
                reply.put("msg","该部门信息不存在！ deptId=" + deptId);
                return objectMapper.writeValueAsString(reply);
            }

            Employee employee = new Employee(empId, empName, null, email, deptId);
            if (employeeService.updateEmployee(employee) < 0) {
                logger.error("用户信息修改失败！");
                reply.put("success",false);
                reply.put("msg","后台操作失败，请联系管理员");
                return objectMapper.writeValueAsString(reply);
            }
            reply.put("success",true);
            reply.put("msg","成功");
            return objectMapper.writeValueAsString(reply);
        } catch(JsonProcessingException e) {
            logger.error("数据json化失败" + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/{empId}",method = RequestMethod.DELETE)
    public String delEmployeeById(@PathVariable("empId") Integer empId){
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> reply = new HashMap<>();
        if (logger.isDebugEnabled()) {
            logger.debug("接收到要删除的id：" + empId);
        }
        try {
            //根据id判断用户是否存在
            if (empId == null||empId < 0 ||!employeeService.isExistsId(empId)) {
                logger.info("该用户id不存在！");
                reply.put("success",false);
                reply.put("msg","该用户id不存在！");
                return objectMapper.writeValueAsString(reply);
            }

            //开始删除
            if (employeeService.delEmployeeById(empId) < 1) {
                logger.info("该用户删除失败，请联系管理员！");
                reply.put("success",false);
                reply.put("msg","该用户删除失败，请联系管理员！");
                return objectMapper.writeValueAsString(reply);
            }

            reply.put("success",true);
            reply.put("msg","删除成功！");
            return objectMapper.writeValueAsString(reply);
        } catch (JsonProcessingException e) {
            logger.error("json数据转换异常reply=" + reply + "\n" + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/mulDel",method = RequestMethod.POST)
    public String multiDelEmployee(@RequestParam("empIdArr") Integer[] empIdArr){
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> reply = new HashMap<>();
        if (logger.isDebugEnabled()) {
            logger.debug("接收到前端传来的id数组：empIdArr=" + empIdArr);
        }
        try {
            if (empIdArr.length == 0) {
                logger.info("批量删除失败，没有选择任何用户！");
                reply.put("success",false);
                reply.put("msg","删除失败，没有选择任何用户！");
                return objectMapper.writeValueAsString(reply);
            }

            //批量删除
            if (employeeService.multiDelEmployeeByIds(empIdArr) < empIdArr.length) {
                logger.info("有数据批量删除失败，请联系管理员！");
                reply.put("success",false);
                reply.put("msg","有数据批量删除失败，请联系管理员！");
                return objectMapper.writeValueAsString(reply);
            }

            reply.put("success",true);
            reply.put("msg","数据批量删除成功！");
            return objectMapper.writeValueAsString(reply);
        } catch (JsonProcessingException e) {
            logger.error("json数据转换异常reply=" + reply + "\n" + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    }
}
