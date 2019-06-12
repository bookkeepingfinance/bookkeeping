package com.bookkeeping.common.session;

import com.bookkeeping.entity.UserSession;

import javax.swing.text.html.Option;
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

    /**
    * 通过sessionID获取userID
    *
    * @Author:wanghua
    * @Date:12:04 2019-06-12
    */

    Long findUserId(String sessionId);

}
