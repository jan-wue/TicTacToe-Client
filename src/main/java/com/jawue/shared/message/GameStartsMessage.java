package com.jawue.shared.message;

public class GameStartsMessage extends Message {
  private String gameStartsMessage;

  public GameStartsMessage(String gameStartsMessage) {
    this.gameStartsMessage = gameStartsMessage;
  }

  public GameStartsMessage() {
  }

  public String getGameStartsMessage() {
    return gameStartsMessage;
  }
}
