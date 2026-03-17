import { Component, Input } from '@angular/core';
import { Subreddit } from '../../models/subreddit';
import { RouterLink } from "@angular/router";

@Component({
  selector: 'app-sidebar',
  imports: [RouterLink],
  templateUrl: './sidebar.html',
  styleUrl: './sidebar.css',
})
export class Sidebar {
@Input() subreddits!: Subreddit[];
}
