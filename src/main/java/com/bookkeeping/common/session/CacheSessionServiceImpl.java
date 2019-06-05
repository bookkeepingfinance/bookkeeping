package com.bookkeeping.common.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * author: liuyazong <br>
 * datetime: 2019-06-05 10:00 <br>
 * <p></p>
 */
@Service
public class CacheSessionServiceImpl implements SessionService<SessionService.Session> {

    private String prefix = "user:session";

    @Autowired
    private CacheManager cacheManager;

    @Override
    public void save(Session session) throws Exception {
        String sessionId = session.getSessionId();
        Cache cache = cacheManager.getCache(prefix);
        cache.put(sessionId, session);
    }

    @Override
    public Optional<Session> load(Session session) throws Exception {
        String sessionId = session.getSessionId();
        Cache cache = cacheManager.getCache(prefix);
        Session result = cache.get(sessionId, Session.class);
        return Optional.ofNullable(result);
    }
}
