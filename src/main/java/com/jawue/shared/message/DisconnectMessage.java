package com.jawue.shared.message;

public class DisconnectMessage extends  Message{
 private String message;

  public DisconnectMessage(String message) {
    this.message = message;
  }

  public DisconnectMessage() {
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
