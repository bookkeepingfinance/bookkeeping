package com.bookkeeping.common.session;

import lombok.Data;

import java.util.Optional;

/**
 * author: liuyazong <br>
 * datetime: 2019-06-04 16:41 <br>
 * <p></p>
 */
public interface SessionService<T extends SessionService.Session> {

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

    @Data
    class Session {

        private Long id;
        private String sessionId;
        private Long userId;
        private SessionData sessionData;

        @Data
        public static class SessionData {
            String openId;
            String nikeName;
            Long age;
            String mobile;
            String deviceNo;
        }
    }
}
