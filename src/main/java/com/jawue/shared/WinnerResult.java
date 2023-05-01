package com.jawue.shared;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public enum WinnerResult {

  PLAYER,
  BOT,
  DRAW
}
