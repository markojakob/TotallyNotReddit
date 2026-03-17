import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubredditPage } from './subreddit-page';

describe('Subreddit', () => {
  let component: SubredditPage;
  let fixture: ComponentFixture<SubredditPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SubredditPage]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SubredditPage);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
