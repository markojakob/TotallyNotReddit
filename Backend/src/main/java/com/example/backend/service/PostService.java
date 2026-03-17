package com.example.backend.service;

import com.example.backend.Dto.RequestDtos.CreatePostRequest;
import com.example.backend.Dto.ResponseDtos.PostResponse;
import com.example.backend.Mapper.PostMapper;
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
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final SubredditRepository subredditRepository;

    public PostService(PostRepository postRepository,
                       UserRepository userRepository,
                       SubredditRepository subredditRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.subredditRepository = subredditRepository;
    }

    public PostResponse createPost(CreatePostRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Subreddit subreddit = subredditRepository.findById(request.getSubredditId())
                .orElseThrow(() -> new RuntimeException("Subreddit not found"));

        Post post = PostMapper.fromRequest(request, user, subreddit);
        postRepository.save(post);

        return PostMapper.toResponse(post);
    }

    public List<PostResponse> getPostsBySubreddit(String name) {

        Subreddit subreddit = subredditRepository
                .findByName(name)
                .orElseThrow(() -> new RuntimeException("Subreddit not found"));

        return postRepository.findAllBySubreddit(subreddit)
                .stream()
                .map(PostMapper::toResponse)
                .toList();
    }

    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(PostMapper::toResponse)
                .toList();
    }

    public PostResponse getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return PostMapper.toResponse(post);
    }
}
