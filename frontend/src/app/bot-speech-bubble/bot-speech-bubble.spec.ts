import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BotSpeechBubble } from './bot-speech-bubble';

describe('BotSpeechBubble', () => {
  let component: BotSpeechBubble;
  let fixture: ComponentFixture<BotSpeechBubble>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BotSpeechBubble]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BotSpeechBubble);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
