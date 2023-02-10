package com.jawue.message;

public class PlayerMoveMessage extends Message {
  private String field;
  private Character playerChar;

  public PlayerMoveMessage() {
  }

  public PlayerMoveMessage(String field, Character playerChar) {
    this.field = field;
    this.playerChar = playerChar;
  }

  public String getField() {
    return field;
  }

  public Character getPlayerChar() {
    return playerChar;
  }

  public void setPlayerChar(Character playerChar) {
    this.playerChar = playerChar;
  }

  public void setField(String field) {
    this.field = field;
  }
}
