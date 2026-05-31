import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { PostService } from '../../services/PostService';
import { AuthService } from '../../services/auth-service';
import { Sidebar } from '../../components/sidebar/sidebar';
import { SubredditService } from '../../services/subreddit-service';

@Component({
  selector: 'app-edit-post',
  standalone: true,
  imports: [FormsModule, CommonModule, Sidebar],
  templateUrl: './edit-post.html',
  styleUrl: './edit-post.css'
})
export class EditPost implements OnInit {
  postId!: number;
  title = '';
  content = '';
  submitting = false;
  error = '';
  mediaUrl = '';

  constructor(
    private route: ActivatedRoute,
    public router: Router,
    private postService: PostService,
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
      const id = params.get('postId');
      if (id) {
        this.postId = +id;
        this.postService.getById(this.postId).subscribe({
          next: (post) => {
            const username = this.authService.getUsername();
            if (post.username !== username) {
              this.router.navigate(['/']);
              return;
            }
            this.title = post.title;
            this.content = post.content;
            this.mediaUrl = post.mediaUrl ?? '';
          },
          error: () => this.router.navigate(['/'])
        });
      }
    });
  }

  submit() {
    if (!this.title.trim() || !this.content.trim()) {
      this.error = 'Title and content are required.';
      return;
    }

    this.submitting = true;
    this.error = '';

    this.postService.updatePost(this.postId, {
      title: this.title,
      content: this.content,
      mediaUrl: this.mediaUrl || null 
    }).subscribe({
      next: (post) => {
        this.router.navigate(['/r', post.subredditName]);
      },
      error: (err) => {
        console.error('Failed to update post:', err);
        this.error = 'Failed to update post. Please try again.';
        this.submitting = false;
      }
    });
  }
}