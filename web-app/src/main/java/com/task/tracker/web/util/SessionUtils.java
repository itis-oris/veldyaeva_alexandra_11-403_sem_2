package com.task.tracker.web.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.tracker.web.service.UserSession;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.UUID;

@Component
public class SessionUtils {

    private static final String KEY = "US";

    public UserSession get(HttpSession httpSession) {
        return (UserSession) httpSession.getAttribute(KEY);
    }

    public boolean ok(HttpSession httpSession) {
        UserSession userSession = get(httpSession);
        return userSession != null && userSession.loggedIn();
    }

    public void save(HttpSession httpSession, UserSession userSession) {
        httpSession.setAttribute(KEY, userSession);
    }

    public void clear(HttpSession httpSession) {
        httpSession.invalidate();
    }
}
