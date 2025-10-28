import {Injectable, signal} from '@angular/core';
import {Message, Sender} from './message';

@Injectable({
  providedIn: 'root'
})
export class Chat {

  isOpen = signal<boolean>(false);

  chatHistory = signal<Message[]>([
    {
      sender: Sender.BOT,
      message: "Hallo! Wie kann ich Ihnen heute helfen?"
    },
  ]);

  public isOpenSignal() {
    return this.isOpen;
  }

  public appendMessage(message: Message) {
    this.chatHistory.update(history => [...history, message]);
  }


  public toggle() {
    console.log("Toggle")
    this.isOpen.update(open => !open);
  }
}
