package com.bookkeeping.config;

import com.bookkeeping.BookkeepingApplicationTests;
import com.bookkeeping.common.session.SessionService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Optional;

/**
 * author: liuyazong <br>
 * datetime: 2019-06-05 09:57 <br>
 * <p></p>
 */
public class SessionServiceTest extends BookkeepingApplicationTests {

//    @Autowired
//    @Qualifier(value = "cacheSessionServiceImpl")
//    private SessionService<SessionService.Session> cacheSessionService;

    /*@Test
    public void save() throws Exception {
        SessionService.Session session = new SessionService.Session();
        session.setSessionId("4a2155a9-cf06-4a85-b407-214a53d0e439");
        session.setUserId(1L);
        SessionService.Session.SessionData sessionData = new SessionService.Session.SessionData();
        session.setSessionData(sessionData);
        cacheSessionService.save(session);
        System.err.println(session.getSessionId());
    }

    @Test
    public void load() throws Exception {
        SessionService.Session session = new SessionService.Session();
        session.setSessionId("4a2155a9-cf06-4a85-b407-214a53d0e439");
        Optional<SessionService.Session> load = cacheSessionService.load(session);
        load.ifPresent(s->{
            System.err.println(s);
        });
    }*/

    /*@Test
    public void memory() throws Exception {
        save();
        load();
    }*/
}