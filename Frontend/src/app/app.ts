import { Component, OnInit, signal } from '@angular/core';
import { Header } from './components/header/header';
import { Router, RouterOutlet } from '@angular/router';
import { Sidebar } from "./components/sidebar/sidebar";
import { LoginPromptModal } from './components/login-prompt/login-prompt';
import { LoginPromptService } from './services/login-prompt-service';
import { SidebarService } from './utils/SidebarService';
import { SubredditService } from './services/subreddit-service';


@Component({
  selector: 'app-root',
  imports: [Header, RouterOutlet, LoginPromptModal, Sidebar],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {
  protected readonly title = signal('Frontend');
  constructor(public loginPromptService: LoginPromptService, public router: Router,
    public sidebarService: SidebarService, public subredditService: SubredditService) { }

  ngOnInit(): void {
    this.subredditService.fetchSubreddits();
  }
}

