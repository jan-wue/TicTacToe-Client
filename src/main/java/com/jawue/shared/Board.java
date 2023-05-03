package com.jawue.shared;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Arrays;

@JsonSerialize
public class Board {
  String[][] board = new String[3][3];

  @Override
  public String toString() {
    return Arrays.deepToString(board);
  }



  public void print() {
    this.initialize();
    System.out.println("       |     |");
    for (int i = 0; i < 3; i++) {

      String newLine = "";

      System.out.print(i + " ");

      for (int j = 0; j < 3; j++) {
        newLine = newLine + "  " + (this.board[i][j]);

        if (j != 2) {

          newLine = newLine + "  |";
        }
      }
      System.out.println(newLine);

      if (i != 2) {

        System.out.println("  -----|-----|-----    ");
      }
    }

    System.out.println("       |     |");
    System.out.println("\n   " + " A " + "    B " + "    C  ");
  }

  public void initialize() {
    for(int i = 0; i < this.board.length; i++) {
      for(int j = 0; j < this.board[i].length; j++) {
        if(board[i][j] == null) {
          board[i][j] = " ";
        }
      }
    }
  }
}
