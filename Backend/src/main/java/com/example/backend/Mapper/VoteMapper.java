package com.example.backend.Mapper;

import com.example.backend.Dto.ResponseDtos.VoteResponse;
import com.example.backend.Model.Vote;

public class VoteMapper {
    public static VoteResponse toResponse(Vote vote) {
        return new VoteResponse(
                vote.getId(),
                vote.getPost() != null ? vote.getPost().getId() : null,
                vote.getUser() != null ? vote.getUser().getId() : null,
                vote.getVoteValue(),
                vote.getCreatedAt()
        );
    }
}