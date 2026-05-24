package com.example.backend.Dto.RequestDtos;

public class CreatePostRequest {
    private String title;
    private String content;
    private Long subredditId;
    private String mediaUrl;  // ← add this

    public String getMediaUrl() { return mediaUrl; }
    public void setMediaUrl(String mediaUrl) { this.mediaUrl = mediaUrl; }

    // existing getters/setters unchanged
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Long getSubredditId() { return subredditId; }
    public void setSubredditId(Long subredditId) { this.subredditId = subredditId; }
}