package com.jawue;

import com.jawue.shared.Board;
import com.jawue.shared.PlayerMove;

public interface UserInteraction {


 public PlayerMove getMove();

 public void displayBoard(Board board);

 public void displayError(String string);

 public void displayInfoMessage(String string);



}
