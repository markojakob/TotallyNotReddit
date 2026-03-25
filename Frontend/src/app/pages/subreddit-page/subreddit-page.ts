import { Component, OnInit, signal } from '@angular/core';
import { Sidebar } from '../../components/sidebar/sidebar';
import { ActivatedRoute } from '@angular/router';
import { SubredditService } from '../../services/subreddit-service';
import { Post } from '../../models/post';
import { PostCard } from '../../components/post-card/post-card';
import { Subreddit } from '../../models/subreddit';

@Component({
  selector: 'app-subreddit',
  imports: [Sidebar, PostCard],
  templateUrl: './subreddit-page.html',
  styleUrl: './subreddit-page.css',
})
export class SubredditPage implements OnInit {
  posts = signal<Post[]>([]);
  loading = signal(false);
  subreddit = signal<Subreddit | null>(null);
  subredditName!: string;
  constructor(private route: ActivatedRoute, private subredditService: SubredditService) { }

  ngOnInit() {
  this.route.paramMap.subscribe(params => {

    this.subredditName = params.get('subredditName') || '';

    this.loading.set(true);

    this.subredditService
      .getPostsBySubreddit(this.subredditName)
      .subscribe({
        next: (posts) => {
          this.posts.set(posts);
          this.loading.set(false);
        },
        error: (err) => {
          console.error('Failed to load posts', err);
          this.loading.set(false);
        }
      });
      
      this.subredditService
      .getByName(this.subredditName)
      .subscribe({
        next: (subreddit) => {
          this.subreddit.set(subreddit);
          this.loading.set(false);
        },
        error: (err) => {
          console.error('Failed to load subreddit', err);
          this.loading.set(false)
        }
      })
  });
}


  get subreddits() {
    return this.subredditService.subreddits();
  }
}
