package com.bookkeeping.common.session;

import com.bookkeeping.entity.UserSession;
import com.bookkeeping.mapper.UserSessionMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

/**
 * author: liuyazong <br>
 * datetime: 2019-06-04 17:19 <br>
 * <p></p>
 */
@Service
public class DatabaseSessionServiceImpl implements SessionService<SessionService.Session> {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserSessionMapper userSessionMapper;

    /**
     * 保存session到数据库
     * @param session
     * @throws Exception
     */
    @Override
    public void save(Session session) throws Exception {
        UserSession userSession = new UserSession();
        userSession.setSessionId(session.getSessionId());
        userSession.setUserId(session.getUserId());
        Optional<UserSession> load0 = this.load0(userSession);
        if (load0.isPresent()) {//更新
            UserSession us = load0.get();
            us.setSessionData(objectMapper.writeValueAsString(session.getSessionData()));
            userSessionMapper.update(us);
        } else {//新增
            userSessionMapper.insert(userSession);
        }
    }

    /**
     * 从数据库加载session
     * @param session
     * @return
     * @throws Exception
     */
    @Override
    public Optional<Session> load(Session session) throws Exception {
        UserSession userSession = new UserSession();
        userSession.setSessionId(session.getSessionId());
        userSession.setUserId(session.getUserId());
        return load0(userSession)
                .map(us -> {
                    Long id = us.getId();
                    Long userId = us.getUserId();
                    String sessionId = us.getSessionId();
                    try {
                        Session s = new Session();
                        s.setId(id);
                        s.setUserId(userId);
                        s.setSessionId(sessionId);
                        s.setSessionData(objectMapper.readValue(us.getSessionData(), Session.SessionData.class));
                        return s;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                });
    }

    private Optional<UserSession> load0(UserSession userSession) {
        return userSessionMapper.find(userSession);
    }
}
