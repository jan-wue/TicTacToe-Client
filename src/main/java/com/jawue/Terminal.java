package com.jawue;

import com.jawue.shared.Board;
import com.jawue.shared.PlayerMove;

import java.util.Scanner;

public class Terminal implements UserInteraction {


  @Override
  public PlayerMove getMove() {
    PlayerMove playerMove = new PlayerMove();
    Scanner input = new Scanner(System.in);
    System.out.println("Please enter a move for instance A2");
    String userInput = input.next();
    playerMove.setColumn(userInput.charAt(0));
    playerMove.setRow(userInput.charAt(1));
    return playerMove;
  }

  @Override
  public void displayBoard(Board board) {

  }

  @Override
  public void displayError(String string) {

  }

  @Override
  public void displayInfoMessage(String string) {

  }
}
