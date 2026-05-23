import { TestBed } from '@angular/core/testing';

import { LoginPromptService } from './login-prompt-service';

describe('LoginPromptService', () => {
  let service: LoginPromptService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoginPromptService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
