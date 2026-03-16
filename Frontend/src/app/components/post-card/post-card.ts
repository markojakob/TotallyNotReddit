import { Component, Input, input, signal } from '@angular/core';
import { Post } from '../../interfaces/post';

@Component({
  selector: 'app-post-card',
  imports: [],
  templateUrl: './post-card.html',
  styleUrl: './post-card.css',
})
export class PostCard {
  @Input() post!: Post
}
