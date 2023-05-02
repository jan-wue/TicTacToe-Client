package com.jawue;

import com.jawue.shared.Board;

public interface UserInteraction {


 public PlayerMove getMove();

 public void displayBoard(Board board);

 public void displayError(String string);

 public void displayInfoMessage(String string);



}
