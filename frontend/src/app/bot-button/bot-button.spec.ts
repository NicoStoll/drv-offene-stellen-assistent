import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BotButton } from './bot-button';

describe('BotButton', () => {
  let component: BotButton;
  let fixture: ComponentFixture<BotButton>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BotButton]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BotButton);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
