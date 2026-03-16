import { Component, OnInit, signal } from '@angular/core';
import { Post } from '../../interfaces/post';
import { PostService } from '../../services/PostService';
import { PostCard } from '../../components/post-card/post-card';

@Component({
  selector: 'app-main',
  imports: [PostCard],
  templateUrl: './main.html',
  styleUrl: './main.css',
})
export class Main implements OnInit {
  posts = signal<Post[]>([]);

  loading = signal(false);


  constructor(private postService: PostService) {}

  ngOnInit(): void {
    this.fetchposts();
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
}
