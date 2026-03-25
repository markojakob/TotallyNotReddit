package com.example.backend.service;

import com.example.backend.Dto.RequestDtos.VoteRequest;
import com.example.backend.Dto.ResponseDtos.VoteResponse;
import com.example.backend.Dto.ResponseDtos.VoteResult;
import com.example.backend.Mapper.VoteMapper;
import com.example.backend.model.Post;
import com.example.backend.model.User;
import com.example.backend.model.Vote;
import com.example.backend.repository.PostRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.repository.VoteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public VoteService(VoteRepository voteRepository, PostRepository postRepository, UserRepository userRepository) {
        this.voteRepository = voteRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public VoteResult createPostVote(VoteRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Optional<Vote> existingVote = voteRepository.findByUserAndPost(user, post);

        int voteValue = request.getVoteValue();

        if (existingVote.isPresent()) {
            Vote oldVote = existingVote.get();
            int difference = voteValue - oldVote.getVoteValue();
            post.setScore(post.getScore() + difference);
            oldVote.setVoteValue(voteValue);
            voteRepository.save(oldVote);
            postRepository.save(post);
        } else {
            Vote vote = new Vote();
            vote.setPost(post);
            vote.setUser(user);
            vote.setVoteValue(voteValue);
            voteRepository.save(vote);
            post.setScore(post.getScore() + voteValue);
            postRepository.save(post);
        }

        return new VoteResult(post.getScore(), voteValue);
    }
}