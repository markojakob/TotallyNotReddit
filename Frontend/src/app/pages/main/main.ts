import { Component, OnInit, signal, ViewChild, ElementRef, AfterViewInit, OnDestroy } from '@angular/core';
import { PostService } from '../../services/PostService';
import { AuthService } from '../../services/auth-service';
import { Post } from '../../models/post';
import { CommonModule } from '@angular/common';
import { PostCard } from '../../components/post-card/post-card';
import { RouterLink } from '@angular/router';

const PAGE_SIZE = 10;

@Component({
  selector: 'app-main',
  templateUrl: './main.html',
  imports: [CommonModule, PostCard, RouterLink],
  styleUrls: ['./main.css'],
})
export class Main implements OnInit, AfterViewInit, OnDestroy {
  @ViewChild('scrollAnchor') scrollAnchor!: ElementRef;

  posts = signal<Post[]>([]);
  loading = signal(false);
  hasMore = signal(true);
  activeTab: 'popular' | 'foryou' = 'popular';

  private allPosts: Post[] = [];  // full list from API
  private currentIndex = 0;       // how many we've shown so far
  private observer!: IntersectionObserver;

  constructor(
    private postService: PostService,
    public authService: AuthService
  ) {}

  ngOnInit() {
    this.fetchPosts();
  }

  ngAfterViewInit() {
    this.observer = new IntersectionObserver(entries => {
      if (entries[0].isIntersecting && !this.loading() && this.hasMore()) {
        this.showNextPage();
      }
    }, { threshold: 0.1 });

    this.observer.observe(this.scrollAnchor.nativeElement);
  }

  ngOnDestroy() {
    this.observer?.disconnect();
  }

  setTab(tab: 'popular' | 'foryou') {
    this.activeTab = tab;
    this.fetchPosts();
  }

  fetchPosts() {
    this.loading.set(true);
    this.posts.set([]);
    this.allPosts = [];
    this.currentIndex = 0;
    this.hasMore.set(true);

    const request$ = this.activeTab === 'foryou' && this.authService.isAuthenticated()
      ? this.postService.getForYouFeed()
      : this.postService.listPosts();

    request$.subscribe({
      next: (data) => {
        this.allPosts = data;
        this.loading.set(false);
        this.showNextPage(); // show first batch
      },
      error: (err) => {
        console.error('Failed to fetch posts:', err);
        this.loading.set(false);
      }
    });
  }

  showNextPage() {
    const nextSlice = this.allPosts.slice(this.currentIndex, this.currentIndex + PAGE_SIZE);
    if (nextSlice.length === 0) {
      this.hasMore.set(false);
      return;
    }
    this.posts.update(p => [...p, ...nextSlice]);
    this.currentIndex += nextSlice.length;
    if (this.currentIndex >= this.allPosts.length) {
      this.hasMore.set(false);
    }
  }
}