package com.jawue;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jawue.shared.GameSymbol;
import com.jawue.shared.PlayerMove;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
          board[i][j] = GameSymbol.Empty.getSYMBOL();
        }
      }
    }
  }

  public void fill(PlayerMove move) {
    int rowIndex =  Character.getNumericValue(move.getRow());
    int columnIndex = move.getColumn() - 'A';
    this.board[rowIndex][columnIndex] = GameSymbol.X.getSYMBOL();
  }
  public boolean isMoveValid(PlayerMove playerMove) {
    final List<Character> VALIDROWCHARS = Collections.unmodifiableList(new ArrayList<Character>(Arrays.asList('0', '1', '2')));
    final List<Character> VALIDCOLUMNCHARS = Collections.unmodifiableList(new ArrayList<Character>(Arrays.asList('A', 'B', 'C')));

    if(!VALIDROWCHARS.contains(playerMove.getRow()) || !VALIDCOLUMNCHARS.contains(playerMove.getColumn())) {
      return false;
    }



    return true;
  }

  public boolean isFieldOccupied (PlayerMove playerMove) {
    int rowIndex = Character.getNumericValue(playerMove.getRow());
    int columnIndex = playerMove.getColumn() - 'A';
    this.initialize();

    if(!this.board[rowIndex][columnIndex].equals(GameSymbol.Empty.getSYMBOL())) {
      return true;
    }
    return false;
  }

  public String[][] getBoard() {
    return board;
  }

  public void setBoard(String[][] board) {
    this.board = board;
  }
}
