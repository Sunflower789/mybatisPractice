package com.demo.mapper;

import com.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

//@Mapper
public interface UserMapper {
    User selectUser(@Param("id") Long id);

    //多入参复杂动态sql查询
    List<User> select1(@Param("map") Map<String,Object> map, @Param("ageMax") Integer ageMax);

    Long insertOne(User user);

    Long insertBatch(@Param("users") List<User> users);
}
