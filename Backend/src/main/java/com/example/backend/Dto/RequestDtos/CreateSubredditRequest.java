package com.example.backend.Dto.RequestDtos;

public class CreateSubredditRequest {
    private String name;
    private String description;
    private Boolean isPrivate;
    private String rules;
    public CreateSubredditRequest() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Boolean getIsPrivate() { return isPrivate; }
    public void setIsPrivate(Boolean isPrivate) { this.isPrivate = isPrivate; }

    public String getRules() {
        return rules;
    }

    public Boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }
}