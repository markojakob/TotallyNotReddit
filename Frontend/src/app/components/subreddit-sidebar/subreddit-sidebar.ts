import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Subreddit } from '../../models/subreddit';
import { formatDate } from '../../utils/time.utils';
import { AuthService } from '../../services/auth-service';
import { SubredditService } from '../../services/subreddit-service';

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
   @Output() joinToggled = new EventEmitter<void>();

  constructor(
    public authService: AuthService
  ) {}

  toggleJoin() {
    this.joinToggled.emit();
  }
}

