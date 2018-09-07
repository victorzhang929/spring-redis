package com.vz.redis.base.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

public abstract class AbstractBaseRedisService<K, V> implements BaseRedisService {

    @Autowired
    protected RedisTemplate<K, V> redisTemplate;

    public void setRedisTemplate(RedisTemplate<K, V> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    protected RedisSerializer<String> getRedisSerializer() {
        return redisTemplate.getStringSerializer();
    }

    @Override
    public boolean add(String key, String value) {
        boolean result = false;
        if (null != key && !StringUtils.isBlank(key)) {
            result = redisTemplate.execute((RedisConnection connection) -> {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] keyByte = serializer.serialize(key);
                byte[] valueByte = serializer.serialize(value);
                return connection.setNX(keyByte, valueByte);
            });
        }
        return result;
    }

    @Override
    public boolean add(List<Map<String, String>> list) {
        boolean result = false;
        if (null != list && !list.isEmpty()) {
            result = redisTemplate.execute((RedisConnection connection) -> {
                RedisSerializer<String> serializer = getRedisSerializer();
                for (Map<String, String> map : list) {
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        byte[] key = serializer.serialize(entry.getKey());
                        byte[] value = serializer.serialize(entry.getValue());
                        connection.setNX(key, value);
                    }
                }
                return true;
            }, false, true);
        }
        return result;
    }

    @Override
    public void delete(String key) {
        if (null != key && !StringUtils.isBlank(key)) {
            List<String> list = new ArrayList<>();
            list.add(key);
            delete(list);
        }
    }

    @Override
    public void delete(List<String> keys) {
        if (null != keys && !keys.isEmpty()) {
            redisTemplate.execute((RedisConnection connection) -> {
                RedisSerializer<String> serializer = getRedisSerializer();
                for (String key : keys) {
                    byte[] keyByte = serializer.serialize(key);
                    connection.del(keyByte);
                }
                return true;
            }, false, true);
        }
    }

    @Override
    public boolean update(String key, String value) {
        if (null == get(key)) {
            throw new NullPointerException("the key not exist:" + key);
        }
        boolean result = redisTemplate.execute((RedisConnection connection) -> {
            RedisSerializer<String> serializer = getRedisSerializer();
            byte[] keyByte = serializer.serialize(key);
            byte[] valueByte = serializer.serialize(value);
            connection.set(keyByte, valueByte);
            return true;
        });
        return result;
    }

    @Override
    public Map<String, String> get(String keyId) {
        Map<String, String> result = new HashMap<>();
        if (null != keyId && !StringUtils.isBlank(keyId)) {
            result = redisTemplate.execute((RedisConnection connection) -> {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] keyByte = serializer.serialize(keyId);
                byte[] valueByte = connection.get(keyByte);

                if (null == valueByte) {
                    return null;
                }

                String valueStr = serializer.deserialize(valueByte);
                Map<String, String> map = new HashMap<>();
                map.put(keyId, valueStr);
                return map;
            });
        }
        return result;
    }
}
