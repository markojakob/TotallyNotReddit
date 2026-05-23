import { Component, OnInit, signal } from '@angular/core';
import { PostService } from '../../services/PostService';
import { SubredditService } from '../../services/subreddit-service';
import { Post } from '../../models/post';
import { CommonModule } from '@angular/common';
import { Sidebar } from '../../components/sidebar/sidebar';
import { PostCard } from '../../components/post-card/post-card';
import { LoginPromptService } from '../../services/login-prompt-service';

@Component({
  selector: 'app-main',
  templateUrl: './main.html',
  imports: [CommonModule, Sidebar, PostCard],
  styleUrls: ['./main.css'],
})
export class Main implements OnInit {
  posts = signal<Post[]>([]);
  loading = signal(false);

  constructor(
    private postService: PostService,
    private subredditService: SubredditService,
    private loginPromptService: LoginPromptService
  ) {}

  ngOnInit(): void {
    this.fetchPosts();
    this.subredditService.fetchSubreddits();
  }

  fetchPosts() {
    this.loading.set(true);
    this.postService.listPosts().subscribe({
      next: (data) => {
        this.posts.set(data);
        this.loading.set(false);
      },
      error: (err) => {
        console.error('Failed to fetch posts: ', err);
        this.loading.set(false);
      },
    });
  }

  get subreddits() {
    return this.subredditService.subreddits();
  }
}