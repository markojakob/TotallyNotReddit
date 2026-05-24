import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { forkJoin } from 'rxjs';
import { Post } from '../../models/post';
import { Subreddit } from '../../models/subreddit';
import { PostCard } from '../../components/post-card/post-card';
import { environment } from '../../../environments/environment';
import { timeAgo } from '../../utils/time.utils';

@Component({
  selector: 'app-search-page',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, PostCard],
  templateUrl: './search-page.html',
})
export class SearchPage implements OnInit {
  query = '';
  activeTab: 'posts' | 'subreddits' = 'posts';
  posts: Post[] = [];
  subreddits: Subreddit[] = [];
  loading = false;
  timeAgo = timeAgo;


  constructor(private route: ActivatedRoute, private router: Router, private http: HttpClient, private cdr: ChangeDetectorRef) { }

  ngOnInit() {
    this.route.queryParamMap.subscribe(params => {
      this.query = params.get('q') ?? '';
      if (this.query.trim()) {
        this.search();
      }
    });
  }
  search() {
    if (!this.query.trim()) return;
    this.loading = true;

    this.http.get<{ posts: Post[], subreddits: Subreddit[] }>(
      `${environment.apiUrl}/api/search?q=${encodeURIComponent(this.query)}`
    ).subscribe({
      next: ({ posts, subreddits }) => {
        this.posts = posts;
        this.subreddits = subreddits;
        this.loading = false;
        this.cdr.detectChanges();  // ← add
      },
      error: (err) => {
        console.error('Search failed:', err);
        this.loading = false;
        this.cdr.detectChanges();  // ← add
      }
    });
  }

  submitSearch() {
    this.router.navigate(['/search'], { queryParams: { q: this.query } });
  }
}