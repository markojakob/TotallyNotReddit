package com.example.backend.Service;

import com.example.backend.Dto.RequestDtos.CreatePostRequest;
import com.example.backend.Dto.ResponseDtos.PostResponse;
import com.example.backend.Exception.NotFoundException;
import com.example.backend.Mapper.PostMapper;
import com.example.backend.Model.Post;
import com.example.backend.Model.Subreddit;
import com.example.backend.Model.User;
import com.example.backend.Repository.PostRepository;
import com.example.backend.Repository.SubredditRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;

    public PostService(PostRepository postRepository,
                       SubredditRepository subredditRepository) {
        this.postRepository = postRepository;
        this.subredditRepository = subredditRepository;
    }

    public PostResponse createPost(CreatePostRequest request, User author) {
        Subreddit subreddit = subredditRepository.findById(request.getSubredditId())
                .orElseThrow(() -> new RuntimeException("Subreddit not found"));

        Post post = PostMapper.fromRequest(request, author, subreddit);
        postRepository.save(post);

        return PostMapper.toResponse(post);
    }

    public List<PostResponse> getPostsBySubreddit(String name) {

        Subreddit subreddit = subredditRepository
                .findByName(name)
                .orElseThrow(() -> new NotFoundException("Subreddit not found"));

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
                .orElseThrow(() -> new NotFoundException("Post not found"));
        return PostMapper.toResponse(post);
    }
}
