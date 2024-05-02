package com.demo;

import com.demo.entity.User;
import com.demo.mapper.UserMapper;
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
    UserMapper userMapper;

    public static void main(String[] args) {
        SpringApplication.run(MybatisSpringbootApplication.class, args);
    }

    @Test
    public void test1(){
        User user = userMapper.selectUser(7L);
        System.out.println(user);
    }

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
}
