import { Component, signal } from '@angular/core';
import { Main } from "./pages/main/main";
import { Header } from './components/header/header';

@Component({
  selector: 'app-root',
  imports: [Main, Header],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('Frontend');

}
