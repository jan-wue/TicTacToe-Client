package com.jawue.shared.message;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class NewGameStartsMessage extends  Message {
  private String message;

  public NewGameStartsMessage(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public NewGameStartsMessage() {
  }
}
