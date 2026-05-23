package com.example.backend.Dto.RequestDtos;

import jakarta.validation.constraints.NotNull;

public class CreateCommentRequest {
    @NotNull
    private Long postId;

    @NotNull
    private String content;

    private Long parentCommentId; // Optional parameter for nested chains later

    public Long getPostId() { return postId; }
    public void setPostId(Long postId) { this.postId = postId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Long getParentCommentId() { return parentCommentId; }
    public void setParentCommentId(Long parentCommentId) { this.parentCommentId = parentCommentId; }
}