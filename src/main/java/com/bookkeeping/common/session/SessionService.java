package com.bookkeeping.common.session;

import com.bookkeeping.entity.UserSession;

import java.util.Optional;

/**
 * author: liuyazong <br>
 * datetime: 2019-06-04 16:41 <br>
 * <p></p>
 */
public interface SessionService<T extends UserSession> {

    /**
     * 保存session
     * @param session
     * @throws Exception
     */
    void save(T session) throws Exception;

    /**
     * 加载session
     * @param session
     * @return
     * @throws Exception
     */
    Optional<T> load(T session) throws Exception;


}
