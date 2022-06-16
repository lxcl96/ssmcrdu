import com.ly.ssm.bean.Department;
import com.ly.ssm.bean.Employee;
import com.ly.ssm.dao.DepartmentMapper;
import com.ly.ssm.dao.EmployeeMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.UUID;

/**
 * @FileName:SpringMapperTest.class
 * @Author:ly
 * @Date:2022/6/16
 * @Description: 利用spring测试mapper文件
 * Spring项目就使用Spring的单元测试，可以自动注入我们需要的组件
 * 使用步骤：
 *      1、导入Spring单元测试模块 即SpringTest 的@ContextConfiguration
 *      2、@ContextConfiguration注解指定spring配置文件的位置，即location属性
 *      3、使用@RunWith注解 指定使用Spring单元测试来运行
 *      4、直接使用autowire自动注入我们需要的组件即可
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/applicationContext.xml"})
public class SpringMapperTest {

    //自动注入mybatis实现的接口动态匿名内部类 ，所有直接调用sql即可
    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private DepartmentMapper departmentMapper;
    @Test
    public void testCRUD(){
        //1、创建SpringIOC容器
        //ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        //2、从从容器在取出mapper
        //EmployeeMapper employeeMapper = applicationContext.getBean(EmployeeMapper.class);

        //推荐使用Spring的单元测试，会自动注入我们需要的组件 如mybatis
        System.out.println("=====" + departmentMapper);
        //List<Department> departments = departmentMapper.selectByExample(null);
        //System.out.println(departments);
        //int i = departmentMapper.insertSelective(new Department(null,"测试部"));
        //int j = departmentMapper.insertSelective(new Department(null,"后勤部"));
        //System.out.println("i = " + i + ",j = " + j);
        System.out.println("=====" + employeeMapper);
        //循环插入
//        for (int i = 1; i < 1000; i++) {
//            String uuid = UUID.randomUUID().toString().substring(0,5) + "_" + i;
//            employeeMapper.insertSelective(new Employee(null,uuid,"m",uuid + "@ly.com",1));
//        }
        Employee employee = employeeMapper.selectByPrimaryKeyWithDepartment(558);
        System.out.println(employee);

    }
}
