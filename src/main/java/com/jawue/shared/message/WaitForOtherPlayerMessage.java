package com.jawue.shared.message;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo()
public class WaitForOtherPlayerMessage extends Message {
private String waitMessage;

  public WaitForOtherPlayerMessage() {
  }

  public WaitForOtherPlayerMessage(String waitFormessage) {
    this.waitMessage = waitFormessage;
  }

  public String getWaitMessage() {
    return waitMessage;
  }

  public void setWaitMessage(String waitMessage) {
    this.waitMessage = waitMessage;
  }
}
