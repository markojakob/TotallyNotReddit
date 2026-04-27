import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PostService } from '../../services/PostService';
import { Post } from '../../models/post';
import { Sidebar } from "../../components/sidebar/sidebar";

@Component({
  selector: 'app-comments-page',
  imports: [Sidebar],
  templateUrl: './comments-page.html',
  styleUrl: './comments-page.css',
})
export class CommentsPage implements OnInit{
post!: Post;
postId!: number;
subredditName!: string;
postSlug!: string;
constructor(private route: ActivatedRoute, private postService: PostService) {} 

ngOnInit() {
  this.route.paramMap.subscribe(params => {
    this.subredditName = params.get('subredditName') || '';
    this.postSlug = params.get('postSlug') || '';
    this.postId = parseFloat(params.get('postId') || '');

    this.postService.getById(this.postId).subscribe(post => {
  this.post = post;
});

  });
}

}
