package com.demo.mapper;

import com.demo.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    User selectUser(@Param("id") Long id);
}
