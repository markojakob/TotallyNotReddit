import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateSubreddit } from './create-subreddit';

describe('CreateSubreddit', () => {
  let component: CreateSubreddit;
  let fixture: ComponentFixture<CreateSubreddit>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateSubreddit]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateSubreddit);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
