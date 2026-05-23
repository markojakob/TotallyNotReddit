package com.example.backend.Mapper;

import com.example.backend.Dto.ResponseDtos.VoteResponse;
import com.example.backend.Model.PostVote;

public class VoteMapper {
    public static VoteResponse toResponse(PostVote postVote) {
        return new VoteResponse(
                postVote.getId(),
                postVote.getPost() != null ? postVote.getPost().getId() : null,
                postVote.getUser() != null ? postVote.getUser().getId() : null,
                postVote.getVoteValue(),
                postVote.getCreatedAt()
        );
    }
}