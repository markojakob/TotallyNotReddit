import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { PostService } from '../../services/PostService';
import { AuthService } from '../../services/auth-service';
import { Sidebar } from '../../components/sidebar/sidebar';
import { SubredditService } from '../../services/subreddit-service';
import { PostCard } from '../../components/post-card/post-card';
import { Post } from '../../models/post';

@Component({
  selector: 'app-profile-page',
  standalone: true,
  imports: [CommonModule, Sidebar, PostCard, RouterLink],
  templateUrl: './profile-page.html',
  styleUrl: './profile-page.css'
})
export class ProfilePage implements OnInit {
  username = '';
  posts: Post[] = [];
  loading = true;

  constructor(
    private postService: PostService,
    private authService: AuthService,
    public subredditService: SubredditService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    if (!this.authService.isAuthenticated()) {
      this.router.navigate(['/login']);
      return;
    }

    this.username = this.authService.getUsername() ?? '';
    this.subredditService.fetchSubreddits();

    this.postService.listPosts().subscribe({
      next: (posts) => {
        this.posts = posts.filter(p => p.username === this.username);
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Failed to load posts:', err);
        this.loading = false;
      }
    });
  }
}