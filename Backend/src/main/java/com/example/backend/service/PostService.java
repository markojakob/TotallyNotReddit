package com.example.backend.service;

import com.example.backend.Dto.PostDto;
import com.example.backend.model.Post;
import com.example.backend.model.Subreddit;
import com.example.backend.model.User;
import com.example.backend.repository.PostRepository;
import com.example.backend.repository.SubredditRepository;
import com.example.backend.repository.UserRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final SubredditRepository subredditRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository, SubredditRepository subredditRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.subredditRepository = subredditRepository;
    }

    @Async
    public CompletableFuture<Post> getAsync(Long id) {
        return CompletableFuture.completedFuture(
                postRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Post by id " + id + " does not exist"))
        );
    }

    @Async
    public CompletableFuture<List<Post>> listAsync() {
        return CompletableFuture.completedFuture(postRepository.findAll());
    }

    @Async
    public CompletableFuture<Post> createAsync(PostDto dto) {

        Subreddit subreddit = subredditRepository.findById(dto.getSubredditId())
                .orElseThrow(() -> new RuntimeException("Subreddit not found"));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = new Post();
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setMediaUrl(dto.getMediaUrl());
        post.setScore(0);
        post.setSubreddit(subreddit);
        post.setUser(user);

        return CompletableFuture.completedFuture(postRepository.save(post));
    }

    @Async
    public CompletableFuture<Post> updateAsync(Long id, Post post) {
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post by id " + id + " does not exist"));

        existingPost.setTitle(post.getTitle());
        existingPost.setContent(post.getContent());
        existingPost.setUser(post.getUser());
        existingPost.setSubreddit(post.getSubreddit());

        return CompletableFuture.completedFuture(postRepository.save(existingPost));
    }

    @Async
    public CompletableFuture<Post> deleteAsync(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post by id " + id + " does not exist"));
        postRepository.deleteById(id);
        return CompletableFuture.completedFuture(post);
    }
}
