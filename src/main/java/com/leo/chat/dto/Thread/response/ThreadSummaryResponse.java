package com.leo.chat.dto.Thread.response;

import com.leo.chat.model.Thread;

import java.util.Date;

public class ThreadSummaryResponse {
    private Long id;
    private String title;
    private Date createdAt;
    private Date updatedAt;

    public ThreadSummaryResponse(Thread thread) {
        this.id = thread.getId();
        this.title = thread.getTitle();
        this.createdAt = thread.getCreatedAt();
        this.updatedAt = thread.getUpdatedAt();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }
}
