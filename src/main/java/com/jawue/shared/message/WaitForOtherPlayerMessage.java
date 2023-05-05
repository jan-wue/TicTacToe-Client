package com.jawue.shared.message;


public class WaitForOtherPlayerMessage extends Message {
private String waitMessage;

  public WaitForOtherPlayerMessage() {

  }

  public WaitForOtherPlayerMessage(String waitMessage) {
    this.waitMessage = waitMessage;
  }

  public String getWaitMessage() {
    return waitMessage;
  }

  public void setWaitMessage(String waitMessage) {
    this.waitMessage = waitMessage;
  }
}
