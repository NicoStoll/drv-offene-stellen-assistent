import {Component, inject, Inject} from '@angular/core';
import {Chat} from '../chat/chat';

@Component({
  selector: 'app-bot-button',
  imports: [],
  templateUrl: './bot-button.html',
  styleUrl: './bot-button.css',
})
export class BotButton {

  private readonly chat = inject(Chat);

  onClick() {
    this.chat.toggle();
  }
}
