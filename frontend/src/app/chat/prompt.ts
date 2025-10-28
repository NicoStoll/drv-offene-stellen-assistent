export interface PromptRequestModel {
  prompt: string;
  conversationId?: string;
}

export interface PromptResponseModel {
  response: string;
  conversationId: string;
}
