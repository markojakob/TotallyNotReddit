import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginPrompt } from './login-prompt';

describe('LoginPrompt', () => {
  let component: LoginPrompt;
  let fixture: ComponentFixture<LoginPrompt>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LoginPrompt]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoginPrompt);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
