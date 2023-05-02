package com.jawue.shared.message;

import com.jawue.PlayerMove;

public class PlayerMoveMessage extends Message {
  private PlayerMove playerMove;

  public PlayerMoveMessage() {

  }

  public PlayerMoveMessage(PlayerMove playerMove) {
    this.playerMove = playerMove;
  }

  public PlayerMove getPlayerMove() {
    return playerMove;
  }

  public void setPlayerMove(PlayerMove playerMove) {
    this.playerMove = playerMove;
  }
}