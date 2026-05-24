import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { CommentService } from '../../services/comment-service';
import { AuthService } from '../../services/auth-service';
import { Sidebar } from '../../components/sidebar/sidebar';
import { SubredditService } from '../../services/subreddit-service';

@Component({
  selector: 'app-edit-comment',
  standalone: true,
  imports: [FormsModule, CommonModule, Sidebar],
  templateUrl: './edit-comment.html',
  styleUrl: './edit-comment.css'
})
export class EditComment implements OnInit {
  commentId!: number;
  content = '';
  submitting = false;
  error = '';

  constructor(
    private route: ActivatedRoute,
    public router: Router,
    private commentService: CommentService,
    private authService: AuthService,
    public subredditService: SubredditService
  ) {}

  ngOnInit() {
    if (!this.authService.isAuthenticated()) {
      this.router.navigate(['/']);
      return;
    }

    this.subredditService.fetchSubreddits();

    this.route.paramMap.subscribe(params => {
      const id = params.get('commentId');
      if (id) {
        this.commentId = +id;
      }
    });
  }

  submit() {
    if (!this.content.trim()) {
      this.error = 'Content is required.';
      return;
    }

    this.submitting = true;
    this.error = '';

    this.commentService.updateComment(this.commentId, {
      content: this.content
    }).subscribe({
      next: () => {
        this.router.navigate(['/']);
      },
      error: (err) => {
        console.error('Failed to update comment:', err);
        this.error = 'Failed to update comment. Please try again.';
        this.submitting = false;
      }
    });
  }
}