package com.example.backend.Dto.ResponseDtos;

import java.time.LocalDateTime;

public class PostResponse {

    private Long id;
    private String title;
    private String content;
    private Long userId;          // needed if you want to send userId
    private String username;
    private Long subredditId;     // needed if you want to send subredditId
    private String subredditName;
    private Integer score;
    private Integer currentUserVote = 0;

    private String mediaUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PostResponse(Long id, String title, String content,
                        Long userId, String username,
                        Long subredditId, String subredditName,
                        Integer score, String mediaUrl,
                        LocalDateTime createdAt, LocalDateTime updatedAt, Integer currentUserVote) {
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
        this.currentUserVote = currentUserVote;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public Integer getScore() {
        return score;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }

    public Long getSubredditId() {
        return subredditId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getSubredditName() {
        return subredditName;
    }

    public String getTitle() {
        return title;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSubredditId(Long subredditId) {
        this.subredditId = subredditId;
    }

    public void setSubredditName(String subredditName) {
        this.subredditName = subredditName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getCurrentUserVote() {
        return currentUserVote;
    }

    public void setCurrentUserVote(Integer currentUserVote) {
        this.currentUserVote = currentUserVote;
    }
}