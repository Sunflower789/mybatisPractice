import entity.User;
import mapper.UserMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-dev.xml");
        UserMapper userMapper = (UserMapper) applicationContext.getBean(UserMapper.class);

        User user = userMapper.selectUser(7L);
        System.out.println(user);
    }
}
