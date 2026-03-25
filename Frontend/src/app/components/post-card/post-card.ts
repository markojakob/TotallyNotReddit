import { Component, Input, signal} from '@angular/core';
import { Post } from '../../models/post';
import { RouterLink } from "@angular/router";
import { PostService } from '../../services/PostService';
import { VoteRequest } from '../../models/vote-request';
import { VoteResult } from '../../models/vote-result';
import { NgClass } from '@angular/common';

@Component({
  selector: 'app-post-card',
  imports: [RouterLink, NgClass],
  templateUrl: './post-card.html',
  styleUrl: './post-card.css',
})
export class PostCard {
  @Input() post!: Post
  userVotes = signal<Record<number, 0 | 1 | -1>>({});
  constructor(private postService: PostService) { }


  slugify(title: string): string {
    return title
      .toLowerCase()
      .replace(/[^a-z0-9 ]/g, '')
      .replace(/\s+/g, '-');
  }


  vote(post: Post, value: 1 | -1) {
    const voteRequest: VoteRequest = {
      postId: post.id,
      userId: 2,
      voteValue: value
    };

    this.postService.voteOnPost(post.id, voteRequest).subscribe({
      next: (res: VoteResult) => {
        post.score = res.newPostScore;

        this.userVotes.update(votes => ({
          ...votes,
          [post.id]: res.voteValue
        }));

        console.log("Vote updated:", this.userVotes);
      },
      error: err => console.error(err)
    });
  }

  upvote(post: Post) {
    this.vote(post, 1);
  }

  downvote(post: Post) {
    this.vote(post, -1);
  }



}
