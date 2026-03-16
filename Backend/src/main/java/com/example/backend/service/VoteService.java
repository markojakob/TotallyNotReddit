package com.example.backend.service;

import com.example.backend.Dto.RequestDtos.VoteRequest;
import com.example.backend.Dto.ResponseDtos.VoteResponse;
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
    public VoteResponse createPostVote(VoteRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // Check if this user has already voted on this post
        Optional<Vote> existingVote = voteRepository.findByUserAndPost(user, post);

        if (existingVote.isPresent()) {
            Vote oldVote = existingVote.get();

            // Adjust post score
            int difference = request.getVoteValue() - oldVote.getVoteValue();
            post.setScore(post.getScore() + difference);

            oldVote.setVoteValue(request.getVoteValue());

            voteRepository.save(oldVote);
            postRepository.save(post);

            return VoteMapper.toResponse(oldVote);
        }

        // Create new vote
        Vote vote = new Vote();
        vote.setPost(post);
        vote.setUser(user);
        vote.setVoteValue(request.getVoteValue());

        // Update post score
        post.setScore(post.getScore() + request.getVoteValue());

        postRepository.save(post);
        return VoteMapper.toResponse(voteRepository.save(vote));
    }
}