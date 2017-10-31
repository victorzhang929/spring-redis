package com.victorzhang.redis.user.mapper;

import java.util.List;

import com.victorzhang.redis.user.bean.User;

public interface UserMapper {
    List<User> queryUser();

    Integer countUser();
}
