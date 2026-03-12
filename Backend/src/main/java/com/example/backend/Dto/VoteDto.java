package com.example.backend.Dto;

import java.time.Instant;

public class VoteDto {
    private Long id;
    private Long postId;   // now included in response
    private Long userId;
    private int voteValue;
    private Instant createdAt;

    public VoteDto() {}

    public VoteDto(Long id, Long postId, Long userId, int voteValue, Instant createdAt) {
        this.id = id;
        this.postId = postId;
        this.userId = userId;
        this.voteValue = voteValue;
        this.createdAt = createdAt;
    }

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPostId() { return postId; }
    public void setPostId(Long postId) { this.postId = postId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public int getVoteValue() { return voteValue; }
    public void setVoteValue(int voteValue) { this.voteValue = voteValue; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}