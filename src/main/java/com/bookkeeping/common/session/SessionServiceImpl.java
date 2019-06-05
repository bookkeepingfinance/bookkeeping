package com.bookkeeping.common.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * author: liuyazong <br>
 * datetime: 2019-06-04 16:53 <br>
 * <p></p>
 */
@Service
@Primary
public class SessionServiceImpl implements SessionService<SessionService.Session> {

    @Autowired(required = false)
    @Qualifier(value = "databaseSessionServiceImpl")
    private SessionService<Session> databaseSessionService;

    @Autowired
    @Qualifier(value = "cacheSessionServiceImpl")
    private SessionService<Session> cacheSessionService;


    @Override
    public void save(Session session) throws Exception {
        if (null != databaseSessionService) {
            databaseSessionService.save(session);
        }
        cacheSessionService.save(session);
    }

    @Override
    public Optional<Session> load(Session session) throws Exception {
        Optional<Session> result = cacheSessionService.load(session);
        if (result.isPresent()) {
            return result;
        }
        if (null != databaseSessionService) {
            //从数据库加载
            result = databaseSessionService.load(session);
            if (result.isPresent()) {
                cacheSessionService.save(result.get());
                return result;
            }
        }
        return Optional.empty();
    }
}
