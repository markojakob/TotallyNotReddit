import { Component, effect, Input, OnInit, signal } from '@angular/core';
import { VoteService } from '../../services/vote-service';
import { PostService } from '../../services/PostService';
import { AuthService } from '../../services/auth-service';
import { Router } from '@angular/router';
import { Post } from '../../models/post';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { VoteResult } from '../../models/vote-result';
import { timeAgo } from '../../utils/time.utils';

@Component({
  selector: 'app-post-card',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './post-card.html',
  styleUrl: './post-card.css'
})
export class PostCard implements OnInit {
  @Input({ required: true }) post!: Post;
  currentUserVote = signal<number>(0);
  showDeleteConfirm = false;
  timeAgo = timeAgo;
  
  constructor(
    private voteService: VoteService,
    private postService: PostService,
    public authService: AuthService,
    private router: Router
  ) { 
    effect(() => {
      const isAuth = this.authService.isAuthenticated();

      if (!isAuth){
        this.currentUserVote.set(0);
      }
    })
  }

  ngOnInit(): void {
    const rawPost = this.post as any;
    if (rawPost.currentUserVote !== undefined) {
      this.currentUserVote.set(rawPost.currentUserVote);
    } else if (rawPost.voteValue !== undefined) {
      this.currentUserVote.set(rawPost.voteValue);
    } else {
      this.currentUserVote.set(0);
    }
  }

  get isAuthor(): boolean {
    const username = this.authService.getUsername() ?? '';
    return !!username && username === this.post.username;
  }

  upvote(): void { this.handleVoteUpdate(1); }
  downvote(): void { this.handleVoteUpdate(-1); }

  private handleVoteUpdate(clickedValue: number): void {
    const result$ = this.voteService.handlePostVote(this.post.id, this.currentUserVote(), clickedValue);
    if (!result$) return;

    result$.subscribe({
      next: (result: VoteResult) => {
        this.post.score = result.newPostScore;
        this.currentUserVote.set(result.voteValue);
      },
      error: (err) => console.error('Vote error:', err)
    });
  }

  startEdit(): void {
    this.router.navigate(['/posts', this.post.id, 'edit']);
  }

  confirmDelete(): void {
    (document.activeElement as HTMLElement)?.blur();
    this.showDeleteConfirm = true;
  }

  cancelDelete(): void { this.showDeleteConfirm = false; }

  deletePost(): void {
    this.postService.deletePost(this.post.id).subscribe({
      next: () => {
        this.showDeleteConfirm = false;
        window.location.reload();
      },
      error: (err) => console.error('Delete failed:', err)
    });
  }

  slugify(title: string): string {
    return title ? title.toLowerCase().replace(/[^a-z0-9]+/g, '-').replace(/(^-|-$)/g, '') : '';
  }

}