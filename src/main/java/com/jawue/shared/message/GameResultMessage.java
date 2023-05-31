package com.jawue.shared.message;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class GameResultMessage extends Message {
private String result;

  public GameResultMessage() {
  }

  public GameResultMessage(String result) {
    this.result = result;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

}


