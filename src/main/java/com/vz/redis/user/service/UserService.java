package com.vz.redis.user.service;

import java.util.List;

import com.vz.redis.base.service.BaseRedisService;
import com.vz.redis.user.bean.User;

public interface UserService extends BaseRedisService {

    List<User> queryUser();

    Integer countUser();
}
