import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Map;

/**
 * @FileName:MVCTest.class
 * @Author:ly
 * @Date:2022/6/30
 * @Description: 使用Spring测试模块提供的测试请求功能，模拟前端请求发送get/post等请求
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml","classpath:spring/springmvc-config.xml"})
public class MVCTest {

    @Autowired //只能自动注入IOC里面的属性，如果要自动注入IOC容器自己则需要添加@WebAppConfiguration注解
    //传入Springmvc的IOC容器
    WebApplicationContext webApplicationContext;

    //虚拟MVC请求，获取返回结果（要使用需要先初始化）
    MockMvc mockMvc;

    @Before //每次测试都要先执行，所以加上 org.junit.Before;（junit包下的）
    public void initMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    //测试代码--分页代码
    @Test
    public void testPage() throws Exception {
        //perform方法就是模拟放松get等请求 param方法表示请求的参数 andReturn拿到请求的返回值
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/emps/99")).andReturn();

        //请求成功后，结果是直接返回了。所以直接获取Response即可
        MockHttpServletResponse response = result.getResponse();
        String contentAsString = response.getContentAsString();
        System.out.println(contentAsString);
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode jsonNode = objectMapper.readTree(contentAsString);
//        List<JsonNode> list = jsonNode.findValues("list");
//        list.forEach(user -> System.out.println(user));

    }

}
