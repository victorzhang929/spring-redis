package com.vz.redis.user.service.impl;

import java.util.List;

import com.vz.redis.base.service.AbstractBaseRedisService;
import com.vz.redis.user.bean.User;
import com.vz.redis.user.mapper.UserMapper;
import com.vz.redis.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends AbstractBaseRedisService<String, User> implements UserService {

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
