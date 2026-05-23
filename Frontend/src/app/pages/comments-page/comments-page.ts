import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PostService } from '../../services/PostService';
import { CommentService, CommentResponse } from '../../services/comment-service';
import { Post } from '../../models/post';
import { Sidebar } from "../../components/sidebar/sidebar";
import { FormsModule } from '@angular/forms';
import { forkJoin, take } from 'rxjs';
import { AuthService } from '../../services/auth-service';
import { LoginPromptService } from '../../services/login-prompt-service';

@Component({
  selector: 'app-comments-page',
  standalone: true,
  imports: [Sidebar, FormsModule],
  templateUrl: './comments-page.html',
  styleUrl: './comments-page.css',
})
export class CommentsPage implements OnInit {
  post: Post | null = null;
  postId!: number;
  subredditName!: string;
  comments: CommentResponse[] = [];
  newCommentContent = '';

  constructor(
    private route: ActivatedRoute,
    private postService: PostService,
    private commentService: CommentService,
    private cdr: ChangeDetectorRef,
    private authService: AuthService,
    private loginPromptService: LoginPromptService,
    private router: Router
  ) {}

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
            this.cdr.detectChanges();
          },
          error: (err) => console.error('forkJoin failed:', err)
        });
      }
    });
  }

  loadComments() {
    this.commentService.getByPostId(this.postId).subscribe({
      next: (data) => this.comments = data,
      error: (err) => console.error('Could not fetch comments:', err)
    });
  }

  submitComment() {
    if (!this.authService.isAuthenticated()) {
      this.loginPromptService.show(); // ← use the global service
      return;
    }
    if (!this.newCommentContent.trim()) return;

    this.commentService.createComment({
      postId: this.postId,
      content: this.newCommentContent
    }).subscribe({
      next: (comment) => {
        this.comments.push(comment);
        this.cdr.detectChanges();
        this.newCommentContent = '';
      },
      error: (err) => console.error('Failed to post comment:', err)
    });
  }
}