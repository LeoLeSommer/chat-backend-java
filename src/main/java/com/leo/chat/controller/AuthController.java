package com.leo.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.leo.chat.dto.Auth.body.RegisterBody;
import com.leo.chat.dto.User.response.UserSummaryResponse;
import com.leo.chat.exception.UnauthorizedException;
import com.leo.chat.model.User;
import com.leo.chat.service.AuthService;
import com.leo.chat.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @GetMapping("/auth/is-registered")
    public boolean isRegistered(HttpServletRequest request) {
        var token = request.getHeader("Authorization");
        var email = authService.getUserEmail(token);

        if (email == null) {
            throw new UnauthorizedException();
        }

        return userService.getUserByEmail(email) != null;
    }

    @PostMapping("/auth/register")
    public UserSummaryResponse register(HttpServletRequest request, @RequestBody RegisterBody body) {
        var token = request.getHeader("Authorization");
        var email = authService.getUserEmail(token);

        if (email == null) {
            throw new UnauthorizedException();
        }

        if (userService.getUserByEmail(email) != null) {
            throw new UnauthorizedException("User already registered");
        }

        var user = new User(email, body.getFirstName(), body.getLastName());
        userService.saveUser(user);

        return new UserSummaryResponse(user);
    }
}
