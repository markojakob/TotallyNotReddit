package com.example.backend.service;

import com.example.backend.Dto.RequestDtos.CreateSubredditRequest;
import com.example.backend.Dto.ResponseDtos.SubredditResponse;
import com.example.backend.Mapper.SubredditMapper;
import com.example.backend.model.Subreddit;
import com.example.backend.model.User;
import com.example.backend.repository.SubredditRepository;
import com.example.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubredditService {

    private final SubredditRepository subredditRepository;
    private final UserRepository userRepository;

    public SubredditService(SubredditRepository subredditRepository, UserRepository userRepository) {
        this.subredditRepository = subredditRepository;
        this.userRepository = userRepository;
    }

    public SubredditResponse createSubreddit(CreateSubredditRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Subreddit subreddit = SubredditMapper.fromRequest(request, user);
        subredditRepository.save(subreddit);

        return SubredditMapper.toResponse(subreddit);
    }

    public List<SubredditResponse> getAllSubreddits() {
        return subredditRepository.findAll()
                .stream()
                .map(SubredditMapper::toResponse)
                .toList();
    }

    public SubredditResponse getSubredditById(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subreddit not found"));
        return SubredditMapper.toResponse(subreddit);
    }

    public SubredditResponse updateSubreddit(Long id, CreateSubredditRequest request) {
        Subreddit existing = subredditRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subreddit not found"));

        if (subredditRepository.existsByNameAndIdNot(request.getName(), id)) {
            throw new RuntimeException("A subreddit with this name already exists");
        }

        existing.setName(request.getName());
        existing.setDescription(request.getDescription());
        existing.setRules(request.getRules());
        existing.setPrivate(request.getIsPrivate());

        subredditRepository.save(existing);
        return SubredditMapper.toResponse(existing);
    }

    public void deleteSubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subreddit not found"));
        subredditRepository.delete(subreddit);
    }
}