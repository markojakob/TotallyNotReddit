import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Subreddit } from '../../models/subreddit';
import { formatDate } from '../../utils/time.utils';

@Component({
  selector: 'app-subreddit-sidebar',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './subreddit-sidebar.html',
})
export class SubredditSidebar {
  @Input({ required: true }) subreddit!: Subreddit;
  formatDate = formatDate;
  @Input() title?: string;
}