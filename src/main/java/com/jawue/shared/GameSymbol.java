package com.jawue.shared;

public enum GameSymbol {

  X("X"),
  O("O"),
  Empty(" ");
  final String SYMBOL;

  GameSymbol(String SYMBOL) {
    this.SYMBOL = SYMBOL;
  }

  public String getSYMBOL() {
    return SYMBOL;
  }
}
