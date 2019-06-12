package com.bookkeeping.common.session;

import com.bookkeeping.controller.response.Response;
import com.bookkeeping.entity.UserSession;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

/**
 * author: liuyazong <br>
 * datetime: 2019-06-04 16:37 <br>
 * <p></p>
 */
@Component
@Slf4j
@WebFilter(urlPatterns = {"/*"})
public class SessionFilter extends OncePerRequestFilter implements OrderedFilter {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private SessionService<UserSession> sessionService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if(requestURI.contains("login") || requestURI.contains("bindMobile")){
            filterChain.doFilter(request,response);
            return;
        }

        String sessionId = request.getHeader("session-id");
        if (!StringUtils.hasText(sessionId)) {
            sessionId = request.getHeader("sessionId");
        }
        if (!StringUtils.hasText(sessionId)) {
            sessionId = request.getParameter("session-id");
        }
        if (!StringUtils.hasText(sessionId)) {
            sessionId = request.getParameter("sessionId");
        }
        log.debug("session-id {}",sessionId);
        if (StringUtils.hasText(sessionId)) {
            try {
                UserSession session = new UserSession();
                session.setSessionId(sessionId);
                Optional<UserSession> loadSession = sessionService.load(session);
                if (loadSession.isPresent()) {
                    log.debug("session: {}", loadSession);
                    HttpSession httpSession = request.getSession(true);
                    httpSession.setAttribute("user", loadSession.get());
                    filterChain.doFilter(request, response);
                } else {
                    log.debug("未登陆");
                    response.setStatus(401);
                    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                    PrintWriter writer = response.getWriter();
                    Response<String> msg = Response.<String>builder().flag("0").message("未登陆").build();
                    writer.write(objectMapper.writeValueAsString(msg));
                    writer.close();
                    return;
                }
            } catch (Exception e) {
                if (e instanceof ServletException) {
                    throw (ServletException) e;
                }
                if (e instanceof IOException) {
                    throw (IOException) e;
                }
                throw new ServletException(e);
            }
        }else {
            log.debug("未登陆");
            response.setStatus(401);
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            PrintWriter writer = response.getWriter();
            Response<String> msg = Response.<String>builder().flag("0").message("未登陆").build();
            writer.write(objectMapper.writeValueAsString(msg));
            writer.close();
            return;
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
