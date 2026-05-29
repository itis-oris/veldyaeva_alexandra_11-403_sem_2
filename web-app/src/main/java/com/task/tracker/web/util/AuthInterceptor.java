package com.task.tracker.web.util;

import com.task.tracker.web.client.AuthClient;
import com.task.tracker.web.dto.Identity;
import com.task.tracker.web.dto.TokenPair;
import com.task.tracker.web.service.UserSession;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    private final SessionUtils sessionUtils;
    private final AuthClient authClient;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        HttpSession session = request.getSession(false);
        UserSession us = session != null ? sessionUtils.get(session) : null;

        if (us == null || !us.loggedIn()) {
            return requestSetting(request, response);
        }

        Identity identity = authClient.validate(us.bearer());

        if (identity == null && us.getRefreshToken() != null) {
            TokenPair pair = authClient.refresh(us.getRefreshToken());
            if (pair != null) {
                us.setAccessToken(pair.accessToken());
                us.setRefreshToken(pair.refreshToken());
                identity = authClient.validate(us.bearer());
                log.debug("Access token refreshed | accountId={}", us.getAccountId());
            }
        }

        if (identity == null) {
            sessionUtils.clear(session);
            return requestSetting(request, response);
        }

        us.setAccountId(identity.id());
        us.setUsername(identity.username());
        us.setRole(identity.role());
        us.setValidatedAt(System.currentTimeMillis());
        sessionUtils.save(session, us);
        return true;
    }

    private boolean requestSetting(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (request.getRequestURI().startsWith("/api/")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"error\":\"Не авторизован\"}");
        } else {
            response.sendRedirect("/login");
        }
        return false;
    }
}
