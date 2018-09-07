package com.vz.redis.user.mapper;

import java.util.List;

import com.vz.redis.user.bean.User;

public interface UserMapper {
    List<User> queryUser();

    Integer countUser();
}
