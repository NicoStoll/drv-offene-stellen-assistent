import {Component, Input} from '@angular/core';
import {ClipboardButtonComponent, MarkdownComponent} from 'ngx-markdown';

@Component({
  selector: 'app-bot-speech-bubble',
  imports: [
    MarkdownComponent
  ],
  templateUrl: './bot-speech-bubble.html',
  styleUrl: './bot-speech-bubble.css',
})
export class BotSpeechBubble {

  @Input() message = '';
}
