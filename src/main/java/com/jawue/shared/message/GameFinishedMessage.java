package com.jawue.shared.message;

import com.jawue.shared.WinnerResult;

public class GameFinishedMessage extends Message {
WinnerResult winnerResult;

  public GameFinishedMessage(WinnerResult winnerResult) {
    this.winnerResult = winnerResult;
  }
}
