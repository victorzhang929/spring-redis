package com.victorzhang.redis.user.service.impl;

import java.util.List;

import com.victorzhang.redis.base.service.AbstractBaseRedisService;
import com.victorzhang.redis.user.bean.User;
import com.victorzhang.redis.user.mapper.UserMapper;
import com.victorzhang.redis.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends AbstractBaseRedisService<String,User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    public List<User> queryUser() {
        return userMapper.queryUser();
    }

    @Override
    public Integer countUser() {
        return userMapper.countUser();
    }
}
