package com.example.backend.Dto;

import java.time.LocalDateTime;

public class PostDto {

    private Long id;
    private String title;
    private String content;

    private Long userId;
    private String username;

    private Long subredditId;
    private String subredditName;

    private Integer score;
    private String mediaUrl;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PostDto(Long id, String title, String content,
                   Long userId, String username,
                   Long subredditId, String subredditName,
                   Integer score, String mediaUrl,
                   LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.username = username;
        this.subredditId = subredditId;
        this.subredditName = subredditName;
        this.score = score;
        this.mediaUrl = mediaUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public void setSubredditId(Long subredditId) {
        this.subredditId = subredditId;
    }


    public void setSubredditName(String subredditName) {
        this.subredditName = subredditName;
    }


    public void setScore(Integer score) {
        this.score = score;
    }


    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public Long getSubredditId() {
        return subredditId;
    }

    public String getSubredditName() {
        return subredditName;
    }

    public Integer getScore() {
        return score;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }
}