package com.example.backend.Dto.RequestDtos;

public class PostVoteRequest {
    private Long postId;
    private Long userId;
    private int voteValue; // 1 = upvote, -1 = downvote

    public Long getPostId() { return postId; }
    public void setPostId(Long postId) { this.postId = postId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public int getVoteValue() { return voteValue; }
    public void setVoteValue(int voteValue) { this.voteValue = voteValue; }
}