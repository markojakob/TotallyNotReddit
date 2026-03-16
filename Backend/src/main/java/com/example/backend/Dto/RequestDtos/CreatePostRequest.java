package com.example.backend.Dto.RequestDtos;

public class CreatePostRequest {
    private String title;
    private String content;
    private Long subredditId; // <--- important!

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Long getSubredditId() { return subredditId; }
    public void setSubredditId(Long subredditId) { this.subredditId = subredditId; }
}