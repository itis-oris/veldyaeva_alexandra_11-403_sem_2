package com.task.tracker.web.controller;


import com.task.tracker.web.client.AuthClient;
import com.task.tracker.web.dto.Identity;
import com.task.tracker.web.dto.LoginForm;
import com.task.tracker.web.dto.RegisterForm;
import com.task.tracker.web.dto.TokenPair;
import com.task.tracker.web.service.UserSession;
import com.task.tracker.web.util.SessionUtils;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequiredArgsConstructor
public class AuthController {

    private final SessionUtils sessionUtils;
    private final AuthClient authClient;


    @GetMapping("/")
    public String root(HttpSession session) {
        return sessionUtils.ok(session) ? "redirect:/tasks" : "redirect:/home";
    }

    @GetMapping("/login")
    public String loginPage(Model model, HttpSession session) {
        if (sessionUtils.ok(session)){
            return "redirect:/tasks";
        }
        model.addAttribute("loginForm", new LoginForm());
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginForm form, BindingResult br,
                        HttpSession session, Model model) {
        if (br.hasErrors()) {
            return "auth/login";
        }
        try {
            TokenPair pair = authClient.login(form);

            Identity identity = authClient.validate(pair.accessToken());
            if (identity == null) {
                throw new RuntimeException("Не удалось подтвердить токен");
            }

            UserSession us = new UserSession();
            us.setAccessToken(pair.accessToken());
            us.setRefreshToken(pair.refreshToken());
            us.setAccountId(identity.id());
            us.setUsername(identity.username());
            us.setRole(identity.role());
            us.setValidatedAt(System.currentTimeMillis());
            sessionUtils.save(session, us);

            return "redirect:/tasks";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "auth/login";
        }
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute RegisterForm form,
                           BindingResult br,
                           Model m,
                           RedirectAttributes flash) {
        if (br.hasErrors()) {
            return "auth/register";
        }
        try {
            authClient.register(form);
            flash.addFlashAttribute("success", "Аккаунт создан! Войдите.");
            return "redirect:/login";
        } catch (Exception e) {
            m.addAttribute("error", e.getMessage());
            return "auth/register";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        UserSession us = sessionUtils.get(session);

        if (us != null && us.getAccessToken() != null) {
            authClient.logout(us.bearer());
        }

        sessionUtils.clear(session);

        return "redirect:/login";
    }
}
