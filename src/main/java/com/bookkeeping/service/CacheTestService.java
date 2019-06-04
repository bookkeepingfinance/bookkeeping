package com.bookkeeping.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * author: liuyazong <br>
 * datetime: 2019-06-04 14:47 <br>
 * <p></p>
 */
@Service
public class CacheTestService {

    @Cacheable(cacheNames = "TestService", key = "'test:'.concat(#s)")
    public Map<String, String> get(String s) {
        return null;
    }

    @CachePut(cacheNames = "TestService", key = "'test:'.concat(#s)")
    public Map<String, String> put(String s) {
        Map<String, String> map = new HashMap<>();
        map.put("s", s);
        return map;
    }

    @CacheEvict(cacheNames = "TestService", key = "'test:'.concat(#s)")
    public Map<String, String> delete(String s) {
        return null;
    }
}
