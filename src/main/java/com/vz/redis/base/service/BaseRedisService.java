package com.vz.redis.base.service;

import java.util.List;
import java.util.Map;

public interface BaseRedisService {
    boolean add(final String key, final String value);

    boolean add(final List<Map<String, String>> list);

    void delete(final String key);

    void delete(final List<String> keys);

    boolean update(final String key, final String value);

    Map<String, String> get(final String keyId);
}
