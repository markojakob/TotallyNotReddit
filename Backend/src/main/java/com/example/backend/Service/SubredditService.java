package com.example.backend.Service;

import com.example.backend.Dto.RequestDtos.CreateSubredditRequest;
import com.example.backend.Dto.ResponseDtos.SubredditResponse;
import com.example.backend.Exception.BadRequestException;
import com.example.backend.Exception.NotFoundException;
import com.example.backend.Mapper.SubredditMapper;
import com.example.backend.Model.Subreddit;
import com.example.backend.Model.User;
import com.example.backend.Repository.SubredditRepository;
import com.example.backend.Repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubredditService {

    private final SubredditRepository subredditRepository;

    public SubredditService(SubredditRepository subredditRepository) {
        this.subredditRepository = subredditRepository;
    }

    public SubredditResponse createSubreddit(CreateSubredditRequest request, User user) {
        Subreddit subreddit = SubredditMapper.fromRequest(request, user);
        subredditRepository.save(subreddit);

        return SubredditMapper.toResponse(subreddit);
    }

    @Transactional(readOnly = true)
    public List<SubredditResponse> getAllSubreddits() {
        return subredditRepository.findAll()
                .stream()
                .map(SubredditMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public SubredditResponse getByName(String name) {
        Subreddit subreddit = subredditRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Subreddit not found"));
        return SubredditMapper.toResponse(subreddit);
    }

    @Transactional(readOnly = true)
    public SubredditResponse getSubredditById(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Subreddit not found"));
        return SubredditMapper.toResponse(subreddit);
    }

    public SubredditResponse updateSubreddit(Long id, CreateSubredditRequest request) {
        Subreddit existing = subredditRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Subreddit not found"));

        if (subredditRepository.existsByNameAndIdNot(request.getName(), id)) {
            throw new BadRequestException("A subreddit with this name already exists");
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
                .orElseThrow(() -> new NotFoundException("Subreddit not found"));
        subredditRepository.delete(subreddit);
    }
}