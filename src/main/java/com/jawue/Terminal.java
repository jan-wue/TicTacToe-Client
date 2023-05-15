package com.jawue;

import com.jawue.shared.Board;
import com.jawue.shared.PlayerMove;

import java.util.List;
import java.util.Scanner;

public class Terminal implements UserInteraction {


  @Override
  public PlayerMove getMove() {
    PlayerMove playerMove = new PlayerMove();
    Scanner input = new Scanner(System.in);
    System.out.println("Please enter a valid move first you have to type the column letter and than the row number for instance A2");
    String userInput = input.next().toUpperCase();
    while(userInput.length() != 2) {
      System.out.println("Invalid Move please type again");
     userInput = input.next().toUpperCase();

    }
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

  @Override
  public String askUser(List<String> list) {
    return null;
  }
}
