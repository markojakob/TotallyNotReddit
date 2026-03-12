package com.example.backend.service;

import com.example.backend.model.Subreddit;
import com.example.backend.repository.SubredditRepository;
import com.example.backend.repository.UserRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class SubredditService {

    private final SubredditRepository subredditRepository;
    private final UserRepository userRepository;

    public SubredditService(SubredditRepository subredditRepository, UserRepository userRepository) {
        this.subredditRepository = subredditRepository;
        this.userRepository = userRepository;
    }

    @Async
    public CompletableFuture<Subreddit> getAsync(Long id) {
        return CompletableFuture.completedFuture(
                subredditRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Subreddit by id " + id + " does not exist"))
        );
    }

    @Async
    public CompletableFuture<List<Subreddit>> listAsync() {
        return CompletableFuture.completedFuture(subredditRepository.findAll());
    }

    @Async
    public CompletableFuture<Subreddit> createAsync(Subreddit subreddit) {
        Subreddit savedSubreddit = subredditRepository.save(subreddit);
        return CompletableFuture.completedFuture(savedSubreddit);
    }

    @Async
    public CompletableFuture<Subreddit> updateAsync(Long id, Subreddit subreddit) {
        Subreddit existingSubreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subreddit by id " + id + " does not exist"));

        if (subredditRepository.existsByNameAndIdNot(existingSubreddit.getName(), existingSubreddit.getId())) {
            throw new RuntimeException("A subreddit with this name already exists");
        }

        existingSubreddit.setName(subreddit.getName());
        existingSubreddit.setDescription(subreddit.getDescription());

        return CompletableFuture.completedFuture(subredditRepository.save(existingSubreddit));
    }

    @Async
    public CompletableFuture<Subreddit> deleteAsync(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subreddit by id " + id + " does not exist"));
        subredditRepository.deleteById(id);
        return CompletableFuture.completedFuture(subreddit);
    }
}
