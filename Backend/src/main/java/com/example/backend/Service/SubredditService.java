package com.example.backend.Service;

import com.example.backend.Dto.RequestDtos.CreateSubredditRequest;
import com.example.backend.Dto.ResponseDtos.SubredditResponse;
import com.example.backend.Exception.BadRequestException;
import com.example.backend.Exception.NotFoundException;
import com.example.backend.Mapper.SubredditMapper;
import com.example.backend.Model.Subreddit;
import com.example.backend.Model.SubredditMember;
import com.example.backend.Model.User;
import com.example.backend.Repository.SubredditMemberRepository;
import com.example.backend.Repository.SubredditRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubredditService {

    private final SubredditRepository subredditRepository;
    private final SubredditMemberRepository memberRepository;
    private final AuthService authService;

    public SubredditService(SubredditRepository subredditRepository,
                            SubredditMemberRepository memberRepository,
                            AuthService authService) {
        this.subredditRepository = subredditRepository;
        this.memberRepository = memberRepository;
        this.authService = authService;
    }

    private boolean isCurrentUserJoined(Subreddit subreddit) {
        try {
            User user = authService.getCurrentUser();
            boolean exists = memberRepository.existsByUserAndSubreddit(user, subreddit);
            return exists;
        } catch (Exception e) {
            return false;
        }
    }

    private SubredditResponse toResponse(Subreddit subreddit) {
        int count = memberRepository.countBySubreddit(subreddit);
        boolean joined = isCurrentUserJoined(subreddit);
        return SubredditMapper.toResponse(subreddit, count, joined);
    }

    @Transactional
    public SubredditResponse createSubreddit(CreateSubredditRequest request, User user) {
        Subreddit subreddit = SubredditMapper.fromRequest(request, user);
        subredditRepository.save(subreddit);
        return toResponse(subreddit);
    }

    @Transactional(readOnly = true)
    public List<SubredditResponse> getAllSubreddits() {
        return subredditRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public SubredditResponse getByName(String name) {
        Subreddit subreddit = subredditRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Subreddit not found"));
        return toResponse(subreddit);
    }

    @Transactional(readOnly = true)
    public SubredditResponse getSubredditById(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Subreddit not found"));
        return toResponse(subreddit);
    }

    @Transactional
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
        return toResponse(existing);
    }

    @Transactional
    public void deleteSubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Subreddit not found"));
        subredditRepository.delete(subreddit);
    }

    @Transactional(readOnly = true)
    public List<SubredditResponse> searchSubreddits(String query) {
        return subredditRepository.searchByName(query)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public void joinSubreddit(Long subredditId, User user) {
        Subreddit subreddit = subredditRepository.findById(subredditId)
                .orElseThrow(() -> new NotFoundException("Subreddit not found"));
        if (!memberRepository.existsByUserAndSubreddit(user, subreddit)) {
            SubredditMember member = new SubredditMember();
            member.setUser(user);
            member.setSubreddit(subreddit);
            memberRepository.save(member);
        }
    }

    @Transactional
    public void leaveSubreddit(Long subredditId, User user) {
        Subreddit subreddit = subredditRepository.findById(subredditId)
                .orElseThrow(() -> new NotFoundException("Subreddit not found"));
        memberRepository.findByUserAndSubreddit(user, subreddit)
                .ifPresent(memberRepository::delete);
    }

    @Transactional(readOnly = true)
    public List<SubredditResponse> getJoinedSubreddits(User user) {
        return memberRepository.findSubredditsByUser(user)
                .stream()
                .map(this::toResponse)
                .toList();
    }
}