export interface Message {
  sender : Sender;
  message: string;
}

export enum Sender {
  HUMAN = "human",
  BOT = "bot"
}
