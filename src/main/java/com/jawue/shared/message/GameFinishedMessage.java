package com.jawue.shared.message;


public class GameFinishedMessage extends Message {
private String result;

  public GameFinishedMessage() {

  }

  public GameFinishedMessage(String result) {
    this.result = result;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }
}


