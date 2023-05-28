package com.jawue;

import com.jawue.shared.message.Message;

import java.util.concurrent.ConcurrentLinkedQueue;

public class WebsocketThread extends Thread {

  private TicTacToeClient client;
  private ConcurrentLinkedQueue messageQueue = new ConcurrentLinkedQueue();

  public WebsocketThread(TicTacToeClient c, ConcurrentLinkedQueue messageQueue) {
    this.client = c;
    this.messageQueue = messageQueue;

  }
  @Override
  public void run() {

    try {
      client.connectBlocking();
      while (true) {
        Message receivedMessage = client.nextMessageBlocking();
        messageQueue.add(receivedMessage);

      }
    } catch (Exception ex) {
      System.err.println(ex.getMessage());
    }
  }


}
