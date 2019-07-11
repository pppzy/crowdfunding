import com.itpzy.crowdfunding.bean.User;
import com.itpzy.crowdfunding.manager.dao.UserMapper;
import com.itpzy.crowdfunding.util.MD5Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ="classpath:spring/spring-context.xml" )
public class Test1 {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 测试：用于向表中增加100条数据
     */
    @Test
    public void Test1(){
        UserMapper mapper = sqlSessionTemplate.getMapper(UserMapper.class);
        User user = new User();
        user.setLoginacct("test");
        user.setUserpswd(MD5Util.digest("123"));
        user.setUsername("test");
        user.setEmail("test@qq.com");
        user.setCreatetime("2019-07-07 20:12:30");
        for (int i=101;i<=200;i++){
            user.setLoginacct("test"+i);
            user.setEmail("test"+i+"@qq.com");
            mapper.insert(user);
        }

    }




}
