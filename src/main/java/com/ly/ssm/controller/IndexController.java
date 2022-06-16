package com.ly.ssm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @FileName:IndexController.class
 * @Author:ly
 * @Date:2022/6/16
 * @Description:
 */
@Controller
public class IndexController {

    @RequestMapping("/hello")
    public String toIndex() {
        return "empList.html";
    }
}
