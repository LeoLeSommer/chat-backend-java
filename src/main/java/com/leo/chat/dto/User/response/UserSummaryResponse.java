package com.leo.chat.dto.User.response;

import com.leo.chat.model.User;

public class UserSummaryResponse {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;

    public UserSummaryResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
