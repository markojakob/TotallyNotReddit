package com.example.backend.service;

import com.example.backend.model.Post;
import com.example.backend.model.User;
import com.example.backend.model.Vote;
import com.example.backend.repository.PostRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.repository.VoteRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class VoteService {
    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public VoteService(VoteRepository voteRepository, PostRepository postRepository, UserRepository userRepository){
        this.voteRepository = voteRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Async
    public CompletableFuture<Vote> getAsync(long id) {
           var vote = voteRepository.findById(id).orElseThrow(() -> new RuntimeException("Vote by the id of " + id + " does not exist"));
            return CompletableFuture.completedFuture(vote);
    }

    @Async
    public CompletableFuture<List<Vote>> listAsync(){
        var votes = voteRepository.findAll();
        return CompletableFuture.completedFuture(votes);
    }

    @Transactional
    public Vote createPostVote(Long postId, Long userId, int voteValue) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Optional<Vote> existingVote = voteRepository.findByUserAndPost(user, post);

        if (existingVote.isPresent()) {

            Vote oldVote = existingVote.get();

            int difference = voteValue - oldVote.getVoteValue();
            post.setScore(post.getScore() + difference);

            oldVote.setVoteValue(voteValue);

            voteRepository.save(oldVote);
            postRepository.save(post);

            return oldVote;
        }

        Vote vote = new Vote();
        vote.setUser(user);
        vote.setPost(post);
        vote.setVoteValue(voteValue);

        post.setScore(post.getScore() + voteValue);

        postRepository.save(post);

        return voteRepository.save(vote);
    }


}
