package com.jawue.shared.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jawue.Board;
@JsonIgnoreProperties

public class MoveResultMessage extends Message {
  private Board board;

  private String errorMessage;

  public Board getBoard() {
    return board;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public void setBoard(Board board) {
    this.board = board;
  }

  public MoveResultMessage() {
  }

  public MoveResultMessage(Board board, String errorMessage) {
    this.board = board;
    this.errorMessage = errorMessage;
  }


}
