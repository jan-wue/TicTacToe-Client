package com.jawue.shared;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class PlayerMove {
  private char row;
  private char  column;

  public PlayerMove() {
  }

  public PlayerMove(char row, char column) {
    this.row = row;
    this.column = column;
  }
  public PlayerMove(int row , int column) {
     this.column = (char) ('A' +  column);
     this.row = (char) ( '0' + row);
  }

  public Character getRow() {
    return row;
  }

  public void setRow(Character row) {
    this.row = row;
  }

  public Character getColumn() {
    return column;
  }

  public void setColumn(Character column) {
    this.column = column;
  }
}
