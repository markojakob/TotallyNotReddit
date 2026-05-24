package com.example.backend.Dto.RequestDtos;

public class CommentVoteRequest {
    private Long commentId;
    private int voteValue;

    public Long getCommentId() { return commentId; }
    public int getVoteValue() { return voteValue; }
}