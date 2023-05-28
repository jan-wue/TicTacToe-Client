package com.jawue;

import codedraw.CodeDraw;
import codedraw.Event;
import com.jawue.milkyway.Button;
import com.jawue.milkyway.Image;
import com.jawue.milkyway.*;
import com.jawue.shared.Board;
import com.jawue.shared.PlayerMove;
import com.jawue.shared.message.GameSymbol;
import com.jawue.shared.message.PlayerMoveMessage;
import com.jawue.milkyway.GuiObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class Grid extends GuiObject {
  Double x = 50.00;
  Double y = 50.0;
  Double width = 900.0;
  Double height = 300.0;
  private final Double MARGIN = 20.00;
  List<GuiObject> buttonList1 = new ArrayList<>();
  List<GuiObject> buttonList2 = new ArrayList<>();
  List<GuiObject> buttonList3 = new ArrayList<>();
  List<GuiObject> layouts = new ArrayList<>();
  ButtonStyle buttonStyle = new ButtonStyle(Color.WHITE, Color.WHITE, Color.BLACK, Color.WHITE, Color.BLUE, Color.BLACK, Color.WHITE, Color.YELLOW, Color.BLACK);
  private List<com.jawue.milkyway.Image> images = new ArrayList<>();

  private String pathToXImage = "xImage.png";

  private String pathToOImage = "oImage.png";
  private String[][] board = new String[3][3];
  private TicTacToeClient client;
  private codedraw.Image xImage;
  private codedraw.Image oImage;


  public Grid(TicTacToeClient c) {
    this(100.0, 100.0, 900.0, 300.0, c);
  }

  public Grid(Double x, Double y, Double width, Double height, TicTacToeClient c) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.client = c;
    createButtons();
    createLayouts();
    try {
      this.oImage = codedraw.Image.fromResource(pathToOImage);
      this.xImage = codedraw.Image.fromResource(pathToXImage);

    } catch (Exception ex) {
      System.err.println(ex);

    }
  }

  public void draw(CodeDraw cd) {
    createAndDrawLines(cd);

    for (GuiObject layout : layouts) {
      layout.draw(cd);
    }
    createImagesAndAddToList();
    for (com.jawue.milkyway.Image image : images) {
      codedraw.Image cdImage;
      if (image.getPlayerChar().equals(GameSymbol.X.getSYMBOL())) {
        cdImage = this.xImage;
      } else {
        cdImage = this.oImage;
      }
      cd.drawImage(image.getX(), image.getY(), image.getWidth(), image.getHeight(), cdImage);
    }
  }

  @Override

  public void handleEvent(Integer mouseX, Integer mouseY, Event event) {
    for (GuiObject layout : layouts) {
      layout.handleEvent(mouseX, mouseY, event);
    }
  }

  public void createLayouts() {
    double y = this.y;
    double distanceBetweenLayouts = 10;
    List<GuiObject> buttonList = buttonList1;
    for (int i = 0; i < 3; i++) {
      if (i == 1) {
        y = this.y + this.height / 3 + distanceBetweenLayouts;
        buttonList = buttonList2;
      }
      if (i == 2) {
        y = this.y + (this.height / 3 * 2) + distanceBetweenLayouts * 2;
        buttonList = buttonList3;
      }
      Layout layout = new Layout(buttonList, this.width / 3, this.height / 3, this.x, y);
      layouts.add(layout);
    }
  }

  public void createButtons() {
    for (int i = 0; i < 9; i++) {
      int finalI = i;
      com.jawue.milkyway.Button button = new com.jawue.milkyway.Button("button" + String.valueOf(finalI), 0.0, 0.0, 0.0, 0.0, "", this.buttonStyle) {

        @Override
        public void executeMouseClickEvent() {
          int row = finalI / 3;
          int column = finalI % 3;
          PlayerMove playerMove = new PlayerMove(row, column);
          client.sendMessage(new PlayerMoveMessage(playerMove));


        }
      };
      if (i < 3) {
        buttonList1.add(button);
      } else if (i < 6) {
        buttonList2.add(button);
      } else {
        buttonList3.add(button);
      }
    }
  }

  public void updateBoard(Board board) {
    this.board = board.getBoard();
    List<GuiObject> buttonList = buttonList1;
    for (int i = 0; i < board.getLength(); i++) {
      if (i == 1) {
        buttonList = buttonList2;
      } else if (i == 2) {
        buttonList = buttonList3;
      }
      for (int j = 0; j < board.getLength(); j++) {
        Button button = (Button) buttonList.get(j);
        if (this.board[i][j].equals(GameSymbol.X.getSYMBOL()) || this.board[i][j].equals(GameSymbol.O.getSYMBOL())) {
           button.setEnabled(false);
        } else {
          button.setEnabled(true);
        }
      }
    }

  }

  public void createAndDrawLines(CodeDraw cd) {
    double buttonWidth = buttonList1.get(0).getWidth();
    double xStart = this.x + buttonWidth + MARGIN + MARGIN / 2;
    double xEnd = xStart;
    double yStart = this.y - 10;
    double yEnd = yStart + this.height + 30;

    Line line1Vertical = new Line(xStart, xEnd, yStart, yEnd);
    line1Vertical.draw(cd);

    xStart = this.x + MARGIN * 2.5 + 2 * buttonWidth;
    xEnd = xStart;

    Line line2Vertical = new Line(xStart, xEnd, yStart, yEnd);
    line2Vertical.draw(cd);

    xStart = MARGIN + this.x;
    xEnd = xStart + buttonWidth * 3 + 3 * MARGIN;
    double buttonHeight = buttonList1.get(0).getHeight();
    yStart = this.y + 5 + buttonHeight;
    yEnd = yStart;

    Line lineHorizontal1 = new Line(xStart, xEnd, yStart, yEnd);
    lineHorizontal1.draw(cd);

    yStart = this.y + 15 + buttonHeight * 2;
    yEnd = yStart;

    Line lineHorizontal2 = new Line(xStart, xEnd, yStart, yEnd);
    lineHorizontal2.draw(cd);
  }

  public void createImagesAndAddToList() {
    List<GuiObject> buttonList = buttonList1;
    for (int i = 0; i < board.length; i++) {
      if (i == 1) {
        buttonList = buttonList2;
      }
      if (i == 2) {
        buttonList = buttonList3;
      }

      for (int j = 0; j < board[i].length; j++) {
        com.jawue.milkyway.Button button = (com.jawue.milkyway.Button) buttonList.get(j);
        if (board[i][j] == null) {
          continue;
        }
        if (board[i][j].equals(GameSymbol.O.getSYMBOL())) {
          com.jawue.milkyway.Image image = new com.jawue.milkyway.Image(GameSymbol.O.getSYMBOL(), button.getX(), button.getY(), button.getWidth(), button.getHeight());
          this.images.add(image);
        } else if (board[i][j].equals(GameSymbol.X.getSYMBOL())) {
          com.jawue.milkyway.Image image = new Image(GameSymbol.X.getSYMBOL(), button.getX(), button.getY(), button.getWidth(), button.getHeight());
          this.images.add(image);
        }

      }

    }

  }


}



