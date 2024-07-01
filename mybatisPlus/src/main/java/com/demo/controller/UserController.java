package com.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.dto.PageDTO;
import com.demo.entity.User;
import com.demo.mapper.UserMapper;
import com.demo.request.PageRequest;
import com.demo.service.IUserService;
import com.alibaba.fastjson.JSONObject;
import com.demo.vo.UserVO;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2024-05-08
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    IUserService iUserService;

    @PostMapping("/t1")
    public String t1() {
        User user = iUserService.getById("9");
        System.out.println(user);
        return JSONObject.toJSONString(user);
    }

    // 复杂更新
    @PostMapping("/t2")
    public String t2() {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(User::getAge,24)
                .like(User::getName,"bb")
                .set(User::getUserLevel,"A2");
        boolean update = iUserService.update(updateWrapper);
        System.out.println(update);
        return JSONObject.toJSONString(update);
    }

    // 分页插件查询
    @PostMapping("/t3")
    public String t3() {
        Page<User> page = new Page<>(1,3);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().gt(User::getAge,23)
                .lt(User::getAge,30)
                .orderBy(true,false,User::getName);
        IPage<User> pageResult = iUserService.page(page, queryWrapper);
        return JSONObject.toJSONString(pageResult);
    }

    // 分页插件查询, 封装入参和出参
    @PostMapping("/t4")
    public PageDTO<UserVO> t4(@RequestBody PageRequest<User> pageRequest) {
        Page<User> page = PageRequest.buildPage(pageRequest);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        if(null != pageRequest.getRequestData()){
            queryWrapper.eq(null != pageRequest.getRequestData().getAge(), User::getAge,24)
                    .orderBy(StringUtils.hasText(pageRequest.getRequestData().getName()),false,User::getName);
        }
        IPage<User> pageResult = iUserService.page(page, queryWrapper);

        return PageDTO.buildOf(pageResult, user -> {
            UserVO userVO = new UserVO();
            userVO.setDate(new Date());
            BeanUtils.copyProperties(user,userVO,UserVO.class);
            return userVO;
        });
    }

    // 流式操作
    @PostMapping("/t5")
    public void t5(){
        userMapper.selectList(Wrappers.emptyWrapper(), new ResultHandler<User>() {
            int count = 0;
            @Override
            public void handleResult(ResultContext<? extends User> resultContext) {
                User user = resultContext.getResultObject();
                System.out.println("当前处理第" + (++count) + "条记录: " + user);
                // 在这里进行你的业务处理，比如分发任务
            }
        });
    }

    // 批量
    @PostMapping("/t6")
    //@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {RuntimeException.class,Error.class})
    public void t6(){
        List<User> userList = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            User user = new User();
            user.setAge(28);
            user.setName("dd" + i);
            if(i==5){
                user.setName("qweyuuiouoojklkhjhjkhh1k2h4hj1h4j1hjhjk12h3j1hj3h12j3h1jh3j1h2j31hjh1jgjacjacjbajkdjshjdajkcnja");
            }
            user.setUserLevel("A3");
            userList.add(user);
        }
        System.out.println(userList.size());
        iUserService.saveBatch(userList,3);
    }

}
