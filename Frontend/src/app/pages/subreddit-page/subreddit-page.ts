import { Component, OnInit, signal } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SubredditService } from '../../services/subreddit-service';
import { Post } from '../../models/post';
import { PostCard } from '../../components/post-card/post-card';
import { Subreddit } from '../../models/subreddit';
import { SubredditSidebar } from '../../components/subreddit-sidebar/subreddit-sidebar';
import { formatDate } from '../../utils/time.utils';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-subreddit',
  imports: [PostCard, SubredditSidebar, CommonModule],
  templateUrl: './subreddit-page.html',
  styleUrl: './subreddit-page.css',
})
export class SubredditPage implements OnInit {
  posts = signal<Post[]>([]);
  loading = signal(false);
  subreddit = signal<Subreddit | null>(null);
  subredditName!: string;
  currentSort = 'new';
  formatDate = formatDate;

  constructor(private route: ActivatedRoute, private subredditService: SubredditService) {}

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.subredditName = params.get('subredditName') || '';
      this.fetchPosts();

      this.subredditService.getByName(this.subredditName).subscribe({
        next: (subreddit) => this.subreddit.set(subreddit),
        error: (err) => console.error('Failed to load subreddit', err)
      });
    });
  }

  fetchPosts() {
    this.loading.set(true);
    this.subredditService.getPostsBySubreddit(this.subredditName, this.currentSort).subscribe({
      next: (posts) => {
        this.posts.set(posts);
        this.loading.set(false);
      },
      error: (err) => {
        console.error('Failed to load posts', err);
        this.loading.set(false);
      }
    });
  }

  setSort(sort: string) {
    this.currentSort = sort;
    this.fetchPosts();
  }

  get subreddits() {
    return this.subredditService.subreddits();
  }
}