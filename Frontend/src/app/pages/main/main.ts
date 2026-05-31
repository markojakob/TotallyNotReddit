import { Component, OnInit, signal } from '@angular/core';
import { PostService } from '../../services/PostService';
import { SubredditService } from '../../services/subreddit-service';
import { AuthService } from '../../services/auth-service';
import { Post } from '../../models/post';
import { CommonModule } from '@angular/common';
import { PostCard } from '../../components/post-card/post-card';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-main',
  templateUrl: './main.html',
  imports: [CommonModule, PostCard, RouterLink],
  styleUrls: ['./main.css'],
})
export class Main implements OnInit {
  posts = signal<Post[]>([]);
  loading = signal(false);
  activeTab: 'popular' | 'foryou' = 'popular';

  constructor(
    private postService: PostService,
    private subredditService: SubredditService,
    public authService: AuthService
  ) {}

  ngOnInit(): void {
    this.fetchPosts();
  }

  setTab(tab: 'popular' | 'foryou') {
    this.activeTab = tab;
    this.fetchPosts();
  }

  fetchPosts() {
    this.loading.set(true);

    const request$ = this.activeTab === 'foryou' && this.authService.isAuthenticated()
      ? this.postService.getForYouFeed()
      : this.postService.listPosts();

    request$.subscribe({
      next: (data) => {
        this.posts.set(data);
        this.loading.set(false);
      },
      error: (err) => {
        console.error('Failed to fetch posts:', err);
        this.loading.set(false);
      }
    });
  }
}