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
    this.toString();
  }
}
