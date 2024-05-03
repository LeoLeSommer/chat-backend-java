package com.leo.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.leo.chat.dto.User.response.UserSummaryResponse;
import com.leo.chat.exception.NotFoundException;
import com.leo.chat.model.User;
import com.leo.chat.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users/me")
    public UserSummaryResponse getLoggedInUser(HttpServletRequest request) {
        var user = (User) request.getAttribute("user");
        return new UserSummaryResponse(user);
    }

    @GetMapping("/users/{id}")
    public UserSummaryResponse getUserById(@PathVariable Long id) {
        var user = userService.getUserById(id);

        if (user == null) {
            throw new NotFoundException();
        }

        return new UserSummaryResponse(user.get());
    }
}
