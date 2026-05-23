import { Component, Input, OnInit, signal } from '@angular/core';
import { PostService } from '../../services/PostService';
import { Post } from '../../models/post';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { VoteRequest } from '../../models/vote-request'; 
import { VoteResult } from '../../models/vote-result';   
import { LoginPromptService } from '../../services/login-prompt-service';
import { AuthService } from '../../services/auth-service';

@Component({
  selector: 'app-post-card',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './post-card.html',
  styleUrl: './post-card.css'
})
export class PostCard implements OnInit {
  @Input({ required: true }) post!: Post;

  // Track this specific card's vote status reactively
  currentUserVote = signal<number>(0);

  constructor(private postService: PostService, private loginPromptService: LoginPromptService, private authService: AuthService) {}

  ngOnInit(): void {
    // ✅ FIXES REFRESH COLORING safely checking for undefined instead of falsy 0
    const rawPost = this.post as any;
    if (rawPost.currentUserVote !== undefined) {
      this.currentUserVote.set(rawPost.currentUserVote);
    } else if (rawPost.voteValue !== undefined) {
      this.currentUserVote.set(rawPost.voteValue);
    } else {
      this.currentUserVote.set(0);
    }
  }

  upvote(): void {
    this.handleVoteUpdate(1);
  }

  downvote(): void {
    this.handleVoteUpdate(-1);
  }

  private handleVoteUpdate(clickedValue: number): void {
      if (!this.authService.isAuthenticated()) {
    this.loginPromptService.show();
    return;
  }
    // If clicking the same arrow twice, turn it off (0)
    const targetValue = this.currentUserVote() === clickedValue ? 0 : clickedValue;

    const requestPayload: VoteRequest = {
      postId: this.post.id,
      userId: 0, 
      voteValue: targetValue as (1 | -1 | 0)
    };

    this.postService.voteOnPost(this.post.id, requestPayload).subscribe({
      next: (result: VoteResult) => {
        this.post.score = result.newPostScore;
        
        // This updates the signal to instantly flip the arrow colors
        this.currentUserVote.set(result.voteValue); 
      },
      error: (err) => {
        console.error('Voting transmission error:', err);
      }
    });
  }

  slugify(title: string): string {
    return title ? title.toLowerCase().replace(/[^a-z0-9]+/g, '-').replace(/(^-|-$)/g, '') : '';
  }
}