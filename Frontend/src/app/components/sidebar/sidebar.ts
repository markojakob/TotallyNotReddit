import { Component, Input } from '@angular/core';
import { Subreddit } from '../../models/subreddit';
import { RouterLink } from '@angular/router';
import { SidebarService } from '../../utils/SidebarService';

@Component({
  selector: 'app-sidebar',
  imports: [RouterLink],
  templateUrl: './sidebar.html',
  styleUrl: './sidebar.css',
})
export class Sidebar {
  @Input() subreddits!: Subreddit[];

  constructor(public sidebarService: SidebarService) {}

  get collapsed() { return this.sidebarService.collapsed(); }
  toggle() { this.sidebarService.toggle(); }
}