package com.example.backend.Mapper;

import com.example.backend.Dto.RequestDtos.CreateSubredditRequest;
import com.example.backend.Dto.ResponseDtos.SubredditResponse;
import com.example.backend.Model.Subreddit;
import com.example.backend.Model.User;

public class SubredditMapper {

    public static Subreddit fromRequest(CreateSubredditRequest request, User user) {
        Subreddit subreddit = new Subreddit();
        subreddit.setName(request.getName());
        subreddit.setDescription(request.getDescription());
        subreddit.setRules(request.getRules());
        subreddit.setPrivate(request.getIsPrivate() != null ? request.getIsPrivate() : false);
        subreddit.setCreatedBy(user);
        return subreddit;
    }

    public static SubredditResponse toResponse(Subreddit subreddit, int membersCount, boolean isJoined) {
        return new SubredditResponse(
                subreddit.getId(),
                subreddit.getName(),
                subreddit.getDescription(),
                subreddit.getRules(),
                subreddit.getPrivate(),
                subreddit.getCreatedBy() != null ? subreddit.getCreatedBy().getId() : null,
                subreddit.getCreatedBy() != null ? subreddit.getCreatedBy().getUsername() : null,
                subreddit.getCreatedAt(),
                membersCount,
                isJoined
        );
    }

    // Keep old one for backwards compat, defaults isJoined to false
    public static SubredditResponse toResponse(Subreddit subreddit) {
        return toResponse(subreddit, subreddit.getPosts().size(), false);
    }
}