package com.bookkeeping.service;

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
public class TestService {

    @Cacheable(cacheNames = "TestService", key = "'test:'.concat(#s)")
    public Map<String, String> test(String s) {
        Map<String, String> map = new HashMap<>();
        map.put("s", s);
        return map;
    }
}
