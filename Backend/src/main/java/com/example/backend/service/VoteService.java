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
    public Vote createPostVote(Long postId, int voteValue, Long userid) {
        User user = userRepository.findById(userid)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // Check if this user has already voted on this post
        Optional<Vote> existingVote = voteRepository.findByUserAndPost(user,post );

        if (existingVote.isPresent()) {
            Vote oldVote = existingVote.get();

            // Adjust post score
            int difference = voteValue - oldVote.getVoteValue();
            post.setScore(post.getScore() + difference);

            oldVote.setVoteValue(voteValue);

            voteRepository.save(oldVote);
            postRepository.save(post);

            return oldVote;
        }

        // Create new vote
        Vote vote = new Vote();
        vote.setPost(post);
        vote.setUser(user);
        vote.setVoteValue(voteValue);

        // Update post score
        post.setScore(post.getScore() + voteValue);

        postRepository.save(post);
        return voteRepository.save(vote);
    }


}
