package com.jawue;

import com.jawue.shared.Board;
import com.jawue.shared.message.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;


public class App {


  public static void main(String[] args) {

    try {
      App app = new App();
      TicTacToeClient client = new TicTacToeClient(new URI(
              "ws://localhost:7070/websocket")); // more about drafts here: http://github.com/TooTallNate/Java-WebSocket/wiki/Drafts
      //"ws://192.168.1.30:7070/websocket")); // more about drafts here: http://github.com/TooTallNate/Java-WebSocket/wiki/Drafts

      if (args.length > 0 && args[0].equals("--terminal")) {
        app.runTerminalClient(client);
      } else {
        app.runGui(client);
      }
    } catch (Exception ex) {
      System.err.println(ex);
    }

  }

  public void runTerminalClient(TicTacToeClient client) {
    try {
      client.connectBlocking();
      Message receivedMessage;
      UserInteraction userInteraction = new Terminal();
      Board board = new Board();
      board.initialize();
      board.print();
      while (true) {
        receivedMessage = client.nextMessageBlocking();
        if (receivedMessage instanceof RequestMoveMessage) {
          Message message = new PlayerMoveMessage();
          ((PlayerMoveMessage) message).setPlayerMove(userInteraction.getMove());
          client.sendMessage(message);
        } else if (receivedMessage instanceof MoveResultMessage) {
          MoveResultMessage moveResultMessage = (MoveResultMessage) receivedMessage;
          if (moveResultMessage.getErrorMessage() == null) {
            board = moveResultMessage.getBoard();
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
        } else if (receivedMessage instanceof PlayAgainMessage) {
          PlayAgainMessage message = new PlayAgainMessage();
          boolean playerAnswer = getPlayerAnswer(receivedMessage);
          message.setPlayerAnswer(playerAnswer);
          client.sendMessage(message);
        }
      }

    } catch (Exception ignored) {
      System.err.println(ignored.getMessage());
    }

  }

  public void runGui(TicTacToeClient client) {
    ConcurrentLinkedQueue<Message> messageQueue = new ConcurrentLinkedQueue<>();
    WebsocketThread websockedThread = new WebsocketThread(client, messageQueue);
    websockedThread.start();
    Grid grid = new Grid(client);
    grid.setEnabled(false);
    com.jawue.milkyway.App gui = new com.jawue.milkyway.App();
    com.jawue.milkyway.App.guiObjects.add(grid);
    Board board = new Board();

    while (true) {
      gui.draw();
      Message receivedMessage = messageQueue.poll();
      if (receivedMessage == null) {
        continue;
      } else if (receivedMessage instanceof RequestMoveMessage) {
        grid.setEnabled(true);
      } else if (receivedMessage instanceof MoveResultMessage) {
        board = ((MoveResultMessage) receivedMessage).getBoard();
        grid.updateBoard(board);
      } else if (receivedMessage instanceof WaitForOtherPlayerMessage) {
        grid.setEnabled(false);
      } else if (receivedMessage instanceof GameFinishedMessage) {
        grid.setEnabled(false);
      } else if(receivedMessage instanceof PlayAgainMessage) {

      }

    }

  }

  public static boolean getPlayerAnswer(Message receivedMessage) {
    Scanner input = new Scanner(System.in);
    System.out.println(((PlayAgainMessage) receivedMessage).getMessage());
    boolean isInputValid = false;
    while (!isInputValid) {
      List<Character> validAnswer = new ArrayList<>(Arrays.asList('Y', 'N'));
      String answer = input.next().toUpperCase();
      if (answer.length() != 1) {
        System.out.println("Answer is not valid, please try again");
        continue;
      }
      if (!validAnswer.contains(answer.charAt(0))) {
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
