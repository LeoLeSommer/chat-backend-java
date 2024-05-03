package com.leo.chat.dto.Thread.body;

public class CreateThreadBody {
    private String title;

    public CreateThreadBody() {
    }

    public CreateThreadBody(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
