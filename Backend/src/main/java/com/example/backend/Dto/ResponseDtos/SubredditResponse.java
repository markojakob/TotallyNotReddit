package com.example.backend.Dto.ResponseDtos;

import java.time.LocalDateTime;

public class SubredditResponse {
    private Long id;
    private String name;
    private String description;
    private String rules;
    private Boolean isPrivate;
    private Long createdById;
    private String createdByUsername;
    private LocalDateTime createdAt;
    private int membersCount;
    private boolean isJoined;



    public SubredditResponse(Long id, String name, String description,
                             String rules, Boolean isPrivate,
                             Long createdById, String createdByUsername,
                             LocalDateTime createdAt, int membersCount, Boolean isJoined) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.rules = rules;
        this.isPrivate = isPrivate;
        this.createdById = createdById;
        this.createdByUsername = createdByUsername;
        this.createdAt = createdAt;
        this.membersCount = membersCount;
        this.isJoined = isJoined;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getRules() { return rules; }
    public void setRules(String rules) { this.rules = rules; }
    public Boolean getIsPrivate() { return isPrivate; }
    public void setIsPrivate(Boolean isPrivate) { this.isPrivate = isPrivate; }
    public Long getCreatedById() { return createdById; }
    public void setCreatedById(Long createdById) { this.createdById = createdById; }
    public String getCreatedByUsername() { return createdByUsername; }
    public void setCreatedByUsername(String createdByUsername) { this.createdByUsername = createdByUsername; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public int getMembersCount() { return membersCount; }
    public void setMembersCount(int membersCount) { this.membersCount = membersCount; }
    public boolean getIsJoined() { return isJoined; }
    public void setIsJoined(boolean isJoined) { this.isJoined = isJoined; }
}