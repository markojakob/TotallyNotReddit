import { Component, Input } from '@angular/core';
import { Subreddit } from '../../models/subreddit';
import { RouterLink } from '@angular/router';
import { SidebarService } from '../../utils/SidebarService';
import { AuthService } from '../../services/auth-service';
import { SubredditService } from '../../services/subreddit-service';

@Component({
  selector: 'app-sidebar',
  imports: [RouterLink],
  templateUrl: './sidebar.html',
  styleUrl: './sidebar.css',
})
export class Sidebar {
  @Input() subreddits!: Subreddit[];

  constructor(public sidebarService: SidebarService, public authService: AuthService,
    private subredditService: SubredditService) { }

  get collapsed() { return this.sidebarService.collapsed(); }
  toggle() { this.sidebarService.toggle(); }

  get displayedSubreddits() {
    if (this.authService.isAuthenticated()) {
      const joined = this.subredditService.joinedSubreddits();
      return joined.length > 0 ? joined : this.subreddits;
    }
    return this.subreddits;
  }
}