package com.jawue;

import com.jawue.shared.message.Message;
import com.jawue.shared.message.PlayerMoveMessage;
import com.jawue.shared.message.RequestMoveMessage;

import java.net.URI;

public class App {


  public static void main(String[] args) {
    try {
      TicTacToeClient c = new TicTacToeClient(new URI(
              "ws://localhost:7070/websocket")); // more about drafts here: http://github.com/TooTallNate/Java-WebSocket/wiki/Drafts

      c.connectBlocking();

      Message message;
     Message playerMoveMessage = new PlayerMoveMessage("A2", 'X');
      c.sendMessage(playerMoveMessage);
      while(true) {
       message = c.nextMessageBlocking();

       if(message instanceof RequestMoveMessage) {

       }
      }

    } catch (Exception ignored) {
      System.err.println(ignored.getMessage());
    }


  }


}
