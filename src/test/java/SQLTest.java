import com.ly.ssm.bean.Employee;
import com.ly.ssm.dao.EmployeeMapper;
import org.apache.ibatis.io.DefaultVFS;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
;
import java.util.List;

/**
 * @Author : Ly
 * @Date : 2022/6/15
 * @Description :
 */
public class SQLTest {
    private Logger logger = LoggerFactory.getLogger(SQLTest.class);
    @Test
    public void test() throws Exception {
        SqlSession sqlSession = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("test/mybatis-configTest.xml")).openSession();
        EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
        List<Employee> employees = employeeMapper.selectByExample(null);
        Employee employeeKey = employeeMapper.selectByPrimaryKeyWithDepartment(1);
        logger.debug("所有数据如下：");
        if (logger.isDebugEnabled()) {
            logger.debug("所有数据如下：" + employeeKey);
        }
        employees.forEach(System.out::println);
        //DefaultVFS
    }
}
