// src/app/components/login-prompt-modal/login-prompt-modal.ts
import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-login-prompt-modal',
  standalone: true,
  template: `
    @if (visible) {
      <div class="modal modal-open">
        <div class="modal-box">
          <h3 class="font-bold text-lg">You need to be logged in</h3>
          <p class="py-4">Please log in or register to continue.</p>
          <div class="modal-action">
            <button class="btn" (click)="close.emit()">Cancel</button>
            <button class="btn btn-primary" (click)="login.emit()">Log in</button>
          </div>
        </div>
        <div class="modal-backdrop" (click)="close.emit()"></div>
      </div>
    }
  `
})
export class LoginPromptModal {
  @Input() visible = false;
  @Output() close = new EventEmitter<void>();
  @Output() login = new EventEmitter<void>();
}