package com.example.backend.Dto.ResponseDtos;

import java.time.Instant;

public class VoteResponse {
    private Long id;
    private Long postId;
    private Long userId;
    private int voteValue;
    private Instant createdAt;

    public VoteResponse(Long id, Long postId, Long userId, int voteValue, Instant createdAt) {
        this.id = id;
        this.postId = postId;
        this.userId = userId;
        this.voteValue = voteValue;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public Long getPostId() { return postId; }
    public Long getUserId() { return userId; }
    public int getVoteValue() { return voteValue; }
    public Instant getCreatedAt() { return createdAt; }
}