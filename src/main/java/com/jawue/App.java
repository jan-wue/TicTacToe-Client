package com.jawue;

import com.jawue.shared.Answer;
import com.jawue.shared.Board;
import com.jawue.shared.message.*;

import java.net.URI;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class App {


  public static void main(String[] args) {
    try {
      TicTacToeClient c = new TicTacToeClient(new URI(
              "ws://localhost:7070/websocket")); // more about drafts here: http://github.com/TooTallNate/Java-WebSocket/wiki/Drafts
              //"ws://192.168.1.30:7070/websocket")); // more about drafts here: http://github.com/TooTallNate/Java-WebSocket/wiki/Drafts

      c.connectBlocking();


      Message receivedMessage;
      UserInteraction userInteraction = new Terminal();
      Board board = new Board();
      board.initialize();
      board.print();
      while (true) {

        receivedMessage = c.nextMessageBlocking();
        if (receivedMessage instanceof RequestMoveMessage) {
          Message message = new PlayerMoveMessage();
          ((PlayerMoveMessage) message).setPlayerMove(userInteraction.getMove());
          c.sendMessage(message);
        } else if (receivedMessage instanceof MoveResultMessage) {
          MoveResultMessage moveResultMessage = (MoveResultMessage) receivedMessage;
          if (moveResultMessage.getErrorMessage() == null) {
            board = moveResultMessage.getBoard();
            System.out.println("trororrror " + moveResultMessage.getBoard());
            board.print();
          } else {
            Message message = new PlayerMoveMessage();
            System.out.println(moveResultMessage.getErrorMessage());
            System.out.println();
          }

        } else if (receivedMessage instanceof ConnectMessage) {
          System.out.println("you are connected bro");
        } else if (receivedMessage instanceof GameFinishedMessage) {
          GameFinishedMessage gameFinishedMessage = (GameFinishedMessage) receivedMessage;
          System.out.println(gameFinishedMessage.getResult());

        } else if (receivedMessage instanceof WaitForOtherPlayerMessage) {
          System.out.println(((WaitForOtherPlayerMessage) receivedMessage).getWaitMessage());
        }
        else if(receivedMessage instanceof PlayAgainMessage) {
         PlayAgainMessage message = new PlayAgainMessage();
         boolean playerAnswer = getPlayerAnswer(receivedMessage);
         message.setPlayerAnswer(playerAnswer);
         c.sendMessage(message);
        }
      }

    } catch (Exception ignored) {
      System.err.println(ignored.getMessage());
    }




  }
  public static boolean getPlayerAnswer(Message receivedMessage) {
    Scanner input = new Scanner(System.in);
    System.out.println(((PlayAgainMessage) receivedMessage).getMessage());
    boolean isInputValid = false;
    while(!isInputValid) {
      List<Character> validAnswer = new ArrayList<>(Arrays.asList('Y', 'N'));
      String answer = input.next().toUpperCase();
      if(answer.length() != 1) {
        System.out.println("Answer is not valid, please try again");
        continue;
      }
      if(!validAnswer.contains(answer.charAt(0))) {
        System.out.println("Answer is not valid, please try again");
        continue;
      }

      if (answer.equals("Y")) {
        return true;
      }

      isInputValid = true;
    }
    return false;
  }


}
