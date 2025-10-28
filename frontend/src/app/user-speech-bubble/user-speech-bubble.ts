import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-user-speech-bubble',
  imports: [],
  templateUrl: './user-speech-bubble.html',
  styleUrl: './user-speech-bubble.css',
})
export class UserSpeechBubble {

  @Input() message = '';
}
