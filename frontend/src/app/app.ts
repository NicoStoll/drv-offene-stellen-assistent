import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { BotButton } from './bot-button/bot-button';
import { Sidenav } from './sidenav/sidenav';

@Component({
  selector: 'app-root',
  imports: [BotButton, Sidenav],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected title = 'offene-stellen-assistent-client';
}
