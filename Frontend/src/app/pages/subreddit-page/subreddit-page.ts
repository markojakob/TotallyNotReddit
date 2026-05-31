import { Component, OnInit, signal } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { take } from 'rxjs';
import { AuthService } from '../../services/auth-service';
import { SubredditService } from '../../services/subreddit-service';
import { Post } from '../../models/post';
import { Subreddit } from '../../models/subreddit';
import { PostCard } from '../../components/post-card/post-card';
import { SubredditSidebar } from '../../components/subreddit-sidebar/subreddit-sidebar';
import { formatDate } from '../../utils/time.utils';

@Component({
  selector: 'app-subreddit',
  standalone: true,
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

  constructor(
    private route: ActivatedRoute,
    private subredditService: SubredditService,
    public authService: AuthService
  ) {}

  ngOnInit() {
    this.route.paramMap.pipe(take(1)).subscribe(params => {
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

  toggleJoin() {
    const sub = this.subreddit();
    if (!sub) return;

    const action$ = sub.isJoined
      ? this.subredditService.leaveSubreddit(sub.id)
      : this.subredditService.joinSubreddit(sub.id);

    action$.subscribe({
      next: () => {
        this.subreddit.set({
          ...sub,
          isJoined: !sub.isJoined,
          membersCount: (sub.membersCount ?? 0) + (sub.isJoined ? -1 : 1)
        });
      }
    });
  }
}