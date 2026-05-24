import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubredditSidebar } from './subreddit-sidebar';

describe('SubredditSidebar', () => {
  let component: SubredditSidebar;
  let fixture: ComponentFixture<SubredditSidebar>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SubredditSidebar]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SubredditSidebar);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
