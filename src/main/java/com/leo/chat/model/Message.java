package com.leo.chat.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @Column(name = "sender_id")
    private Long senderId;

    @Column(name = "thread_id")
    private Long threadId;

    @Column(name = "created_at")
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "sender_id", insertable = false, updatable = false)
    private User sender;

    public Message() {
    }

    public Message(String content, Long senderId, Long threadId) {
        this.content = content;
        this.senderId = senderId;
        this.threadId = threadId;
        this.createdAt = new Date();
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Long getSenderId() {
        return senderId;
    }

    public Long getThreadId() {
        return threadId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User user) {
        this.sender = user;
    }
}
