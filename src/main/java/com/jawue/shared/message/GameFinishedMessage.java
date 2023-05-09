package com.jawue.shared.message;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
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


