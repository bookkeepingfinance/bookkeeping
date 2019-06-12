package com.bookkeeping.common.session;

import com.bookkeeping.entity.UserSession;
import com.bookkeeping.mapper.UserSessionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * author: liuyazong <br>
 * datetime: 2019-06-04 17:19 <br>
 * <p></p>
 */
@Service
public class DatabaseSessionServiceImpl implements SessionService<UserSession> {

    @Autowired
    private UserSessionMapper userSessionMapper;


    /**
     * 保存session到数据库
     * @param session
     * @throws Exception
     */
    @Override
    public void save(UserSession session) throws Exception {

        Optional<UserSession> load0 = this.load0(session);
        if (load0.isPresent()) {//更新
            userSessionMapper.update(session);
        } else {//新增
            userSessionMapper.insert(session);
        }
    }

    /**
     * 从数据库加载session
     * @param session
     * @return
     * @throws Exception
     */
    @Override
    public Optional<UserSession> load(UserSession session) throws Exception {

        return load0(session);
    }

    @Override
    public Long findUserId(String sessionId) {
        return userSessionMapper.findUserId(sessionId);
    }

    private Optional<UserSession> load0(UserSession userSession) {
        return userSessionMapper.find(userSession);
    }
}
