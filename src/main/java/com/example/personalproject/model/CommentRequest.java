package com.example.personalproject.model;

import java.time.LocalDateTime;

public class CommentRequest {
    private String content;
    private String username;

    public CommentRequest() {
    }

    public CommentRequest(String content, String username) {
        this.content = content;
        this.username = username;

    }

    public String getContent() {
        return content;
    }

    public String getUsername() {
        return username;
    }

}
