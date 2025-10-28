import {inject, Injectable} from '@angular/core';
import {PromptRequestModel, PromptResponseModel} from './prompt';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class Client {

  private static url = 'http://localhost:8080/ask';

  private readonly http: HttpClient = inject(HttpClient);

  public askQuestion(question: string, conversationid: string): Observable<PromptResponseModel> {
    const prompt: PromptRequestModel = { prompt: question, conversationId: conversationid };
    return this.http.post<any>(Client.url, prompt);
  }

}
