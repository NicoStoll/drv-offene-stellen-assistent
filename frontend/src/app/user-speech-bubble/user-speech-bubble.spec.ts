import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserSpeechBubble } from './user-speech-bubble';

describe('UserSpeechBubble', () => {
  let component: UserSpeechBubble;
  let fixture: ComponentFixture<UserSpeechBubble>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserSpeechBubble]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserSpeechBubble);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
