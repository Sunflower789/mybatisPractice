package com.demo.controller;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.demo.entity.User;
import com.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 多数据源测试：
 * yml配置 + @DS注解
 * */
@DS("master_1")
@RestController
@RequestMapping("/dynamic")
public class DateSourceController {

    @Autowired
    private IUserService iUserService;

    @PostMapping("/t1")
    public boolean t1() {
        User user = new User();
        user.setAge(25);
        user.setName("bb2");
        user.setUserLevel("A1");
        return iUserService.save(user);
    }

    @PostMapping("/t2")
    @DS("slave_1")
    public boolean t2() {
        User user = new User();
        user.setAge(25);
        user.setName("bb2");
        user.setUserLevel("A1");
        return iUserService.save(user);
    }
}
