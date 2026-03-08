package com.example.backend.service;

import com.example.backend.model.Post;
import com.example.backend.model.Vote;
import com.example.backend.repository.PostRepository;
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

    public VoteService(VoteRepository voteRepository, PostRepository postRepository){
        this.voteRepository = voteRepository;
        this.postRepository = postRepository;
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
    public CompletableFuture<Vote> createPostVote(Vote vote, Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Optional<Vote> existingVote =
                voteRepository.findByUserAndPost(vote.getUser(), post);

        if (existingVote.isPresent()) {

            Vote oldVote = existingVote.get();

            int difference = vote.getVoteValue() - oldVote.getVoteValue();
            post.setScore(post.getScore() + difference);

            oldVote.setVoteValue(vote.getVoteValue());

            voteRepository.save(oldVote);
            postRepository.save(post);

            return CompletableFuture.completedFuture(oldVote);
        }

        vote.setPost(post);

        post.setScore(post.getScore() + vote.getVoteValue());

        postRepository.save(post);

        Vote saved = voteRepository.save(vote);

        return CompletableFuture.completedFuture(saved);
    }


}
