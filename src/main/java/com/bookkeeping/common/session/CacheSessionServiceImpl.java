package com.bookkeeping.common.session;

import com.bookkeeping.entity.UserSession;
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
public class CacheSessionServiceImpl implements SessionService<UserSession> {

    private String prefix = "user:session";

    @Autowired
    private CacheManager cacheManager;

    @Override
    public void save(UserSession session) throws Exception {
        String sessionId = session.getSessionId();
        Cache cache = cacheManager.getCache(prefix);
        cache.put(sessionId, session);
    }

    @Override
    public Optional<UserSession> load(UserSession session) throws Exception {
        String sessionId = session.getSessionId();
        Cache cache = cacheManager.getCache(prefix);
        UserSession result = cache.get(sessionId, UserSession.class);
        return Optional.ofNullable(result);
    }
}
