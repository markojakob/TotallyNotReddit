import { Component, OnInit, ChangeDetectorRef, signal } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PostService } from '../../services/PostService';
import { CommentService, CommentResponse } from '../../services/comment-service';
import { Post } from '../../models/post';
import { Sidebar } from "../../components/sidebar/sidebar";
import { CommonModule } from '@angular/common';
import { forkJoin, take } from 'rxjs';
import { AuthService } from '../../services/auth-service';
import { LoginPromptService } from '../../services/login-prompt-service';
import { VoteService } from '../../services/vote-service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-comments-page',
  standalone: true,
  imports: [Sidebar, CommonModule, FormsModule],
  templateUrl: './comments-page.html',
  styleUrl: './comments-page.css',
})
export class CommentsPage implements OnInit {
  post: Post | null = null;
  postId!: number;
  subredditName!: string;
  comments: CommentResponse[] = [];
  newCommentContent = '';
  currentUserVote = signal<number>(0);
  currentUsername = localStorage.getItem('username') ?? '';
  commentToDelete: CommentResponse | null = null;
  commentError = '';

  constructor(
    private route: ActivatedRoute,
    private postService: PostService,
    private commentService: CommentService,
    private cdr: ChangeDetectorRef,
    private authService: AuthService,
    private loginPromptService: LoginPromptService,
    private voteService: VoteService,
    private router: Router
  ) { }

  ngOnInit() {
    this.route.paramMap.pipe(take(1)).subscribe(params => {
      this.subredditName = params.get('subredditName') || '';
      const idParam = params.get('postId');

      if (idParam) {
        this.postId = +idParam;

        forkJoin({
          post: this.postService.getById(this.postId),
          comments: this.commentService.getByPostId(this.postId)
        }).subscribe({
          next: ({ post, comments }) => {
            this.post = post;
            this.comments = comments;
            this.currentUserVote.set((post as any).currentUserVote ?? 0); // ← add this
            this.cdr.detectChanges();
          },
          error: (err) => console.error('forkJoin failed:', err)
        });
      }
    });
  }

  vote(clickedValue: number) {
    if (!this.post) return;
    const result$ = this.voteService.handleVote(this.post.id, this.currentUserVote(), clickedValue);
    if (!result$) return;

    result$.subscribe({
      next: (result) => {
        this.post!.score = result.newPostScore;
        this.currentUserVote.set(result.voteValue);
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Vote error:', err)
    });
  }

submitComment() {
  if (!this.authService.isAuthenticated()) {
    this.loginPromptService.show();
    return;
  }
  if (!this.newCommentContent.trim()) {
    this.commentError = 'Comment cannot be empty.';
    return;
  }

  this.commentError = '';
  this.commentService.createComment({
    postId: this.postId,
    content: this.newCommentContent
  }).subscribe({
    next: (comment) => {
      this.comments.push(comment);
      this.newCommentContent = '';
      this.commentError = '';
      this.cdr.detectChanges();
    },
    error: (err) => {
      this.commentError = 'Failed to post comment. Please try again.';
      console.error('Failed to post comment:', err);
    }
  });
}

  startEditComment(comment: CommentResponse) {
  this.router.navigate(['/comments', comment.id, 'edit']);
}

confirmDeleteComment(comment: CommentResponse) {
  this.commentToDelete = comment;
}

cancelDeleteComment() {
  this.commentToDelete = null;
}

deleteComment() {
  if (!this.commentToDelete) return;
  this.commentService.deleteComment(this.commentToDelete.id).subscribe({
    next: () => {
      this.comments = this.comments.filter(c => c.id !== this.commentToDelete!.id);
      this.commentToDelete = null;
      this.cdr.detectChanges();
    },
    error: (err) => console.error('Delete comment failed:', err)
  });
}
}