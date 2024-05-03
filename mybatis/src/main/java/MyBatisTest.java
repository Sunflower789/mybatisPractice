import com.demo.entity.User;
import com.demo.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MyBatisTest {

    public static void main(String[] args) throws IOException {
        // 通过配置文件获取SqlSessionFactory
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        // 获取SqlSession对象
        try (SqlSession session = sqlSessionFactory.openSession()) {
            // 获取SqlSession对象
            //   --也可以不写Mapper接口直接通过namespace获取结果User user = (User) session.selectOne("com.demo.mapper.UserMapper.selectUser", 7L);但是官方建议写mapper接口
            UserMapper mapper = session.getMapper(UserMapper.class);
            // 执行方法(sql)并获取映射结果
            User user = mapper.selectUser(7L);
            System.out.println(user);
        }
    }
}
