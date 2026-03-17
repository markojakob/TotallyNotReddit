import { Component, Input } from '@angular/core';
import { Subreddit } from '../../models/subreddit';

@Component({
  selector: 'app-sidebar',
  imports: [],
  templateUrl: './sidebar.html',
  styleUrl: './sidebar.css',
})
export class Sidebar {
@Input() subreddits!: Subreddit[];
}
