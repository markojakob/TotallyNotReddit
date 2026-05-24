package com.example.backend.Model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(
        name = "comment_votes",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "comment_id"})
)
public class CommentVote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    @Column(name = "vote_value", nullable = false)
    private int voteValue;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;


    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Comment getComment() { return comment; }
    public void setComment(Comment comment) { this.comment = comment; }

    public int getVoteValue() { return voteValue; }
    public void setVoteValue(int voteValue) { this.voteValue = voteValue; }

    public Long getId() { return id; }
}