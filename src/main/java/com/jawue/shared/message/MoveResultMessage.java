package com.jawue.shared.message;

import com.jawue.shared.Board;

public class MoveResultMessage extends Message {
  Board board;
  String errorMessage;
  public MoveResultMessage(Board board) {
    this.board = board;
  }

  public MoveResultMessage(Board board, String errorMessage) {
    this.board = board;
    this.errorMessage = errorMessage;
  }
}
