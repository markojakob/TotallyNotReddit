import { Component, signal } from '@angular/core';
import { Header } from './components/header/header';
import {Router, RouterOutlet } from '@angular/router';
import { Sidebar } from "./components/sidebar/sidebar";
import { LoginPromptModal } from './components/login-prompt/login-prompt';
import { LoginPromptService } from './services/login-prompt-service';


@Component({
  selector: 'app-root',
  imports: [Header, RouterOutlet, LoginPromptModal],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('Frontend');
  constructor(public loginPromptService: LoginPromptService, public router: Router) {}
}

