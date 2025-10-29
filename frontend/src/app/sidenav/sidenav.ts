import {Component, inject} from '@angular/core';
import {Chat} from '../chat/chat';
import {CommonModule} from '@angular/common';
import {BotSpeechBubble} from '../bot-speech-bubble/bot-speech-bubble';
import {UserSpeechBubble} from '../user-speech-bubble/user-speech-bubble';
import {FormControl, ReactiveFormsModule} from '@angular/forms';
import {Sender} from '../chat/message';
import {Client} from '../chat/client';
import {PromptResponseModel} from '../chat/prompt';

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

    // Leeren BOT-Eintrag anlegen, der gleich live gefüllt wird
    this.chat.chatHistory.update(history => [
      ...history,
      { sender: Sender.BOT, message: '' }
    ]);

    let currentBotIndex = this.chat.chatHistory().length - 1;

    // Stream abonnieren
    this.client.streamQuestion(this.messageControl.value || '', this.chat.conversationId()).subscribe({
      next: (resp: PromptResponseModel) => {

        //update conversationId falls neu
        this.chat.conversationId.set(resp.conversationId);

        // Chunk an die letzte Bot-Nachricht anhängen
        this.chat.chatHistory.update(history => {
          const updated = [...history];
          updated[currentBotIndex] = {
            ...updated[currentBotIndex],
            message: (updated[currentBotIndex].message || '') + resp.response
          };
          return updated;
        });
      },
      error: (err) => console.error('Stream error:', err),
      complete: () => {
        console.log('Stream abgeschlossen');
      }
    });
    this.messageControl.reset();
  }
}
