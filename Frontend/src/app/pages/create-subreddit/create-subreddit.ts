import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { SubredditService } from '../../services/subreddit-service';
import { AuthService } from '../../services/auth-service';
import { LoginPromptService } from '../../services/login-prompt-service';
import { Sidebar } from '../../components/sidebar/sidebar';

@Component({
  selector: 'app-create-subreddit',
  standalone: true,
  imports: [FormsModule, CommonModule, Sidebar],
  templateUrl: './create-subreddit.html',
  styleUrl: './create-subreddit.css'
})
export class CreateSubreddit implements OnInit {
  name = '';
  description = '';
  rules = '';
  isPrivate = false;
  submitting = false;
  error = '';

  constructor(
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
    if (!this.name.trim()) {
      this.error = 'Community name is required.';
      return;
    }

    this.submitting = true;
    this.error = '';

    this.subredditService.createSubreddit({
      name: this.name,
      description: this.description,
      rules: this.rules,
      isPrivate: this.isPrivate
    }).subscribe({
      next: (subreddit) => {
        this.router.navigate(['/r', subreddit.name]);
      },
      error: (err) => {
        console.error('Failed to create subreddit:', err);
        this.error = 'Failed to create community. Name may already be taken.';
        this.submitting = false;
      }
    });
  }
}