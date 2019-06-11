package com.bookkeeping.common.session;

import com.bookkeeping.entity.UserSession;
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
public class SessionServiceImpl implements SessionService<UserSession> {

    @Autowired(required = false)
    @Qualifier(value = "databaseSessionServiceImpl")
    private SessionService<UserSession> databaseSessionService;

    @Autowired
    @Qualifier(value = "cacheSessionServiceImpl")
    private SessionService<UserSession> cacheSessionService;


    @Override
    public void save(UserSession session) throws Exception {
        if (null != databaseSessionService) {
            databaseSessionService.save(session);
        }
        cacheSessionService.save(session);
    }

    @Override
    public Optional<UserSession> load(UserSession session) throws Exception {
        Optional<UserSession> result = cacheSessionService.load(session);
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
