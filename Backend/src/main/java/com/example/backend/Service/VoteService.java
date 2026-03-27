package com.example.backend.Service;

import com.example.backend.Dto.RequestDtos.VoteRequest;
import com.example.backend.Dto.ResponseDtos.VoteResult;
import com.example.backend.Exception.NotFoundException;
import com.example.backend.Model.Post;
import com.example.backend.Model.User;
import com.example.backend.Model.Vote;
import com.example.backend.Repository.PostRepository;
import com.example.backend.Repository.VoteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;

    public VoteService(VoteRepository voteRepository, PostRepository postRepository) {
        this.voteRepository = voteRepository;
        this.postRepository = postRepository;
    }

    @Transactional
    public VoteResult createPostVote(VoteRequest request, User user) {

        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new NotFoundException("Post not found"));

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