import {inject, Injectable} from '@angular/core';
import {PromptRequestModel, PromptResponseModel} from './prompt';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class Client {

  private static url = 'http://localhost:8080/api/chat';

  private readonly http: HttpClient = inject(HttpClient);

  public askQuestion(question: string, conversationId: string): Observable<PromptResponseModel> {
    const prompt: PromptRequestModel = { prompt: question, conversationId: conversationId };
    return this.http.post<any>(Client.url, prompt);
  }

  public streamQuestion(question: string, conversationId: string): Observable<PromptResponseModel> {
    return new Observable<PromptResponseModel>(observer => {
      const url = `${Client.url}/stream?prompt=${encodeURIComponent(question)}&conversationId=${encodeURIComponent(conversationId)}`;
      const eventSource = new EventSource(url);

      eventSource.onmessage = (event) => {
        try {
          const data: PromptResponseModel = JSON.parse(event.data);
          observer.next(data);
        } catch (err) {
          console.error('Error parsing SSE event', err);
        }
      };

      eventSource.onerror = (error) => {
        console.error('SSE error', error);
        eventSource.close();
        observer.complete();
      };

      return () => eventSource.close();
    });
  }
}
