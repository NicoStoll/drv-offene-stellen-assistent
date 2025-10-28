import {Component, inject} from '@angular/core';
import {Chat} from '../chat/chat';
import {CommonModule} from '@angular/common';
import {BotSpeechBubble} from '../bot-speech-bubble/bot-speech-bubble';
import {UserSpeechBubble} from '../user-speech-bubble/user-speech-bubble';
import {FormControl, ReactiveFormsModule} from '@angular/forms';
import {Sender} from '../chat/message';
import {Client} from '../chat/client';

@Component({
  selector: 'app-sidenav',
  imports: [CommonModule, BotSpeechBubble, UserSpeechBubble, ReactiveFormsModule],
  templateUrl: './sidenav.html',
  styleUrl: './sidenav.css',
})
export class Sidenav {

  protected readonly chat = inject(Chat);

  public readonly client = inject(Client);

  messageControl = new FormControl('');


  toggleChat() {
    this.chat.toggle();
  }

  sendMessage() {
    this.chat.appendMessage({
      sender: Sender.HUMAN,
      message: this.messageControl.value || ''
    });

    this.client.askQuestion(this.messageControl.value || '', this.chat.conversationId()).subscribe(resp => {
      this.chat.appendMessage({
        sender: Sender.BOT,
        message: resp.response
      });

      this.chat.conversationId.set(resp.conversationId);
    })
    this.messageControl.reset();


  }
}
