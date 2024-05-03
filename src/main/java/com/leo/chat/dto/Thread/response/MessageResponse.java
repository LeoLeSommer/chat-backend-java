package com.leo.chat.dto.Thread.response;

import java.util.Date;

import com.leo.chat.dto.User.response.UserSummaryResponse;
import com.leo.chat.model.Message;

public class MessageResponse {
    private Long id;
    private String content;
    private UserSummaryResponse sender;
    private Date createdAt;

    public MessageResponse() {
    }

    public MessageResponse(Message message) {
        this.id = message.getId();
        this.content = message.getContent();
        this.sender = new UserSummaryResponse(message.getSender());
        this.createdAt = message.getCreatedAt();
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public UserSummaryResponse getSender() {
        return sender;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
