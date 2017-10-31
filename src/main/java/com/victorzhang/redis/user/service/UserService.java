package com.victorzhang.redis.user.service;

import java.util.List;

import com.victorzhang.redis.base.service.BaseRedisService;
import com.victorzhang.redis.user.bean.User;

public interface UserService extends BaseRedisService {

    List<User> queryUser();

    Integer countUser();
}
