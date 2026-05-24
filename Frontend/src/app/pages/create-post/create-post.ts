import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { PostService } from '../../services/PostService';
import { SubredditService } from '../../services/subreddit-service';
import { AuthService } from '../../services/auth-service';
import { LoginPromptService } from '../../services/login-prompt-service';
import { Sidebar } from '../../components/sidebar/sidebar';
import { Subreddit } from '../../models/subreddit';

@Component({
  selector: 'app-create-post',
  standalone: true,
  imports: [FormsModule, CommonModule, Sidebar],
  templateUrl: './create-post.html',
  styleUrl: './create-post.css'
})
export class CreatePost implements OnInit {
  title = '';
  content = '';
  selectedSubredditId: number | null = null;
  submitting = false;
  error = '';

  constructor(
    private postService: PostService,
    public subredditService: SubredditService,
    private authService: AuthService,
    private loginPromptService: LoginPromptService,
    public router: Router
  ) {}

  ngOnInit() {
    if (!this.authService.isAuthenticated()) {
      this.loginPromptService.show();
      this.router.navigate(['/']);
      return;
    }

    this.subredditService.fetchSubreddits();
  }

  submit() {
    if (!this.title.trim() || !this.content.trim() || !this.selectedSubredditId) {
      this.error = 'Please fill in all fields and select a subreddit.';
      return;
    }

    this.submitting = true;
    this.error = '';

    this.postService.createPost({
      title: this.title,
      content: this.content,
      subredditId: this.selectedSubredditId
    }).subscribe({
      next: (post) => {
        this.router.navigate(['/r', post.subredditName]);
      },
      error: (err) => {
        console.error('Failed to create post:', err);
        this.error = 'Failed to create post. Please try again.';
        this.submitting = false;
      }
    });
  }
}