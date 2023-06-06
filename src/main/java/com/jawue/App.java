package com.jawue;

import com.jawue.milkyway.Button;
import com.jawue.milkyway.GuiObject;
import com.jawue.milkyway.Label;
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
      //        "ws://localhost:7070/websocket")); // more about drafts here: http://github.com/TooTallNate/Java-WebSocket/wiki/Drafts
      //"ws://192.168.1.30:7070/websocket")); // more about drafts here: http://github.com/TooTallNate/Java-WebSocket/wiki/Drafts
      "ws://janwue.com:7070/websocket")); // more about drafts here: http://github.com/TooTallNate/Java-WebSocket/wiki/Drafts


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
      boolean applicationIsRunning = true;

      client.connectBlocking();
      while (applicationIsRunning) {
        Message receivedMessage;
        UserInteraction userInteraction = new Terminal();
        Board board = new Board();
        board.initialize();
        board.print();
        boolean gameIsRunning = true;
        while (gameIsRunning) {
          receivedMessage = client.nextMessageBlocking();
          if (receivedMessage instanceof GameStartsMessage) {
            System.out.println("Game starts");
          } else if (receivedMessage instanceof RequestMoveMessage) {
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
          } else if (receivedMessage instanceof GameResultMessage) {
            System.out.println(receivedMessage);
            String message = ((GameResultMessage) receivedMessage).getResult();
            System.out.println(message + "  troloolololololo");

          } else if (receivedMessage instanceof WaitForOtherPlayerMessage) {
            System.out.println(((WaitForOtherPlayerMessage) receivedMessage).getWaitMessage());
          } else if (receivedMessage instanceof PlayAgainMessage) {
            PlayAgainMessage message = new PlayAgainMessage();
            boolean playerAnswer = getPlayerAnswer(receivedMessage);
            message.setPlayerAnswer(playerAnswer);
            client.sendMessage(message);
          } else if (receivedMessage instanceof GameFinishedMessage) {
            String message = ((GameFinishedMessage) receivedMessage).getMessage();
            System.out.println(message);
            gameIsRunning = false;
            applicationIsRunning = false;
          } else if(receivedMessage instanceof NewGameStartsMessage) {
            gameIsRunning = false;
          }
        }
      }
    } catch (Exception ignored) {
      System.err.println(ignored.getMessage());
    }

  }

  public void runGui(TicTacToeClient client) {

    ConcurrentLinkedQueue<Message> messageQueue = new ConcurrentLinkedQueue<>();
    WebsocketThread websocketThread = new WebsocketThread(client, messageQueue);
    websocketThread.start();
    boolean applicationRuns = true;
    while (applicationRuns) {

      Grid grid = new Grid(client);
      grid.setEnabled(false);
      com.jawue.milkyway.App gui = new com.jawue.milkyway.App();
      List<GuiObject> guiObjects = com.jawue.milkyway.App.guiObjects;
      guiObjects.add(grid);
      GuiObject label = new Label(grid.getX() - 100, grid.getY() + grid.getHeight() + 50, grid.getWidth(), 100.0, "");
      Board board;
      guiObjects.add(label);
      boolean gameRuns = true;
      while (gameRuns) {
        gui.draw();
        
        Message receivedMessage = messageQueue.poll();
        if (receivedMessage == null) {
          continue;
        } else if (receivedMessage instanceof GameStartsMessage) {
          String message = ((GameStartsMessage) receivedMessage).getGameStartsMessage();
          label.setText(message);
        } else if (receivedMessage instanceof ConnectMessage) {
          label.setText("You are connected bro");

        } else if (receivedMessage instanceof RequestMoveMessage) {
          label.setText("Play your move!");
          grid.setEnabled(true);
        } else if (receivedMessage instanceof MoveResultMessage) {
          board = ((MoveResultMessage) receivedMessage).getBoard();
          grid.updateBoard(board);
        } else if (receivedMessage instanceof WaitForOtherPlayerMessage) {
          String message = ((WaitForOtherPlayerMessage) receivedMessage).getWaitMessage();
          label.setText(message);
          grid.setEnabled(false);
        } else if (receivedMessage instanceof GameResultMessage) {
          GameResultMessage message = (GameResultMessage) receivedMessage;
          label.setText(message.getResult());
          try {
            Thread.sleep(1000);
          } catch (Exception ex) {
            System.err.println(ex);
          }
          grid.setEnabled(false);
        } else if (receivedMessage instanceof PlayAgainMessage) {
          PlayAgainMessage playAgainMessage = new PlayAgainMessage();
          Button noButton = new Button("no") {
            @Override
            public void executeMouseClickEvent() {
              playAgainMessage.setPlayerAnswer(false);
              client.sendMessage(playAgainMessage);

            }
          };
          Button yesButton = new Button("yes") {
            @Override
            public void executeMouseClickEvent() {
              playAgainMessage.setPlayerAnswer(true);
              client.sendMessage(playAgainMessage);

            }
          };
          List<Button> buttons = new ArrayList<>(Arrays.asList(yesButton, noButton));
          Modal modal = new Modal(buttons, 500.0, 400.0, "Hey would you like to play again?", gui.cd);
          guiObjects.clear();
          guiObjects.add(modal);

        } else if (receivedMessage instanceof GameFinishedMessage) {
          String message = ((GameFinishedMessage) receivedMessage).getMessage();
          label.setText(message);
          gui.draw();
          gameRuns = false;
          applicationRuns = false;
        }
        else if(receivedMessage instanceof NewGameStartsMessage) {
          String message = ((NewGameStartsMessage) receivedMessage).getMessage();
          label.setText(message);
          gameRuns = false;
          guiObjects.clear();
        }

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
