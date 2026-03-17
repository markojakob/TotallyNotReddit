import { Component, OnInit, signal } from '@angular/core';
import { Post } from '../../models/post';
import { PostService } from '../../services/PostService';
import { PostCard } from '../../components/post-card/post-card';
import { CommonModule, NgIf } from '@angular/common';
import { Sidebar } from '../../components/sidebar/sidebar';
import { SubredditService } from '../../services/subreddit-service';
import { Subreddit } from '../../models/subreddit';

@Component({
  selector: 'app-main',
  imports: [PostCard, CommonModule, Sidebar],
  templateUrl: './main.html',
  styleUrl: './main.css',
})
export class Main implements OnInit {
  subreddits = signal<Array<Subreddit>>([]);
  posts = signal<Array<Post>>([]);
  loading = signal(false);


  constructor(private postService: PostService, private subredditService: SubredditService) {}

  ngOnInit(): void {
    this.fetchposts();
    this.fetchsubreddits();
  }


  fetchposts(): void {
    this.loading.set(true);
    this.postService.listPosts().subscribe({
      next: (data) => {
        this.posts.set(data);
        this.loading.set(false);

      },
      error: (err) => {
        console.error('Failed to fetch posts: ', err);
        this.loading.set(false);
      }
    });
  }

  fetchsubreddits(): void {
    this.loading.set(true);
    this.subredditService.listSubreddits().subscribe({
      next: (data) => {
        this.subreddits.set(data);
        this.loading.set(false);
      },
      error: (err) => {
        console.error('Failed to fetch posts: ', err)
        this.loading.set(false);
      }
    })
  }
}
