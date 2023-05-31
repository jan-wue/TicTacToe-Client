package com.jawue.shared.message;

public class GameFinishedMessage extends Message {
 private String message;

  public GameFinishedMessage(String message) {
    this.message = message;
  }

  public GameFinishedMessage() {
  }

  public String getMessage() {
    return message;
  }

}
