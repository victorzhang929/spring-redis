package com.victorzhang.redis.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.alibaba.fastjson.JSONObject;
import com.victorzhang.redis.user.bean.User;
import com.victorzhang.redis.user.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = {"classpath*:spring-mvc.xml"})
public class RedisTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private UserService userService;

    @Test
    public void testAddUser() {
        User user = new User();
        user.setId("2");
        user.setName("victorzhang");
        user.setPassword("123456");
        String value = JSONObject.toJSONString(user);
        System.out.println(value);
        boolean result = userService.add(user.getId(), value);
        Assert.assertTrue(result);
    }

    @Test
    public void testUpdate() {
        User user = new User();
        user.setId("1");
        user.setName("lindahu");
        user.setPassword("123456");
        String value = JSONObject.toJSONString(user);
        System.out.println(value);
        boolean result = userService.update(user.getId(), value);
        Assert.assertTrue(result);
    }

    @Test
    public void testGetUser() {
        String id = "1";
        Map<String, String> value = userService.get(id);
        System.out.println(value.get(id));
        Assert.assertNotNull(value);
    }

    @Test
    public void testDelete() {
        String key = "name";
        userService.delete(key);
    }

    @Test
    public void testAddUsers() {
        List<User> users = new ArrayList<>();
        for (int i = 10; i < 150000; i++) {
            User user = new User();
            user.setId("userId" + i);
            user.setName("userName" + i);
            users.add(user);
        }
        long begin = System.currentTimeMillis();
        for (User user : users) {
            userService.add(user.getId(), JSONObject.toJSONString(user));
        }
        System.out.println(System.currentTimeMillis() - begin);
    }

    @Test
    public void testDeleteUsers() {
        List<String> users = new ArrayList<>();
        for (int i = 0; i < 150000; i++) {
            users.add("userId" + i);
        }
        long begin = System.currentTimeMillis();
        userService.delete(users);
        System.out.println(System.currentTimeMillis() - begin);
    }

    @Test
    public void testAddUsersPipline() {
        List<Map<String, String>> users = new ArrayList<>();
        for (int i = 0; i < 150000; i++) {
            User user = new User();
            user.setId("userId" + i);
            user.setName("username" + i);
            user.setPassword("userpassword" + i);
            Map<String, String> map = new HashMap<>();
            map.put(user.getId(), JSONObject.toJSONString(user));
            users.add(map);
        }
        long begin = System.currentTimeMillis();
        boolean result = userService.add(users);
        System.out.println(System.currentTimeMillis() - begin);
        Assert.assertTrue(result);
    }

    @Test
    public void test() {
        List<User> users = userService.queryUser();
        List<Map<String, String>> usersMap = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            Map<String, String> map = new HashMap<>();
            map.put("user" + i, JSONObject.toJSONString(user));
            usersMap.add(map);
        }
        userService.add(usersMap);

        Random random = new Random();
        int num = random.nextInt(usersMap.size()) - 1;
        Map<String, String> valueMap = userService.get("user" + num);
        JSONObject json = JSONObject.parseObject(valueMap.get("user" + num));
        System.out.println(json);
        System.out.println(json.get("name"));
    }

    @Test
    public void testMyBatis() {
        userService.countUser();
    }

}
