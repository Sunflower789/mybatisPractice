package com.demo;

import com.demo.entity.User;
import com.demo.mapper.UserMapper;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MybatisSpringbootApplication.class)
@MapperScan("com.demo.mapper")
@SpringBootApplication
public class MybatisSpringbootApplication {

    @Resource
    SqlSessionFactory sqlSessionFactory;

    @Resource
    UserMapper userMapper;

    public static void main(String[] args) {
        SpringApplication.run(MybatisSpringbootApplication.class, args);
    }

    /** springboot+mybatis框架整合测试 */
    @Test
    public void test1(){
        User user = userMapper.selectUser(7L);
        System.out.println(user);
    }

    /** 复杂动态SQL查询 */
    @Test
    public void test2(){
        Map<String,Object> map = new HashMap<>(8);
        map.put("name","lisi");
        List<Long> ids = new ArrayList<>();
        ids.add(7L);
        ids.add(9L);
        map.put("ids",ids);
        List<User> userList = userMapper.select1(map,26);
        System.out.println(userList);
    }

    /** 测试 useGeneratedKeys，keyProperty 属性 */
    @Test
    public void test3(){
        User userParam = new User();
        userParam.setAge(24);
        userParam.setName("aa1");
        userParam.setUserLevel("A1");
        Long id = userMapper.insertOne(userParam);
        System.out.println(userParam.getId());  //回显获取useGeneratedKeys="true" keyProperty="id"配置获取生效的id
    }

    /** 批量插入测试 */
    @Test
    public void test4(){
        List<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setAge(24);
        user1.setName("aa2");
        user1.setUserLevel("A2");
        User user2 = new User();
        user2.setAge(24);
        user2.setName("aa3");
        user2.setUserLevel("A2");
        users.add(user1);
        users.add(user2);

        // 方法一：使用批量sql语句执行
        Long id = userMapper.insertBatch(users);

        // 方法二：使用sqlSession批量预处理后手动提交
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        for (User user : users) {
            mapper.insertOne(user);
        }
        sqlSession.commit();  //sqlSession默认是不自动提交的，需要手动提交
    }
}
