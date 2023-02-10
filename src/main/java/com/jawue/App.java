package com.jawue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jawue.message.PlayerMoveMessage;

import java.net.URI;

public class App {


  public static void main(String[] args) {
    try {
      TicTacToeClient c = new TicTacToeClient(new URI(
              "ws://localhost:7070/websocket")); // more about drafts here: http://github.com/TooTallNate/Java-WebSocket/wiki/Drafts

      c.connectBlocking();
      ObjectMapper mapper = new ObjectMapper();
      PlayerMoveMessage object = new PlayerMoveMessage("testibus", 'x');
      String objectAsString = mapper.writeValueAsString(object);
      c.send(objectAsString);
    } catch (Exception ignored) {
      System.err.println(ignored.getMessage());
    }

  }


}
