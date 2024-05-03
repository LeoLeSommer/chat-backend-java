package com.leo.chat;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.leo.chat.service.AuthService;
import com.leo.chat.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Bypass the filter for specific endpoints
        if (request.getRequestURI().contains("/socket.io") ||
                request.getRequestURI().equals("/auth/is-registered") ||
                request.getRequestURI().equals("/auth/register")) {
            filterChain.doFilter(request, response);
            return;
        }

        var token = request.getHeader("Authorization");
        var userEmail = authService.getUserEmail(token);

        if (userEmail == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        var user = userService.getUserByEmail(userEmail);

        if (user == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        request.setAttribute("user", user);
        filterChain.doFilter(request, response);
    }
}
