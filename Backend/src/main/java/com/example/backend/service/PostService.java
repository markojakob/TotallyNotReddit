package com.example.backend.service;

import com.example.backend.model.Post;
import com.example.backend.repository.PostRepository;
import com.example.backend.repository.UserRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
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
    public CompletableFuture<Post> createAsync(Post post, Long userId) {
        var user = userRepository.findById(userId);

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
