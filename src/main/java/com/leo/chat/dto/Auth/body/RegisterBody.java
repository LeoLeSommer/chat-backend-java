package com.leo.chat.dto.Auth.body;

public class RegisterBody {
    private String firstName;
    private String lastName;

    public RegisterBody() {
    }

    public RegisterBody(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
