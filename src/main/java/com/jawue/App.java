package com.jawue;

import com.jawue.shared.Board;
import com.jawue.shared.PlayerMove;
import com.jawue.shared.message.*;

import java.net.URI;
import java.sql.SQLOutput;

public class App {


  public static void main(String[] args) {
    try {
      TicTacToeClient c = new TicTacToeClient(new URI(
              "ws://localhost:7070/websocket")); // more about drafts here: http://github.com/TooTallNate/Java-WebSocket/wiki/Drafts

      c.connectBlocking();

      Message receivedMessage;
      UserInteraction userInteraction = new Terminal();
      while (true) {
        receivedMessage = c.nextMessageBlocking();

        if (receivedMessage instanceof RequestMoveMessage) {
          Message message = new PlayerMoveMessage();
          ((PlayerMoveMessage) message).setPlayerMove(userInteraction.getMove());
          c.sendMessage(message);
        } else if (receivedMessage instanceof MoveResultMessage) {
          MoveResultMessage moveResult = (MoveResultMessage) receivedMessage;
          if (moveResult.getErrorMessage() == null) {
              Board board = moveResult.getBoard();
              board.print();
          }
            System.out.println(moveResult.getBoard());
          System.out.println(moveResult.getErrorMessage());
        } else if (receivedMessage instanceof ConnectMessage) {
          System.out.println("connected");
        } else if (receivedMessage instanceof GameFinishedMessage) {
          GameFinishedMessage gameFinishedMessage = (GameFinishedMessage) receivedMessage;
          System.out.println(gameFinishedMessage.getWinnerResult());

        }
      }

    } catch (Exception ignored) {
      System.err.println(ignored.getMessage());
    }


  }


}
