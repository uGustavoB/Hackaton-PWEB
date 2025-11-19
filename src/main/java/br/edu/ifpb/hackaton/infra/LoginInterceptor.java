package br.edu.ifpb.hackaton.infra;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String uri = request.getRequestURI();

        HttpSession session = request.getSession();
        if (session.getAttribute("usuario") != null) {
            return true;
        }

        if (uri.equals("/") || uri.startsWith("/login") || uri.contains("/css") || uri.contains("/js")) {
            return true;
        }

        response.sendRedirect("/login");
        return false;
    }
}