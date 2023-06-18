package com.jawue;

import com.jawue.milkyway.Button;
import com.jawue.milkyway.GuiObject;
import com.jawue.milkyway.Label;
import com.jawue.shared.Board;
import com.jawue.shared.message.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;


public class App {


  public static void main(String[] args) {

    try {
      App app = new App();
      String localHost = "ws://localhost:7070/websocket";
      String gandalf = "wss://janwue.com/tictactoe/websocket";
      String environment = System.getenv("APP_ENV");
      String webSocketURL;
      if (environment == null ) {
        System.out.println("set the APP_ENV environment variable either to 'development' or 'production' ");
        return;
      } else if (environment.equals("development")) {
        webSocketURL = localHost;
      } else if(environment.equals("production")) {
        webSocketURL = gandalf;
      } else {
        System.out.println("set the APP_ENV environment variable either to 'development' or 'production' ");
        return;
      }


      System.out.println("run in environment: " + environment);
      TicTacToeClient client = new TicTacToeClient(new URI(webSocketURL));
      Thread thread = new Thread() { //if a player closes the application notify server
        public void run(){
          client.close();
        }
      };

      Runtime.getRuntime().addShutdownHook(thread);


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
            String message = ((GameResultMessage) receivedMessage).getResult();
            System.out.println(message);

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
          } else if (receivedMessage instanceof NewGameStartsMessage) {
            gameIsRunning = false;
          } else if (receivedMessage instanceof DisconnectMessage) {
            String message = ((DisconnectMessage) receivedMessage).getMessage();
            System.out.println(message);
            gameIsRunning = false;
            applicationIsRunning = false;
          }
        }
      }
    } catch (Exception ignored) {
      System.err.println(ignored.getMessage());
    }

  }

  public void runGui(TicTacToeClient client) {

    playMusic();
    ConcurrentLinkedQueue<Message> messageQueue = new ConcurrentLinkedQueue<>();
    WebsocketThread websocketThread = new WebsocketThread(client, messageQueue);
    websocketThread.start();
    boolean applicationRuns = true;
    while (applicationRuns) {

      Grid grid = new Grid(client);
      grid.setEnabled(false);
      com.jawue.milkyway.App gui = new com.jawue.milkyway.App();
      gui.event.onClose(e -> {
        try {
          Thread.sleep(5000);
        } catch (Exception xx) {

        }
      });

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
          gui.draw();
          try {
            Thread.sleep(4000);
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

          Modal modal = new Modal(new ArrayList<>(Arrays.asList(noButton, yesButton)), 500.0, 400.0, "Hey would you like to play again?", gui.cd);
          guiObjects.clear();
          guiObjects.add(modal);

        } else if (receivedMessage instanceof GameFinishedMessage) {
          String message = ((GameFinishedMessage) receivedMessage).getMessage();
           label.setText(message);
           guiObjects.clear();
           guiObjects.add(label);
           gui.draw();
          try{
           Thread.sleep(4000);
          } catch (Exception ex) {

          }
          gameRuns = false;
          applicationRuns = false;
          System.exit(0);
        } else if (receivedMessage instanceof NewGameStartsMessage) {
          String message = ((NewGameStartsMessage) receivedMessage).getMessage();
          label.setText(message + " new game starts");
          guiObjects.clear();
          guiObjects.add(label);
          gui.cd.setColor(Color.BLACK);
          gui.draw();
          try{
            Thread.sleep(5000);
          } catch (Exception ex) {

          }
          gui.cd.close();
          gameRuns = false;
          guiObjects.clear();
        } else if (receivedMessage instanceof DisconnectMessage) {
          String message = ((DisconnectMessage) receivedMessage).getMessage();
          label.setText(message);
          gui.draw();
          try {
            Thread.sleep(5000);
          } catch (Exception ex) {
            ex.printStackTrace();
          }
          gameRuns = false;
          applicationRuns = false;
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

  public void playMusic() {
    String musicPath = "cartelmusic.wav";
    try {
      URL url = getClass().getClassLoader().getResource(musicPath);
      System.out.println(url);
      if (url != null) {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
      } else {
      }
   } catch (Exception ex) {
      System.err.println(ex);
    }

  }
}
