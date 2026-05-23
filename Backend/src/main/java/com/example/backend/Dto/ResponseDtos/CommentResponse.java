package com.example.backend.Dto.ResponseDtos;

import java.time.LocalDateTime;

public class CommentResponse {
    private Long id;
    private String content;
    private String username;
    private Long postId;
    private LocalDateTime createdAt;
    private Integer score;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public Long getPostId() { return postId; }
    public void setPostId(Long postId) { this.postId = postId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }
}